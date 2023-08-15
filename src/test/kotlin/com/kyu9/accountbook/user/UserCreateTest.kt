//package com.kyu9.accountbook.user
//
//import com.kyu9.accountbook.AccountBookApplication
//import io.kotest.core.script.test
//import io.kotest.core.spec.style.DescribeSpec
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.junit.jupiter.MockitoExtension
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.MediaType
//import org.springframework.test.context.TestContextManager
//import org.springframework.test.context.junit.jupiter.SpringExtension
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.ResultActions
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//import javax.transaction.Transactional
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [AccountBookApplication::class])
//@ExtendWith(MockitoExtension::class, SpringExtension::class)
//@AutoConfigureMockMvc
//@Transactional
//class UserCreateTest (
//    @Autowired val mockMvc: MockMvc
//) : DescribeSpec({
//
//    fun postPerform(url: String, req: String, status: Int=200): ResultActions{
//        return mockMvc.perform(
//            MockMvcRequestBuilders.post(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(req)
//        )
//            .andExpect(MockMvcResultMatchers.status().`is`(status))
//    }
//
//    fun getPerform(url: String, status: Int=200): ResultActions{
//        return mockMvc.perform(
//            MockMvcRequestBuilders.get(url)
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//            .andExpect(MockMvcResultMatchers.status().`is`(status))
//    }
//
//    fun putPerform(url: String, req: String, status: Int=200): ResultActions{
//        return mockMvc.perform(
//            MockMvcRequestBuilders.put(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(req)
//        )
//            .andExpect(MockMvcResultMatchers.status().`is`(status))
//    }
//
//    fun deletePerform(url: String, status: Int=200): ResultActions{
//        return mockMvc.perform(
//            MockMvcRequestBuilders.delete(url)
//                .contentType(MediaType.APPLICATION_JSON)
//        )
//            .andExpect(MockMvcResultMatchers.status().`is`(status))
//    }
//
//
//    describe("유저 CRUD 테스트") {
//        it("#1 유저가 Create") {
//
//            postPerform(
//                "/user",
//                "{\n  \"id\": \"testId7\",\n  \"name\": \"testName\",\n  \"password\": \"testPassword\"\n}"
//            )
//        }
//
//        it("#2 #1에서 만든 유저 Get"){
//            getPerform(
//                "/user/testId7/info"
//            )
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("testId7"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("testName"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("testPassword"))
//                .andExpect{
//                    MockMvcResultMatchers.jsonPath("$.id").value("testId7")
//                    MockMvcResultMatchers.jsonPath("$.name").value("testName")
//                    MockMvcResultMatchers.jsonPath("$.password").value("testPassword")
//                    //todo 요건 실패해야하는데 잘 돌아감.... kotest으로 적용해서 테스트를 돌리면 이게 작동이 안되는거같은데 찾아봐야함 >> kotlinDsl은 적용이 안되구나
//                    MockMvcResultMatchers.jsonPath("$.password").value("testPassword2")
//                }
//                .andDo (MockMvcResultHandlers.print())
//        }
//    }
//
//}){
//    init {
//        val testContextManager = TestContextManager(this::class.java)
//        test("context should load") {
//            testContextManager.beforeTestClass()
//            testContextManager.prepareTestInstance(this)
//        }
//    }
//}