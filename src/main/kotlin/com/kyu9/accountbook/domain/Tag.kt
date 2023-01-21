package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import java.time.LocalDateTime

data class Tag(
    val id: Long = 0L,
    val name: String = "",
    val color: String = "",

    ): BaseEntity() {

}
