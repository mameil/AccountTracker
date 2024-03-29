package com.kyu9.accountbook.swagger

import com.kyu9.accountbook.application.TagService
import com.kyu9.accountbook.swagger.api.TagApiDelegate
import com.kyu9.accountbook.swagger.model.GetListTagDto
import com.kyu9.accountbook.swagger.model.GetSingleTagDto
import com.kyu9.accountbook.swagger.model.PostSingleTagDto
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.UnexpectedRollbackException
import java.math.BigDecimal
import javax.transaction.Transactional

@Service
class TagApiControllerImpl(
    private val tagService: TagService
): TagApiDelegate{
    override fun postTag(postSingleTagDto: PostSingleTagDto): ResponseEntity<GetSingleTagDto> {
        return ResponseEntity.ok(tagService.storeTag(postSingleTagDto))
    }

    override fun getAllTags(): ResponseEntity<List<GetSingleTagDto>> {
        return ResponseEntity.ok(tagService.getAllTags())
    }

    @Transactional
    override fun deleteSingleTag(tagId: BigDecimal): ResponseEntity<Unit> {
        //todo difference between Kotlin.Int and BidDecimal..?
        tagService.removeTag(tagId.intValueExact())
//        tagService.removeTagError(tagId.intValueExact()) //for @Transactional rollback TEST

        return ResponseEntity(HttpStatus.OK)
    }

    override fun updateSingleTag(
        tagId: BigDecimal,
        postSingleTagDto: PostSingleTagDto
    ): ResponseEntity<GetSingleTagDto> {
        return ResponseEntity.ok(tagService.updateTag(tagId.intValueExact(), postSingleTagDto))
    }

    override fun postAdminTag(tagId: BigDecimal, postSingleTagDto: PostSingleTagDto): ResponseEntity<GetSingleTagDto> {
        return ResponseEntity.ok(tagService.removeAndSave(tagId.intValueExact(), postSingleTagDto))
    }
}