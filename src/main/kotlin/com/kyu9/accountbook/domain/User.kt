package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import lombok.Builder
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "USER_INFO")
@Builder
data class User(
    @Id
    var id: String,
    var password: String,
    var name: String,
): BaseEntity() {

}
