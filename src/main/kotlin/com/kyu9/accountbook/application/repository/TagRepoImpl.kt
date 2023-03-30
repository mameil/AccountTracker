package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.common.BaseJpaRepo
import com.kyu9.accountbook.domain.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class TagRepoImpl(
    @Autowired private val tagRepository: TagRepository
): BaseJpaRepo<Tag, Long, TagRepository>(
    tagRepository
) {
    fun deleteWithEntity(tag: Tag){
        tagRepository.delete(tag)
    }

    fun deleteWithEntity_query(tag: Tag){
        tagRepository.deleteWithQuery(tag)
    }

    fun getAllTags(): List<Tag>{
    return tagRepository.findAll()
    }

//    @Cacheable(value = ["tags"])
    override fun storeEntity(t: Tag): Tag {
        return super.storeEntity(t)
    }

    @CacheEvict(value = ["tags"], allEntries = true)
    override fun removeEntityWithId(id: Long) {
        super.removeEntityWithId(id)
    }

    @Cacheable(value = ["tags"])
    override fun getEntityWithId(id: Long): Tag {
        return super.getEntityWithId(id)
    }
}