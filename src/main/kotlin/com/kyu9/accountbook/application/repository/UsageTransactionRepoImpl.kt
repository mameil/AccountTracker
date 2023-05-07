package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.common.BaseJpaRepo
import com.kyu9.accountbook.domain.QUsageTransaction
import com.kyu9.accountbook.domain.UsageTransaction
import com.kyu9.accountbook.domain.dto.MonthlyTran
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.core.types.dsl.NumberExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class UsageTransactionRepoImpl(
    @Autowired private val usageTransactionRepository: UsageTransactionRepository,
    @Autowired private val jpaQueryFactory: JPAQueryFactory
): BaseJpaRepo<UsageTransaction, String, UsageTransactionRepository>(usageTransactionRepository){

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

}