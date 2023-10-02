package com.kyu9.accountbook.swagger

import com.kyu9.accountbook.elastic.TestInfo
import com.kyu9.accountbook.elastic.TestInfoService
import com.kyu9.accountbook.swagger.api.TestApiDelegate
import com.kyu9.accountbook.swagger.model.TestInfoDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller

@Controller
class TestApiControllerImpl(
        private val testInfoService: TestInfoService
): TestApiDelegate {
    override fun getTestInfo(name: String): ResponseEntity<TestInfoDto> {
        val testInfo = testInfoService.getTestInfo(name)
        return ResponseEntity.ok(TestInfoDto(testInfo.name, testInfo.desc))
    }

    override fun postTestInfo(testInfoDto: TestInfoDto): ResponseEntity<Unit> {
        testInfoService.save(TestInfo(testInfoDto.name!!, testInfoDto.desc!!))
        return ResponseEntity(HttpStatus.OK)
    }
}