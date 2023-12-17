package com.kyu9.accountbook.domain

import com.kyu9.accountbook.common.BaseEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "TAG")
data class Tag(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long?,
    var name: String,
    var color: String,
    ): BaseEntity() {
        companion object{
            fun of(name: String, color: String): Tag{
                return Tag(
                    id = null,
                    name = name,
                    color = color
                )
            }
        }
}
