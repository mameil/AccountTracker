package com.kyu9.accountbook.common

import java.time.LocalDateTime
import javax.persistence.PostUpdate

abstract class BaseEntity(
    open val created: LocalDateTime = LocalDateTime.now(),
    open var updated: LocalDateTime = LocalDateTime.now()
) {
    @PostUpdate
    fun postUpdate() {
        updated = LocalDateTime.now()
    }
}