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
            "사용자 생성",
            "/user",
            "{\n  \"id\": \"testId7\",\n  \"name\": \"testName\",\n  \"password\": \"testPassword\"\n}"
        )
    }

    @Test
    @DisplayName("사용자를 만들고 그 사용자를 조회한다")
    fun getUser(){
        createUser()
        getPerform("조회", "/user/testId7/info")
            .andExpect {
                MockMvcResultMatchers.jsonPath("\$.id").value("testId7")
                MockMvcResultMatchers.jsonPath("\$.name").value("testName")
                MockMvcResultMatchers.jsonPath("\$.password").value("testPassword")
                MockMvcResultMatchers.jsonPath("$.createdAt").exists()
                MockMvcResultMatchers.jsonPath("$.updatedAt").exists()
            }
    }

    @Test
    @DisplayName("사용자를 만들고 수정하고 수정한 데이터를 조회한다")
    fun updateUser(){
        val randomKey = createRandomUser()

        putPerform(
            "아이디 ${randomKey.id}인 사용자 업데이트",
            "/user/${randomKey.id}",
            "{\n  \"id\": \"${randomKey.id}__\",\n  \"name\": \"${randomKey.name}__\",\n  \"password\": \"${randomKey.password}__\"\n}"
        )
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("${randomKey.id}__"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("${randomKey.name}__"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("${randomKey.password}__"))

        getPerform(
            "${randomKey.id} 요걸로 조회하면 password, name의 업데이트가 쳐져있어야함",
            "/user/${randomKey.id}/info"
        )
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("${randomKey.id}"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("${randomKey.name}__"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("${randomKey.password}__"))

    }

    @Test
    @DisplayName("사용자를 등록하고 삭제할 수 있다")
    fun deleteUser(){
        val user = createRandomUser()

        getPerform(
            "등록한 사용자를 조회한다",
            "/user/${user.id}/info"
        )
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("${user.id}"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("${user.name}"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("${user.password}"))


        deletePerform(
            "등록한 사용자를 삭제한다",
            "/user/${user.id}"
        )

        //todo exception 구현하고 해야함
//        getPerform(
//            "등록한 사용자를 조회한다",
//            "/user/${user.id}/info",
//            400
//        )
        mockMvc.perform(
            MockMvcRequestBuilders.get("/user/${user.id}/info")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

}