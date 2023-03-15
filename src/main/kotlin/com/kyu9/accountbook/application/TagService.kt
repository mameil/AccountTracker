package com.kyu9.accountbook.application

import com.kyu9.accountbook.application.repository.TagRepoImpl
import com.kyu9.accountbook.common.MyTime
import com.kyu9.accountbook.domain.Tag
import com.kyu9.accountbook.swagger.model.GetListTagDto
import com.kyu9.accountbook.swagger.model.GetSingleTagDto
import com.kyu9.accountbook.swagger.model.PostSingleTagDto
import lombok.RequiredArgsConstructor
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class TagService(
    private val tagRepoImpl: TagRepoImpl
) {
    fun storeTag(dto: PostSingleTagDto): GetSingleTagDto{
        tagRepoImpl.storeEntity(
            Tag.of(
                dto.name!!,
                dto.color!!
            )
        ).let {
            return GetSingleTagDto(
                it.id?.toInt(),
                it.name,
                it.color,
                MyTime.toYyyymmddhhmmss(it.created),
                MyTime.toYyyymmddhhmmss(it.updated)
            )
        }
    }

    fun getAllTags(): List<GetSingleTagDto>{
        return tagRepoImpl.getAllTags().map {
            GetSingleTagDto(
                it.id?.toInt(),
                it.name,
                it.color,
                MyTime.toYyyymmddhhmmss(it.created),
                MyTime.toYyyymmddhhmmss(it.updated)
            )
        }
    }

    fun removeTag(id: Int){
        tagRepoImpl.removeEntityWithId(id.toLong())
    }

    fun updateTag(id: Int, dto: PostSingleTagDto): GetSingleTagDto{
        return updateEntity(id, dto).let {
            GetSingleTagDto(
                it.id?.toInt(),
                it.name,
                it.color,
                MyTime.toYyyymmddhhmmss(it.created),
                MyTime.toYyyymmddhhmmss(it.updated)
            )
        }
    }

    @CachePut(value = ["tags"])
    fun updateEntity(id: Int, dto: PostSingleTagDto): Tag{
        tagRepoImpl.getOptionalWithId(id.toLong()).ifPresent{
            it.name = dto.name!!
            it.color = dto.color!!
            tagRepoImpl.storeEntity(it)
        }

        return tagRepoImpl.getEntityWithId(id.toLong())
    }
}