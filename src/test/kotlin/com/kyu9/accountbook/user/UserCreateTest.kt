package com.kyu9.accountbook.user

import com.kyu9.accountbook.AccountBookApplication
import io.kotest.core.spec.style.DescribeSpec
import io.swagger.v3.oas.models.Components
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import javax.transaction.Transactional

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [AccountBookApplication::class])
@ExtendWith(MockitoExtension::class, SpringExtension::class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class UserCreateTest (
    @Autowired val mockMvc: MockMvc
) : DescribeSpec({


    //todo > 현재는 real db에 들어감 -> mock db로 변경해야함 아니면 다 지워주는거 작업 필요함
    describe("유저를 생성할 수 있다") {
        it("유저가 생성된다") {

            mockMvc.perform(
                MockMvcRequestBuilders.post("/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\n  \"id\": \"testId7\",\n  \"name\": \"testName\",\n  \"password\": \"testPassword\"\n}")
            )
                .andExpect(MockMvcResultMatchers.status().isOk)


        }
    }

})