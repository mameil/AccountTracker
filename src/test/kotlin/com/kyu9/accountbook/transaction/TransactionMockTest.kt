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

    @Test
    fun findAllTranTest(){
        postPerform(
                "거래 생성 되는거 확인",
                "/transaction",
                "{\n  \"amount\": 1111,\n  \"registeredAt\": \"${MyTime.toYyyymmddhhmmss(MyTime.now())}\",\n  \"title\": \"1111\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
                "거래 생성 되는거 확인2",
                "/transaction",
                "{\n  \"amount\": 2222,\n  \"registeredAt\": \"${MyTime.toYyyymmddhhmmss(MyTime.now())}\",\n  \"title\": \"2222\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
                "거래 생성 되는거 확인3",
                "/transaction",
                "{\n  \"amount\": 3333,\n  \"registeredAt\": \"${MyTime.toYyyymmddhhmmss(MyTime.now())}\",\n  \"title\": \"3333\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        getPerform(
                "모든 거래를 확인해보자",
                "/transaction/all"
        )
                .andExpect{
                    MockMvcResultMatchers.jsonPath("$[0].amount").value(3333)
                    MockMvcResultMatchers.jsonPath("$[0].title").value("3333")
                    MockMvcResultMatchers.jsonPath("$[1].amount").value(2222)
                    MockMvcResultMatchers.jsonPath("$[1].title").value("2222")
                    MockMvcResultMatchers.jsonPath("$[2].amount").value(1111)
                    MockMvcResultMatchers.jsonPath("$[2].title").value("1111")
                }
    }

    @Test
    fun post_register_transaction_with_yyyymmdd(){

        postPerform(
                "거래 등록 시 yyyymmdd 필드 추가된거 어떻게 들어가는지 확인",
                "/transaction",
                "{\n  \"amount\": 3333,\n  \"registeredAtYyyymmdd\": \"20230329\",\n  \"title\": \"3333\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        getPerform(
                "모든 거래로 조회했을 때 yyyymmdd 필드가 적용되었는지 확인",
                "/transaction/all"
        )
                .andExpect{
                    MockMvcResultMatchers.jsonPath("$[0].registeredAt").value("20230329000000")
                }
    }

    @Test
    fun post_register_transaction_with_userId(){
        postPerform(
                "거래 등록 시 yyyymmdd 필드 추가된거 어떻게 들어가는지 확인",
                "/transaction",
                "{\n  \"amount\": 3333,\n  \"userId\": \"testIDIDID\",\n  \"registeredAtYyyymmdd\": \"20230329\",\n  \"title\": \"3333\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        getPerform(
                "모든 거래로 조회했을 때 yyyymmdd 필드가 적용되었는지 확인",
                "/transaction/all"
        )
                .andExpect{
                    MockMvcResultMatchers.jsonPath("$[0].userId").value("testIDIDID")
                }
    }
}