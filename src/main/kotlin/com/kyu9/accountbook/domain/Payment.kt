package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


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
