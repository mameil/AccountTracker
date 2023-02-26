package com.kyu9.accountbook.swagger

import com.kyu9.accountbook.application.TagService
import com.kyu9.accountbook.swagger.api.TagApiDelegate
import com.kyu9.accountbook.swagger.model.GetSingleTagDto
import com.kyu9.accountbook.swagger.model.PostSingleTagDto
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class TagApiControllerImpl(
    private var tagService: TagService
): TagApiDelegate{
    override fun postTag(postSingleTagDto: PostSingleTagDto): ResponseEntity<GetSingleTagDto> {
        return ResponseEntity.ok(tagService.storeTag(postSingleTagDto))
    }

    override fun getAllTags(): ResponseEntity<List<GetSingleTagDto>> {
        return ResponseEntity.ok(tagService.getAllTags())
    }

    override fun deleteSingleTag(tagId: Int): ResponseEntity<Unit> {
        tagService.removeTag(tagId)
        return ResponseEntity(HttpStatus.OK)
    }
}