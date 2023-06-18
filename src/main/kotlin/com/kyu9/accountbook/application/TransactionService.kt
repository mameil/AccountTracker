package com.kyu9.accountbook.application

import com.kyu9.accountbook.application.repository.UsageTransactionRepoImpl
import com.kyu9.accountbook.common.CustomError
import com.kyu9.accountbook.common.MyTime
import com.kyu9.accountbook.domain.UsageTransaction
import com.kyu9.accountbook.domain.properties.MoneyType
import com.kyu9.accountbook.swagger.model.*
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@RequiredArgsConstructor
class TransactionService(
        private val transactionRepoImpl: UsageTransactionRepoImpl
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


        return GetDailyListTransResponseDto(
                arr,
                calCachAmount(d3Yyyymmdd).map{
                    GetDailySumDto(
                            yyyymmdd = it.yyyymmdd,
                            cashType = GetDailySumDto.CashType.valueOf(it.moneyType.toString().uppercase()),
                            amountSum = it.amount.toInt()
                    )
                }
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
}