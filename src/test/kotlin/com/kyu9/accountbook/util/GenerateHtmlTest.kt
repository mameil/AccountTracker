package com.kyu9.accountbook.util

import com.kyu9.accountbook.common.TestFrame
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class GenerateHtmlTest: TestFrame() {

    @Test
    @DisplayName("html 생성")
    fun generateHtml(){
        val keys = listOf("id", "name", "password")
        val values = listOf("testId7", "testName", "testPassword")

        postPerform(
            "create html",
            "/util/html",
            "{\n  \"keys\": [\"trType\", \"value\", \"password\"],\n  \"values\": \"\"\n}"
        )
            .andExpect {
                MockMvcResultMatchers.jsonPath("\$.htmlString").exists()
            }
    }
}