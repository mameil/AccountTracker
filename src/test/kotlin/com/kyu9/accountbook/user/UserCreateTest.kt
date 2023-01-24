package com.kyu9.accountbook.user

import io.kotest.core.spec.style.BehaviorSpec
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension::class, SpringExtension::class)
class UserCreateTest: BehaviorSpec({

    lateinit var mockmvc: MockMvc


    given("유저를 생성할 수 있다"){
        `when`("기본적인 유저 생성"){


            `then`("유저가 생성된다"){
                mockmvc.perform(MockMvcRequestBuilders.post("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n  \"id\": \"testId\",\n  \"name\": \"testName\",\n  \"password\": \"testPassword\"\n}")
                )
                    .andExpect(MockMvcResultMatchers.status().isOk)


            }
        }
    }


}) {
}