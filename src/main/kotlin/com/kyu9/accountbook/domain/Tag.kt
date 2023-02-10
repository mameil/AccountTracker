package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Tag(
    //todo 요거 엔티티가 맞냐.. 아니면 ENUM 이 맞냐.. 생각필요함
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String,
    val color: String,
    ): BaseEntity() {

}
