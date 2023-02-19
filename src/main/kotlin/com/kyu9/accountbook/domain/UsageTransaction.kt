package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import com.kyu9.accountbook.common.MyTime
import com.kyu9.accountbook.domain.properties.MoneyType
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.Parameter
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.SequenceGenerator


@Entity
data class UsageTransaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CustomSequenceGenerator")
    @GenericGenerator(
        name = "CustomSequenceGenerator",
        strategy = "com.kyu9.accountbook.common.CustomSequenceGenerator",
//        parameters = [
//            Parameter(name = "sequence_name", value = "usage_transaction_seq"),
//        ]
    )
    val id: String?,
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

    constructor(
        amount: Long,
        registered: LocalDateTime,
        title: String,
        content: String,
        categoryId: Long,
        moneyType: String
    ): this(
        id = null,
        amount = amount,
        registered = registered,
        registeredYYYY = MyTime.toYyyy(registered),
        registeredYYYYMM = MyTime.toYyyyMm(registered),
        registeredYYYYMMDD = MyTime.toYyyyMmDd(registered),
        title = title,
        content = content,
        categoryId = categoryId,
        moneyType = MoneyType.valueOf(moneyType.uppercase())
    )
}
