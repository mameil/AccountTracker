package com.kyu9.accountbook.swagger

import com.kyu9.accountbook.swagger.api.TagApiDelegate
import com.kyu9.accountbook.swagger.model.GetSingleTagDto
import com.kyu9.accountbook.swagger.model.PostSingleTagDto
import org.springframework.http.ResponseEntity

class TagApiControllerImpl: TagApiDelegate{
    override fun postTag(postSingleTagDto: PostSingleTagDto): ResponseEntity<GetSingleTagDto> {
        return super.postTag(postSingleTagDto)
    }
}