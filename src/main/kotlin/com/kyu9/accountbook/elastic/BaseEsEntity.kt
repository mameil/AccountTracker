package com.kyu9.accountbook.elastic

import com.kyu9.accountbook.common.MyTime
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

open class BaseEsEntity {
    @Field(type = FieldType.Date, format = arrayOf(DateFormat.custom), pattern = arrayOf("yyyy-MM-dd'T'HH:mm:ss'Z'"))
    val created: LocalDateTime = MyTime.now()
    val creator: String = "KD.SHIM"
}