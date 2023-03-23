package com.kyu9.accountbook.view

import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/")
class SwaggerController {

    @RequestMapping(method = [RequestMethod.GET], value = ["sample"], produces = [MediaType.TEXT_PLAIN_VALUE])
    @ApiOperation(value = "Test Sample", tags = ["sample"])
    fun sample(@RequestParam param: String?): ResponseEntity<*> {
        return ResponseEntity.ok(param)
    }

}