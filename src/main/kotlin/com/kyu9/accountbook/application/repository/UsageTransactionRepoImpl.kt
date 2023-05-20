package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.common.BaseJpaRepo
import com.kyu9.accountbook.common.MyTime
import com.kyu9.accountbook.domain.QUsageTransaction
import com.kyu9.accountbook.domain.UsageTransaction
import com.kyu9.accountbook.domain.dto.MonthlyTran
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.NumberExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import lombok.extern.log4j.Log4j
import lombok.extern.log4j.Log4j2
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger


@Service
@Log4j2
class UsageTransactionRepoImpl(
    @Autowired private val usageTransactionRepository: UsageTransactionRepository,
    @Autowired private val jpaQueryFactory: JPAQueryFactory
): BaseJpaRepo<UsageTransaction, String, UsageTransactionRepository>(usageTransactionRepository){
    private val logger: org.slf4j.Logger = LoggerFactory.getLogger(this.javaClass)

    fun getAllEntityOrderByCreatedDesc(): List<UsageTransaction> {
        return repo.findAllByOrderByCreatedDesc()
    }

    fun getAllEntityOrderByRegisteredDesc(): List<UsageTransaction> {
        return repo.findAllByOrderByRegisteredDesc()
    }

    fun getAllEntityGroupByRegisteredYYYYMM(): List<MonthlyTran> {
        val ut = QUsageTransaction.usageTransaction

        return jpaQueryFactory
                .select(Projections.constructor(MonthlyTran::class.java,
                        ut.registeredYYYYMM.`as`("registeredYYYYMM"),
                        ut.moneyType.`as`("moneyType"),
                        ut.amount.sum().`as`("sumAmt"))
                )
                .from(ut)
                .groupBy(ut.registeredYYYYMM, ut.moneyType)
                .fetch()
    }

    fun getAllEntityBetweenRegisteredYyyymmdd(days: Int): List<UsageTransaction>{
        //오늘은 포함해서 계산해야하기 때문에 -1을 해줌(오늘이 20230520인데 3일치라고 하면 20230518, 20230519, 20230520을 포함해야함) >> 20230520 - 3 + 1 = 20230518
        val startYyyymmdd = MyTime.toYyyyMmDd(MyTime.now().minusDays(days.toLong()-1))
        val endYyyymmdd = MyTime.toYyyyMmDd(MyTime.now())
        logger.info("GET RECENT TRANS => startYyyymmdd: $startYyyymmdd, endYyyymmdd: $endYyyymmdd")
        return repo.findByRegisteredYYYYMMDDBetweenOrderByRegisteredYYYYMMDD(
                startYyyymmdd,
                endYyyymmdd
        )
    }

}