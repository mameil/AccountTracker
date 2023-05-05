package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.domain.UsageTransaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UsageTransactionRepository: JpaRepository<UsageTransaction, String> {
    fun findAllByOrderByCreatedDesc(): List<UsageTransaction>

    fun findAllByOrderByRegisteredDesc(): List<UsageTransaction>

    @Query("SELECT ut FROM UsageTransaction ut GROUP BY ut.registeredYYYYMM ORDER BY ut.registeredYYYYMM DESC")
    fun findAllGroupByRegistered(): List<UsageTransaction>

}