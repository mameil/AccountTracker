package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.common.BaseJpaRepo
import com.kyu9.accountbook.domain.Tag
import lombok.extern.log4j.Log4j2
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.util.Optionals
import org.springframework.data.util.Optionals.ifPresentOrElse
import org.springframework.stereotype.Service
import java.util.*


@Service
@Log4j2
class TagRepoImpl(
    @Autowired private val tagRepository: TagRepository
): BaseJpaRepo<Tag, Long, TagRepository>(
    tagRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)


    fun deleteWithEntity(tag: Tag){
        tagRepository.delete(tag)
    }

    fun deleteWithEntity_query(tag: Tag){
        tagRepository.deleteWithQuery(tag)
    }

    fun getAllTags(): List<Tag>{
    return tagRepository.findAll()
    }

    fun deleteFunctionTest(id: Long){
        //Resolved [org.springframework.transaction.UnexpectedRollbackException: Transaction silently rolled back because it has been marked as rollback-only]
        //근데 결과적으로는 400이 떨어지는 모습임 >> 재현은 완료
        //@Transactional이 붙어있는 메소드에서 예외가 발생하면 자동으로 해당 메소드에서 발생한 트랜잭션을 롤백시키고 예외를 상위 호출자에게 전파해버린다 그래서 아무리 catch 해도 에러가 발생한다
        //해결방법 1. @Transactional에서 noRollback 옵션을 사용해서 해당 예외에서는 롤백하지 않도록 설정한다 >> 근데 이건 좀 위험할 듯.. 다른 로직에서 어떤 이슈가 발생할지 모름
//        try{
//            tagRepository.deleteById(id)
//        }catch (e: Exception){
//            logger.error(e.message)
//            logger.error("catch로 잘 잡힌거야아")
//        }
//        logger.info("이 로그가 나오면 정상적으로 catch 되고 진행되었다는 의미")

        //해결방법 2. delete 로직을 애초에 findById 해서 있으면 delete 날리는 방식으로 수정 ifPresentOrElse 으로 사용하자
//        tagRepository.findById(id).ifPresentOrElse(
//            {
//                tagRepository.delete(it)
//            },
//            {
//                logger.error("$id 로 저장되어있는 tag가 존재하지 않아 삭제되지 않았습니다")
//            }
//        )
    }

//    @Cacheable(value = ["tags"])
    override fun storeEntity(t: Tag): Tag {
        return super.storeEntity(t)
    }

//    @CacheEvict(value = ["tags"], allEntries = true)
    override fun removeEntityWithId(id: Long) {
        super.removeEntityWithId(id)
    }

//    @Cacheable(value = ["tags"])
    override fun getEntityWithId(id: Long): Tag {
        return super.getEntityWithId(id)
    }
}