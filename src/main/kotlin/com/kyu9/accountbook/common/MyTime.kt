package com.kyu9.accountbook.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyTime {

    companion object{

        fun toYyyymmddhhmmss(localDateTime: LocalDateTime): String{
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        }

        fun toYyyy(localDateTime: LocalDateTime): String{
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyy"))
        }

        fun toYyyyMm(localDateTime: LocalDateTime): String{
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyyMd"))
        }

        fun toYyyyMmDd(localDateTime: LocalDateTime): String{
            return localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        }

        fun toLocalDateTime(yyyyMMddHHmmss: String): LocalDateTime{
            return LocalDateTime.of(
                yyyyMMddHHmmss.substring(0, 4).toInt(),
                yyyyMMddHHmmss.substring(4, 6).toInt(),
                yyyyMMddHHmmss.substring(6, 8).toInt(),
                yyyyMMddHHmmss.substring(8, 10).toInt(),
                yyyyMMddHHmmss.substring(10, 12).toInt(),
                yyyyMMddHHmmss.substring(12, 14).toInt()
            )
        }

        fun now(): String{
            return toYyyymmddhhmmss(LocalDateTime.now())
        }
    }
}