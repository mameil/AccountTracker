package com.kyu9.accountbook.common

enum class DefaultValue(val value: Any) {
    STRING("-"),
    INT(0),
    BOOLEAN(true),
    ;

    companion object {
        fun <T> getDefaultValue(value: T): T {
            return when (value) {
                is String -> STRING.value as T
                is Int -> INT.value as T
                is Boolean -> BOOLEAN.value as T
                else -> throw IllegalArgumentException("Not support type")
            }
        }
    }

}