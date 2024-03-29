package com.kyu9.accountbook.application

import com.kyu9.accountbook.application.repository.TagRepository
import com.kyu9.accountbook.application.repository.UsageTransactionRepoImpl
import com.kyu9.accountbook.common.CustomError
import com.kyu9.accountbook.common.MyTime
import com.kyu9.accountbook.domain.UsageTransaction
import com.kyu9.accountbook.domain.properties.MoneyType
import com.kyu9.accountbook.elastic.Transaction
import com.kyu9.accountbook.elastic.TransactionRepository
import com.kyu9.accountbook.swagger.model.*
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.IsoFields
import java.time.temporal.TemporalAdjusters
import javax.transaction.Transactional


@Service
class TransactionService(
        private val transactionRepoImpl: UsageTransactionRepoImpl,
        private val transactionElasticRepository: TransactionRepository,
        private val tagRepoImpl: TagRepository
) {

    fun storeFromDto(tranReqDto: PostTranRequestDto): PostTransResponseDto {
//        val registered = if (tranReqDto.registeredAt == null) MyTime.now() else MyTime.toLocalDateTimeWithYyyymmdd(tranReqDto.registeredAt)

        var registered: LocalDateTime
        if (tranReqDto.registeredAt == null && tranReqDto.registeredAtYyyymmdd == null) {
            registered = MyTime.now()
        }
        else if (tranReqDto.registeredAt == null && tranReqDto.registeredAtYyyymmdd == "") {
            registered = MyTime.now()
        }
        else {
            registered = MyTime.toLocalDateTimeWithYyyymmdd(tranReqDto.registeredAtYyyymmdd?:MyTime.toYyyyMmDd(MyTime.now()))
        }

        return transactionRepoImpl.storeEntity(
                UsageTransaction(
                        userId = tranReqDto.userId?:"Unknown",
                        amount = tranReqDto.amount?.toLong()!!,
                        registered = registered,
                        title = tranReqDto.title!!,
                        content = tranReqDto.content!!,
                        tagId = tranReqDto.tagId?.toLong()!!,
                        moneyType = tranReqDto.moneyType.toString()
                )
        ).let {
            PostTransResponseDto(it.id)
        }
    }

    fun getSingleTransaction(utid: String): GetSingleTransResponseDto {
        return transactionRepoImpl.getEntityWithId(utid).let {
            GetSingleTransResponseDto(
                    userId = it.userId,
                    utid = it.id,
                    amount = it.amount.toInt(),
                    registeredAt = MyTime.toYyyymmddhhmmss(it.registered),
                    title = it.title,
                    content = it.content,
                    tagId = it.tagId.toInt(),
                    moneyType = GetSingleTransResponseDto.MoneyType.valueOf(it.moneyType.toString().uppercase()),
                    created = MyTime.toYyyymmddhhmmss(it.created),
                    updated = MyTime.toYyyymmddhhmmss(it.updated)
            )
        }
    }

    fun deleteSingleTransaction(utid: String) {
        transactionRepoImpl.removeEntityWithId(utid)
    }

    fun getAllTransaction(): GetListTransResponseDto {
        //tag 을 조인해서 가져오는 방식 언젠간 해야함..
        val map = transactionRepoImpl.getAllEntityOrderByRegisteredDesc().map {
            GetSingleTransResponseDto(
                    userId = it.userId,
                    utid = it.id,
                    amount = it.amount.toInt(),
                    registeredAt = MyTime.toYyyyMmDd(it.registered),
                    title = it.title,
                    content = it.content,
                    tagId = it.tagId.toInt(),
                    moneyType = GetSingleTransResponseDto.MoneyType.valueOf(it.moneyType.toString().uppercase()),
                    created = MyTime.toYyyymmddhhmmss(it.created),
                    updated = MyTime.toYyyymmddhhmmss(it.updated)
            )
        }

        return GetListTransResponseDto(map)
    }

    fun getMonthlyTransactions(): GetMonthlyTranListResponseDto {
        val listRes = arrayListOf<GetMonthlyTranResponseDto>()
        transactionRepoImpl.getAllEntityGroupByRegisteredYYYYMM()
                .groupBy { it.registeredYYYYMM }
                .forEach{
                    mapped ->
                    run {
                    val yyyymm = mapped.key
                    var mineAmt: Int? = 0
                    var freeAmt: Int? = 0
                    mapped.value.forEach {
                        when (it.moneyType) {
                            MoneyType.MINE -> mineAmt = mineAmt?.plus(it.sumAmt?.toInt()!!)
                            MoneyType.FREE -> freeAmt = freeAmt?.plus(it.sumAmt?.toInt()!!)
                            else -> {
                                CustomError.DATA_NOT_FOUND.doThrow()
                            }
                        }
                    }
                    listRes.add(GetMonthlyTranResponseDto(yyyymm, mineAmt, freeAmt))
                }
        }
        return GetMonthlyTranListResponseDto(listRes)
    }

    fun getRecentDaysTransactions(days: Int): GetDailyListTransResponseDto {
        val d3Yyyymmdd = transactionRepoImpl.getAllEntityBetweenRegisteredYyyymmdd(days)
        val yyyymmddSet = d3Yyyymmdd.groupBy { it.registeredYYYYMMDD }

        val arr = mutableListOf<GetDailySingleTransResponseDto>()
        yyyymmddSet.forEach {
            var infoList = mutableListOf<GetDailySingleTransResponseInfoDto>()
            it.value.map {
                GetDailySingleTransResponseInfoDto(
                        yyyymmdd = it.registeredYYYYMMDD,
                        amount = it.amount.toInt(),
                        title = it.title,
                        content = it.content,
                        moneyType = GetDailySingleTransResponseInfoDto.MoneyType.valueOf(it.moneyType.toString().uppercase()),
                )
            }.forEach(infoList::add)

            arr.add(GetDailySingleTransResponseDto(
                    yyyymmdd = it.key,
                    infoList = infoList
            ))
        }

        val totalArr = mutableListOf<GetDailyTotalDto>()
        yyyymmddSet.forEach(){
            var mine: Int = 0
            var free: Int = 0
            it.value.forEach {
                when (it.moneyType) {
                    MoneyType.MINE -> {
                        mine += it.amount.toInt()
                    }
                    MoneyType.FREE -> {
                        free += it.amount.toInt()
                    }
                    else -> {
                        CustomError.DATA_NOT_FOUND.doThrow()
                    }
                }
            }

            totalArr.add(GetDailyTotalDto(
                    yyyymmdd = it.key,
                    totalString = "복지: ${free}원 / 내돈: ${mine}원"
            ))
        }


        return GetDailyListTransResponseDto(
                arr,
                calCachAmount(d3Yyyymmdd).map{
                    GetDailySumDto(
                            yyyymmdd = it.yyyymmdd,
                            cashType = GetDailySumDto.CashType.valueOf(it.moneyType.toString().uppercase()),
                            amountSum = it.amount.toInt()
                    )
                },
                totalArr
        )
    }

    fun calCachAmount(list: List<UsageTransaction>): List<UsageTransaction.UTAmountSum>{
        var daily = list.groupBy { it.registeredYYYYMMDD to it.moneyType }
        var dailySum = arrayListOf<UsageTransaction.UTAmountSum>()

        daily.forEach {
            var sum = 0
            it.value.forEach {
                it2 -> sum += it2.amount.toInt()
            }

            dailySum.add(UsageTransaction.UTAmountSum(it.key.first, it.key.second, sum.toLong()))
        }

        return dailySum
    }

    fun getLastTransactionRecordedDay(): GetLastRecordedDayResponseDto? {
        return GetLastRecordedDayResponseDto(
                lastRecordedDay = transactionRepoImpl.getLastTransactionRecordedDay().registeredYYYYMMDD
        )
    }

    @Transactional
    fun migrateAllToElasticSearch() {
        val documentList = arrayListOf<Transaction>()

        transactionRepoImpl.getAllEntityOrderByRegistered().forEach {
            var document = UsageTransaction.toDocument(it)
            document.category = tagRepoImpl.findById(it.tagId).orElseThrow(CustomError.DATA_NOT_FOUND::doThrow).name
            documentList.add(document)
        }

        val tobeList = arrayListOf<Transaction>()
        documentList.minus(transactionElasticRepository.findAll().toSet()).forEach {
            tobeList.add(it)
        }

        transactionElasticRepository.saveAll(tobeList)
    }

    fun inquiryViewStatistics(): GetViewStatisticsSingleDto? {
        val now = MyTime.now()
        val today = MyTime.toYyyyMmDd(now)
        val todayTran = transactionRepoImpl.getAllEntityBetweenRegisteredYyyymmddEqual(today).sumOf { it.amount.toInt() }

        val date = LocalDate.parse(today, DateTimeFormatter.BASIC_ISO_DATE)

        // 입력된 날짜가 속한 주의 시작일과 종료일 계산
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val firstDayOfWeek = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).format(formatter)
        val lastDayOfWeek = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).format(formatter)
        println(firstDayOfWeek)
        println(lastDayOfWeek)

        // 주차 계산 (주차를 정확하게 계산하려면 일요일부터 시작되는 주차가 사용되어야 할 수도 있습니다)
        val weekNumber = date[IsoFields.WEEK_OF_WEEK_BASED_YEAR]
        val formattedDate = date.format(DateTimeFormatter.ofPattern("MM'월'W'주'"))
        val weeklyTran = transactionRepoImpl.getAllEntityByRegistered(firstDayOfWeek.replace("-", ""), lastDayOfWeek.replace("-", "")).sumOf { it.amount.toInt() }

        val month = MyTime.toYyyyMm(MyTime.now())
        val monthlyTran = transactionRepoImpl.getAllEntityByRegisteredYyyymm(month).sumOf { it.amount.toInt() }


        return GetViewStatisticsSingleDto(
                dailyName = today,
                dailySum = todayTran.toString(),
                weeklyName = formattedDate,
                weeklySum = weeklyTran.toString(),
                monthlyName = month.substring(4, 6) + "월",
                monthlySum = monthlyTran.toString()
        )
    }

    fun getTransactionsGroupByTag(): GetListGroupTagDto? {
        val list = transactionRepoImpl.findAll()
        val groupList = arrayListOf<GetGroupOfTagDto>()
        list.groupBy { it.tagId }
                .forEach{ it1 ->
                    groupList.add(GetGroupOfTagDto(
                        tagId = it1.key.toInt(),
                        name = tagRepoImpl.findById(it1.key).orElseThrow(CustomError.DATA_NOT_FOUND::doThrow).name,
                        amountSum = it1.value.sumOf { it.amount.toInt() }
                        ))
                }

        return GetListGroupTagDto(groupList.sortedBy { it.tagId })
    }
}