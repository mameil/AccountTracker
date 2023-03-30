package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.domain.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface TagRepository: JpaRepository<Tag, Long> {

    @Modifying(clearAutomatically = true)
    @Query("delete from Tag t where t = ?1")
    fun deleteWithQuery(tag: Tag)
}