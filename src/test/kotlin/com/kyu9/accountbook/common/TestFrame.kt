package com.kyu9.accountbook.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.kyu9.accountbook.AccountBookApplication
import com.kyu9.accountbook.swagger.model.GetUserResponseDto
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.random.Random

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [AccountBookApplication::class])
@ExtendWith(MockitoExtension::class, SpringExtension::class)
@AutoConfigureMockMvc
class TestFrame(
) {
    @Autowired lateinit var mockMvc: MockMvc
    val objectMapper: ObjectMapper = ObjectMapper()

    fun postPerform(desc: String="", url: String, req: String, status: Int=200): ResultActions {
        if(desc != "") println(desc)
        return mockMvc.perform(
            MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(req)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(status))
    }

    fun getPerform(desc: String = "", url: String, status: Int=200): ResultActions {
        if(desc != "") println(desc)
        return mockMvc.perform(
            MockMvcRequestBuilders.get(url)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(status))
    }

    fun putPerform(desc: String = "", url: String, req: String, status: Int=200): ResultActions {
        if(desc != "") println(desc)
        return mockMvc.perform(
            MockMvcRequestBuilders.put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(req)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(status))
    }

    fun deletePerform(desc: String = "", url: String, status: Int=200): ResultActions {
        if(desc != "") println(desc)
        return mockMvc.perform(
            MockMvcRequestBuilders.delete(url)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().`is`(status))
    }

    fun createRandomUser(): GetUserResponseDto{
        val random = Random.nextInt()

        return objectMapper.readValue(
            postPerform(
                "키워드 번호 ${random}인 사용자 생성",
                "/user",
                "{\n  \"id\": \"${random}Id\",\n  \"name\": \"${random}Name\",\n  \"password\": \"${random}Pwd\"\n}"
            )
                .andReturn().response.contentAsString,
            GetUserResponseDto::class.java
        )
    }
}