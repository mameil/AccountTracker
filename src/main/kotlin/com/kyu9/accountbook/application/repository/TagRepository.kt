package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.domain.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import javax.persistence.LockModeType

@Repository
interface TagRepository: JpaRepository<Tag, Long> {

    @Modifying(clearAutomatically = true)
    @Query("delete from TAG t where t = ?1")
    fun deleteWithQuery(tag: Tag)


//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    override fun findById(id: Long): Optional<Tag>
}