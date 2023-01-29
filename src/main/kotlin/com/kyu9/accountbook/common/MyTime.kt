package com.kyu9.accountbook.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyTime {

    companion object{

        fun toYyyymmddhhmmss(localDateTime: LocalDateTime): String{
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        }
    }
}