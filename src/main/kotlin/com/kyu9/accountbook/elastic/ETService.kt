package com.kyu9.accountbook.elastic

import com.kyu9.accountbook.common.CustomError
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class ETService(
        private val transactionRepository: TransactionRepository
) {


    fun postTransaction(userId: String?, userName: String?, amt: Int?): String? {
        return transactionRepository
                .save(Transaction(userId!!, userName!!, amt!!))
                .id
    }

    fun getTransactionById(id: String): Transaction? {
        return transactionRepository.findById(id).orElseThrow(CustomError.DATA_NOT_FOUND::doThrow)
    }
}