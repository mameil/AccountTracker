package com.kyu9.accountbook.transaction

import com.kyu9.accountbook.common.MyTime
import com.kyu9.accountbook.common.TestFrame
import com.kyu9.accountbook.swagger.model.PostTransResponseDto
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class TransactionMockTest: TestFrame() {

    @Test
    fun tranSaveTest(){

        postPerform(
            "거래 생성 되는거 확인",
            "/transaction",
            "{\n  \"amount\": 1234,\n  \"registeredAt\": \"${MyTime.toYyyymmddhhmmss(MyTime.now())}\",\n  \"title\": \"편의점 - 병 커피\",\n  \"content\": \"병으로된 커피가 먹고 싶으니까..?\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
            "거래 생성 되는거 확인",
            "/transaction",
            "{\n  \"amount\": 1234,\n  \"registeredAt\": \"${MyTime.toYyyymmddhhmmss(MyTime.now())}\",\n  \"title\": \"편의점 - 병 커피2\",\n  \"content\": \"병으로된 커피가 먹고 싶으니까..?\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )
    }

    @Test
    fun tranGetTest(){
        val tid = postPerform(
            "거래 생성 되는거 확인",
            "/transaction",
            "{\n  \"amount\": 1234,\n  \"registeredAt\": \"${MyTime.toYyyymmddhhmmss(MyTime.now())}\",\n  \"title\": \"편의점 - 병 커피\",\n  \"content\": \"병으로된 커피가 먹고 싶으니까..?\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )
            .andReturn().response.contentAsString.let {
                objectMapper.readValue(it, PostTransResponseDto::class.java)
            }.id


        getPerform(
            "거래 조회 되는거 확인",
            "/transaction/$tid"
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.jsonPath("$.utid").value(tid))
            .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(1234))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("편의점 - 병 커피"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("병으로된 커피가 먹고 싶으니까..?"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.tagId").value(1234))
            .andExpect(MockMvcResultMatchers.jsonPath("$.moneyType").value("MINE"))

    }

    @Test
    fun tranDeleteTest(){
        val tid = postPerform(
            "거래 생성 되는거 확인",
            "/transaction",
            "{\n  \"amount\": 1234,\n  \"registeredAt\": \"${MyTime.toYyyymmddhhmmss(MyTime.now())}\",\n  \"title\": \"테스트전용\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )
            .andReturn().response.contentAsString.let {
                objectMapper.readValue(it, PostTransResponseDto::class.java)
            }.id

        deletePerform(
            "거래 삭제 되는거 확인",
            "/transaction/$tid"
        )

        getPerform(
            "거래 없는거 확인",
            "/transaction/$tid",
            400
        )
    }
}