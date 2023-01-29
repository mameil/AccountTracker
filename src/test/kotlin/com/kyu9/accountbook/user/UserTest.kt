package com.kyu9.accountbook.user

import com.kyu9.accountbook.AccountBookApplication
import com.kyu9.accountbook.common.TestFrame
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcResultHandlersDsl
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class UserTest(
): TestFrame() {
    @Test
    @DisplayName("사용자를 만든다")
    fun createUser(){
        postPerform(
            "/user",
            "{\n  \"id\": \"testId7\",\n  \"name\": \"testName\",\n  \"password\": \"testPassword\"\n}"
        )
    }

    @Test
    @DisplayName("사용자를 만들고 그 사용자를 조회한다")
    fun getUser(){
        createUser()
        getPerform("/user/testId7/info")
            .andExpect {
                MockMvcResultMatchers.jsonPath("\$.id").value("testId7")
                MockMvcResultMatchers.jsonPath("\$.name").value("testName")
                MockMvcResultMatchers.jsonPath("\$.password").value("testPassword")
                MockMvcResultMatchers.jsonPath("$.createdAt").exists()
                MockMvcResultMatchers.jsonPath("$.updatedAt").exists()
            }
    }
}