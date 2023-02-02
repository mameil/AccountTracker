package com.kyu9.accountbook.swagger

import com.kyu9.accountbook.swagger.api.UtilApiDelegate
import com.kyu9.accountbook.swagger.model.GenerateHtmlTableRequestDto
import com.kyu9.accountbook.swagger.model.GenerateHtmlTableResponseDto
import com.kyu9.accountbook.utils.HtmlGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UtilApiControllerImpl(
    @Autowired val htmlGenerator: HtmlGenerator
): UtilApiDelegate {
    override fun generateHtmlTable(generateHtmlTableRequestDto: GenerateHtmlTableRequestDto): ResponseEntity<GenerateHtmlTableResponseDto> {
        val htmlString: String =
            htmlGenerator.generateHtmlFromJson(generateHtmlTableRequestDto.keys!!, generateHtmlTableRequestDto.values!!)

        val resp = GenerateHtmlTableResponseDto(htmlString)

        return ResponseEntity.ok(resp)
    }
}