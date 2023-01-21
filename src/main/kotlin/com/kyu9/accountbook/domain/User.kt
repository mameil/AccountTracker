package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import com.kyu9.accountbook.common.DefaultValue

data class User(
    val id: String = DefaultValue.getDefaultValue(String::class.java).toString(),
    val password: String = "",
    val name: String = "",

): BaseEntity() {

}
