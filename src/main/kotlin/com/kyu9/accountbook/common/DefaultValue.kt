package com.kyu9.accountbook.common

import java.time.LocalDateTime

enum class DefaultValue(val value: Any) {
    STRING("-"),
    INT(0),
    BOOLEAN(true),
    LONG(0L),

    NOW(LocalDateTime.now()),
    ;

    companion object {
        fun defaultString(): String{
            return STRING.value as String
        }

        fun defaultInt(): Int{
            return INT.value as Int
        }

        fun defaultLong(): Long{
            return LONG.value as Long
        }

        fun defaultBoolean(): Boolean{
            return BOOLEAN.value as Boolean
        }

        fun defaultNow(): LocalDateTime{
            return NOW.value as LocalDateTime
        }
    }
}