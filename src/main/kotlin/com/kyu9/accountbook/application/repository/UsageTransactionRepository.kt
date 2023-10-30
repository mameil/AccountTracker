package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.domain.UsageTransaction
import com.kyu9.accountbook.domain.dto.MonthlyTran
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UsageTransactionRepository: JpaRepository<UsageTransaction, String> {
    fun findAllByOrderByCreatedDesc(): List<UsageTransaction>

    fun findAllByOrderByRegisteredDesc(): List<UsageTransaction>

    fun findAllByOrderByRegistered(): List<UsageTransaction>

    @Query("SELECT ut.registeredYYYYMM, ut.moneyType, sum(ut.amount) FROM UsageTransaction ut GROUP BY ut.registeredYYYYMM, ut.moneyType ORDER BY ut.registeredYYYYMM DESC")
    fun findAllGroupByRegistered(): List<MonthlyTran>

    fun findByRegisteredYYYYMMDDBetweenOrderByRegisteredYYYYMMDD(start: String, end: String): List<UsageTransaction>

    fun findAllByRegisteredYYYYMMDD(yyyymmdd: String): List<UsageTransaction>

    fun findAllByRegisteredYYYYMM(yyyymm: String): List<UsageTransaction>

    fun findAllByRegisteredYYYYMMDDBetween(from: String, to: String): List<UsageTransaction>

    fun findFirstByOrderByRegisteredYYYYMMDDDesc(): UsageTransaction

}