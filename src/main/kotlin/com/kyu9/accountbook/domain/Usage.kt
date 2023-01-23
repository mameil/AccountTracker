package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import com.kyu9.accountbook.common.DefaultValue
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Usage(
    @Id
    val id: Long,
    val amount: Long,
    val registered: LocalDateTime,
    val registeredYYYYMMDD: String,
    val title: String,
    val content: String,
    val categoryId: Long,
): BaseEntity() {

}
