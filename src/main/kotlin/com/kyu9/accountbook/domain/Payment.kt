package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val amount: Long,
    val registered: LocalDateTime,
    val registeredYYYYMMDD: String,
    val title: String,
    val content: String,
    val categoryId: Long,
): BaseEntity() {

}
