package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.common.BaseJpaRepo
import com.kyu9.accountbook.domain.UsageTransaction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsageTransactionRepoImpl(
    @Autowired private val usageTransactionRepository: UsageTransactionRepository
): BaseJpaRepo<UsageTransaction, String, UsageTransactionRepository>(usageTransactionRepository){

    fun getAllEntityOrderByCreatedDesc(): List<UsageTransaction> {
        return repo.findAllByOrderByCreatedDesc()
    }

    fun getAllEntityOrderByRegisteredDesc(): List<UsageTransaction> {
        return repo.findAllByOrderByRegisteredDesc()
    }

    fun getAllEntityGroupByRegisteredYYYYMM(): List<UsageTransaction> {
        return repo.findAllGroupByRegistered()
    }
}