package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import java.time.LocalDateTime

data class Usage(
    val id: Long = 0L,
    val amount: Long = 0L,
    val registered: LocalDateTime = LocalDateTime.now(),
    val registeredYYYYMMDD: String = "",
    val title: String = "",
    val content: String = "",
    val categoryId: Long = 0L,

): BaseEntity() {

}
