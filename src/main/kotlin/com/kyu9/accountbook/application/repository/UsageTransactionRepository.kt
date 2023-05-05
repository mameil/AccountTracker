package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.domain.UsageTransaction
import org.springframework.data.jpa.repository.JpaRepository

interface UsageTransactionRepository: JpaRepository<UsageTransaction, String> {
    fun findAllByOrderByCreatedDesc(): List<UsageTransaction>

    fun findAllByOrderByRegisteredDesc(): List<UsageTransaction>
}