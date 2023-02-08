package com.kyu9.accountbook.common

import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpClientErrorException.BadRequest


enum class ErrorCode(val code: String, val msg: String)   {
    DATA_NOT_FOUND("000_0001", "no data")

    ;

    override fun toString(): String {
        return "[${this.code}] : ${this.msg}"
    }

    private fun toStringWithAdditionalMsg(add: String): String{
        return "$this / $add"
    }

    fun doThrow(): RuntimeException{
//        throw HttpClientErrorException(HttpStatus.BAD_REQUEST, this.toString())
        throw RuntimeException(this.toString())
    }

    fun doThrowWithAdditionalMsg(additionalMsg: String): RuntimeException{
//        throw HttpClientErrorException(HttpStatus.BAD_REQUEST, this.toStringWithAdditionalMsg(additionalMsg))
        throw RuntimeException(this.toStringWithAdditionalMsg(additionalMsg))
    }
}
