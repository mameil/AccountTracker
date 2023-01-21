package com.kyu9.accountbook.common

import jakarta.persistence.PostUpdate
import java.time.LocalDateTime

abstract class BaseEntity(
    open val created: LocalDateTime = LocalDateTime.now(),
    open var updated: LocalDateTime = LocalDateTime.now()
) {
    @PostUpdate
    fun postUpdate() {
        updated = LocalDateTime.now()
    }
}