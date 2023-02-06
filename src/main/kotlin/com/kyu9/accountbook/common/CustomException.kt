package com.kyu9.accountbook.common

import org.springframework.web.client.HttpClientErrorException.BadRequest

enum class CustomException(val msg: String, val code: String) {
    DATA_NOT_FOUND("no data", "0001")

    ;


    fun doThrow(): Exception{
        throw RuntimeException(this.msg)
    }
}