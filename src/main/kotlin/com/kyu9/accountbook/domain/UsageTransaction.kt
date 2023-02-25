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


@Entity
data class UsageTransaction(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usage_transaction_seq")
    @GenericGenerator(
        name = "usage_transaction_seq",
        strategy = "com.kyu9.accountbook.common.PrefixSequenceGenerator",
        parameters = [
            Parameter(name = "PrefixSequenceIdGenerator.INCREMENT_PARAM", value = "50"),
            Parameter(name = "PrefixSequenceIdGenerator.CODE_NUMBER_SEPARATOR_PARAMETER", value = "_"),
            Parameter(name = "PrefixSequenceIdGenerator.NUMBER_FORMAT_PARAMETER", value = "%05d")
        ]
    )
    val id: String?,
    val amount: Long,
    val registered: LocalDateTime,
    val registeredYYYY: String,
    val registeredYYYYMM: String,
    val registeredYYYYMMDD: String,
    val title: String,
    val content: String,
    val tagId: Long,
    val moneyType: MoneyType
): BaseEntity() {

    constructor(
        amount: Long,
        registered: LocalDateTime,
        title: String,
        content: String,
        tagId: Long,
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
        tagId = tagId,
        moneyType = MoneyType.valueOf(moneyType.uppercase())
    )
}
