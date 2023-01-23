package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Tag(
    @Id
    val id: Long,
    val name: String,
    val color: String,
    ): BaseEntity() {

}
