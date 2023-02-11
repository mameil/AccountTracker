package com.kyu9.accountbook.util

import com.kyu9.accountbook.common.TestFrame
import io.kotest.core.spec.style.AnnotationSpec
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class GenerateHtmlTest: TestFrame() {

    @Test
    @DisplayName("html 생성")
    @Disabled
    fun generateHtml(){
        val keys = listOf("id", "name", "password")
        val values = listOf("testId7", "testName", "testPassword")

        postPerform(
            "create html",
            "/util/html/table/generate",
            "{\n  \"keys\": [\"trType\", \"value\", \"password\"],\n  \"values\": \"\"\n}"
        )
            .andExpect {
                MockMvcResultMatchers.jsonPath("\$.htmlString").exists()
            }
    }
}