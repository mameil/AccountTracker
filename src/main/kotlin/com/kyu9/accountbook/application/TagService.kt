package com.kyu9.accountbook.application

import com.kyu9.accountbook.common.CustomError
import com.kyu9.accountbook.common.MyTime
import com.kyu9.accountbook.domain.Tag
import com.kyu9.accountbook.swagger.model.GetSingleTagDto
import com.kyu9.accountbook.swagger.model.PostSingleTagDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class TagService(
    private val tagRepoImpl: TagRepoImpl,
) {
    fun storeTag(dto: PostSingleTagDto): GetSingleTagDto{
//        log.info("")

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

    fun removeTagError(id: Int){
        tagRepoImpl.deleteFunctionTest(id.toLong())
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
        tagRepoImpl.deleteEntityByQuery(tag)
    }

//    @CachePut(value = ["tags"])
    fun updateEntity(id: Int, dto: PostSingleTagDto): Tag{
        tagRepoImpl.getOptionalWithId(id.toLong()).ifPresent{
            it.name = dto.name!!
            it.color = dto.color!!
            tagRepoImpl.storeEntity(it)
        }

        return tagRepoImpl.getEntityWithId(id.toLong())
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    fun forDeadLockMinus(tagId1: Long){
        val e1 = tagRepoImpl.getEntityWithId(tagId1)

        println("$e1 Before======================")
        var eName1: Int = e1.name.toInt() + 1

        e1.name = eName1.toString()

        tagRepoImpl.storeEntity(e1)
        println("$e1 After======================")
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    fun forDeadLockPlus(tagId2: Long){
        val e2 = tagRepoImpl.getEntityWithId(tagId2)

        println("$e2 Before======================")
        var eName2: Int = e2.name.toInt() - 1

        e2.name = eName2.toString()

        tagRepoImpl.storeEntity(e2)
        println("$e2 After======================")
    }

    @Transactional
    fun minusTransaction(tagId: Long, tagId2: Long){
        forDeadLockMinus(tagId)

        forDeadLockMinus(tagId2)
    }

    @Transactional
    fun plusTransaction(tagId2: Long, tagId: Long){
        forDeadLockPlus(tagId2)

        forDeadLockPlus(tagId)
    }
}