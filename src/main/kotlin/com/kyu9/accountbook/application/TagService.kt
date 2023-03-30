package com.kyu9.accountbook.application

import com.kyu9.accountbook.application.repository.TagRepoImpl
import com.kyu9.accountbook.application.repository.TagRepository
import com.kyu9.accountbook.common.CustomError
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
import javax.transaction.Transactional

@Service
class TagService(
    private val tagRepoImpl: TagRepoImpl,

    //for TEST
    private val tagRepository: TagRepository
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

    @Transactional
    fun removeAndSave(id: Int, dto: PostSingleTagDto): GetSingleTagDto{
        println("============================================")
        val OptionalTag = tagRepoImpl.getOptionalWithId(id.toLong()).orElseThrow(CustomError.DATA_NOT_FOUND::doThrow)

        //이렇게해놓고 쿼리보면
        //select > insert > delete 순임 = 원하는대로 안나가는듯
//        deleteTag(OptionalTag)

        //ver2 flush >> 이건 원하는대로 잘만됨
//        tagRepoImpl.deleteWithEntity(OptionalTag)
//        tagRepository.flush()

        //ver3 query >> 이건 원하는대로 잘만됨
        deleteTagQuery(OptionalTag)

        val found = tagRepoImpl.getOptionalWithId(id.toLong())

        OptionalTag.id = id.toLong()
        OptionalTag.name = dto.name!!
        OptionalTag.color = dto.color!!


        tagRepoImpl.storeEntity(
            OptionalTag
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

    @Transactional
    fun deleteTag(tag: Tag){
        tagRepoImpl.deleteWithEntity(tag)
    }

    @Transactional
    fun deleteTagQuery(tag: Tag){
        tagRepoImpl.deleteWithEntity_query(tag)
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