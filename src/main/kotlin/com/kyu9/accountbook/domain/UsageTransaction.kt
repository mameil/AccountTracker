package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import com.kyu9.accountbook.domain.properties.MoneyType
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
data class UsageTransaction(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val amount: Long,
    val registered: LocalDateTime,
    val registeredYYYY: String,
    val registeredYYYYMM: String,
    val registeredYYYYMMDD: String,
    val title: String,
    val content: String,
    val categoryId: Long,
    val moneyType: MoneyType
): BaseEntity() {

}
