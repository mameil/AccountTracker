package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import com.kyu9.accountbook.common.DefaultValue
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.Builder

@Entity
@Builder
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: String,
    val password: String,
    val name: String,
): BaseEntity() {

}
