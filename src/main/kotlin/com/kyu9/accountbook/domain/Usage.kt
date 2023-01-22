package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import com.kyu9.accountbook.common.DefaultValue
import java.time.LocalDateTime

data class Usage(
    val id: Long,
    val amount: Long,
    val registered: LocalDateTime,
    val registeredYYYYMMDD: String,
    val title: String,
    val content: String,
    val categoryId: Long,
): BaseEntity() {

}
