package com.kyu9.accountbook.common

import java.time.LocalDateTime

class DefaultValue() {
    companion object {
        ≈val STRING = "-"
        val INT = 0
        val LONG = 0L
        val BOOLEAN = false
        val NOW = LocalDateTime.now()

    }
}