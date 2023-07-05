package com.kyu9.accountbook.elastic.dto

data class GetTranRespDto(
        val userId: String,
        val userName: String,
        val amt: Int
)
