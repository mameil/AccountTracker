//package com.kyu9.accountbook.transaction
//
//import com.kyu9.accountbook.AccountBookApplication
//import com.kyu9.accountbook.common.MyTime
//import io.kotest.core.spec.style.StringSpec
//import io.kotest.core.spec.style.WordSpec
//import io.kotest.core.test.TestContext
//import kotlinx.coroutines.runBlocking
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.junit.jupiter.MockitoExtension
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.MediaType
//import org.springframework.test.context.junit.jupiter.SpringExtension
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = [AccountBookApplication::class])
//@ExtendWith(MockitoExtension::class, SpringExtension::class)
//
//class TransactionTest(
//    @Autowired val mockMvc: MockMvc
//): WordSpec() {
//
//    init{
//        "transaction api들" should {
//            "정상적인 req가 들어오면 정상적으로 200을 뱉어야만 한다"{
//                //todo... 이거 왜안될까....
////                mockMvc.perform(
////                    MockMvcRequestBuilders.post("/transaction")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content("{\n  \"amount\": 1234,\n  \"registeredAt\": \"${MyTime.now()}\",\n  \"title\": \"편의점 - 병 커피\",\n  \"content\": \"병으로된 커피가 먹고 싶으니까..?\",\n  \"categoryId\": 1234,\n  \"moneyType\": \"MY_CASH\"\n}")
////                )
////                    .andExpect(MockMvcResultMatchers.status().isOk)
//            }
//
//            "비정상적인 케이스 - 뭔가 빠져서 들어오는 경우"{
//
//            }
//        }
//    }
//}