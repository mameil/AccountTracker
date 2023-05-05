package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.domain.UsageTransaction
import org.springframework.data.jpa.repository.JpaRepository

interface UsageTransactionRepository: JpaRepository<UsageTransaction, String> {
    //todo 이거 created 가 아니라 registeredAt desc 로 해야함
    fun findAllByOrderByCreatedDesc(): List<UsageTransaction>

    fun findAllByOrderByRegisteredDesc(): List<UsageTransaction>
}