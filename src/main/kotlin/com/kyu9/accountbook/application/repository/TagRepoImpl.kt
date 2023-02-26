package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.common.BaseJpaRepo
import com.kyu9.accountbook.domain.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TagRepoImpl(
    @Autowired private val tagRepository: TagRepository
): BaseJpaRepo<Tag, Long, TagRepository>(
    tagRepository
) {

    fun getAllTags(): List<Tag>{
        return tagRepository.findAll()
    }
}