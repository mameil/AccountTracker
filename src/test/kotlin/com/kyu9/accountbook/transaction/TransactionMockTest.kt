package com.kyu9.accountbook.transaction

import com.kyu9.accountbook.common.MyTime
import com.kyu9.accountbook.common.TestFrame
import com.kyu9.accountbook.swagger.model.PostTransResponseDto
import org.hamcrest.Matchers
import org.junit.jupiter.api.DisplayName
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

    @Test
    fun find_JSONERROR(){
        val req = "{\n  \"amount\": 3333,\n  \"userId\": \"testIDIDID\",\n  \"registeredAtYyyymmdd\": \"20230329\",\n  \"title\": \"3333\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"ERROR\"\n}"
        postPerform(
                "거래 등록 시 yyyymmdd 필드 추가된거 어떻게 들어가는지 확인",
                "/transaction",
                req,
                400
        )
                .andDo(MockMvcResultHandlers.print())

    }

    @Test
    @DisplayName("원별 사용내역 조회 api 테스트")
    fun inquiry_monthly_transaction(){
        postPerform(
                "거래 등록 - 202211",
                "/transaction",
                "{\n  \"amount\": 3333,\n  \"userId\": \"testIDIDID\",\n  \"registeredAtYyyymmdd\": \"20221101\",\n  \"title\": \"3333\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
                "거래 등록 - 202212",
                "/transaction",
                "{\n  \"amount\": 3333,\n  \"userId\": \"testIDIDID\",\n  \"registeredAtYyyymmdd\": \"20221201\",\n  \"title\": \"3333\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
                "거래 등록 - 202301",
                "/transaction",
                "{\n  \"amount\": 3333,\n  \"userId\": \"testIDIDID\",\n  \"registeredAtYyyymmdd\": \"20230101\",\n  \"title\": \"3333\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
                "거래 등록 - 202302",
                "/transaction",
                "{\n  \"amount\": 3333,\n  \"userId\": \"testIDIDID\",\n  \"registeredAtYyyymmdd\": \"20230201\",\n  \"title\": \"3333\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
                "거래 등록 - 202303",
                "/transaction",
                "{\n  \"amount\": 3333,\n  \"userId\": \"testIDIDID\",\n  \"registeredAtYyyymmdd\": \"20230301\",\n  \"title\": \"3333\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        getPerform(
                "월별 거래 조회 - 월별로 나오는지 확인",
                "/transaction/monthly"
        )
                .andExpect(MockMvcResultMatchers.jsonPath("transList[0].yyyymmdd").value("202211"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[0].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[1].yyyymmdd").value("202212"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[1].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[2].yyyymmdd").value("202301"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[2].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[3].yyyymmdd").value("202302"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[3].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[4].yyyymmdd").value("202303"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[4].mineAmount").value("3333"))

        postPerform(
                "거래 등록 - 202303 합산",
                "/transaction",
                "{\n  \"amount\": 7777,\n  \"userId\": \"testIDIDID\",\n  \"registeredAtYyyymmdd\": \"20230301\",\n  \"title\": \"7777\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        getPerform(
                "월별 거래 조회 - 202303 합산 확인",
                "/transaction/monthly"
        )
                .andExpect(MockMvcResultMatchers.jsonPath("transList[0].yyyymmdd").value("202211"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[0].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[1].yyyymmdd").value("202212"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[1].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[2].yyyymmdd").value("202301"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[2].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[3].yyyymmdd").value("202302"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[3].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[4].yyyymmdd").value("202303"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[4].mineAmount").value("11110"))

        postPerform(
                "거래 등록 - 202303 FREE Type grouping 확인",
                "/transaction",
                "{\n  \"amount\": 9999,\n  \"userId\": \"testIDIDID\",\n  \"registeredAtYyyymmdd\": \"20230301\",\n  \"title\": \"9999\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"FREE\"\n}"
        )

        getPerform(
                "월별 거래 조회 - 202303 FREE 타입 금액 확인",
                "/transaction/monthly"
        )
                .andExpect(MockMvcResultMatchers.jsonPath("transList[0].yyyymmdd").value("202211"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[0].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[1].yyyymmdd").value("202212"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[1].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[2].yyyymmdd").value("202301"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[2].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[3].yyyymmdd").value("202302"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[3].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[4].yyyymmdd").value("202303"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[4].mineAmount").value("11110"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[4].freeAmount").value("9999"))

        postPerform(
                "거래 등록 - 202303 FREE Type 합 확인",
                "/transaction",
                "{\n  \"amount\": 1111,\n  \"userId\": \"testIDIDID\",\n  \"registeredAtYyyymmdd\": \"20230301\",\n  \"title\": \"9999\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"FREE\"\n}"
        )

        getPerform(
                "월별 거래 조회 - 202303 FREE 타입 합계 확인",
                "/transaction/monthly"
        )
                .andExpect(MockMvcResultMatchers.jsonPath("transList[0].yyyymmdd").value("202211"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[0].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[1].yyyymmdd").value("202212"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[1].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[2].yyyymmdd").value("202301"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[2].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[3].yyyymmdd").value("202302"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[3].mineAmount").value("3333"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[4].yyyymmdd").value("202303"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[4].mineAmount").value("11110"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[4].freeAmount").value("11110"))
    }

    @Test
    @DisplayName("오늘기준으로 변수로 받는 지정 날짜까지의 거래 조회 api 테스트")
    fun getTransactionListByDate() {
        postPerform(
                "거래 등록 - 20230520",
                "/transaction",
                "{\n  \"amount\": 1111,\n  \"userId\": \"20s_1\",\n  \"registeredAtYyyymmdd\": \"20230520\",\n  \"title\": \"1111\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
                "거래 등록 - 20230520",
                "/transaction",
                "{\n  \"amount\": 1111,\n  \"userId\": \"20s_2\",\n  \"registeredAtYyyymmdd\": \"20230520\",\n  \"title\": \"2222\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
                "거래 등록 - 20230519",
                "/transaction",
                "{\n  \"amount\": 11111,\n  \"userId\": \"19s_1\",\n  \"registeredAtYyyymmdd\": \"20230519\",\n  \"title\": \"3333\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
                "거래 등록 - 20230519",
                "/transaction",
                "{\n  \"amount\": 11111,\n  \"userId\": \"19s_2\",\n  \"registeredAtYyyymmdd\": \"20230519\",\n  \"title\": \"4444\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
                "거래 등록 - 20230518",
                "/transaction",
                "{\n  \"amount\": 111,\n  \"userId\": \"18s_1\",\n  \"registeredAtYyyymmdd\": \"20230518\",\n  \"title\": \"3333\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
                "거래 등록 - 20230518",
                "/transaction",
                "{\n  \"amount\": 111,\n  \"userId\": \"18s_2\",\n  \"registeredAtYyyymmdd\": \"20230518\",\n  \"title\": \"4444\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
                "거래 등록 - 20230517",
                "/transaction",
                "{\n  \"amount\": 111,\n  \"userId\": \"17s_1\",\n  \"registeredAtYyyymmdd\": \"20230517\",\n  \"title\": \"4444\",\n  \"content\": \"테스트 전용 내요ㅇ\",\n  \"tagId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        //todo 이거 조회 기준을 왜 userId로 했을까... 그냥 눈에 띄여서..? 나중에 유저아이디 검증들어가면 테스트 코드 수정이 필요할지도..

        getPerform(
                "거래 조회 - 3일치(20230518-20230520)",
                "/transaction/recent/days?days=3"
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("transList[*].userId", Matchers.not(Matchers.hasItem("17s_1"))))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[0].yyyymmdd").value("20230518"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[1].userId").value("18s_2"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[1].yyyymmdd").value("20230518"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[2].userId").value("19s_1"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[2].yyyymmdd").value("20230519"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[3].userId").value("19s_2"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[3].yyyymmdd").value("20230519"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[4].userId").value("20s_1"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[4].yyyymmdd").value("20230520"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[5].userId").value("20s_2"))
                .andExpect(MockMvcResultMatchers.jsonPath("transList[5].yyyymmdd").value("20230520"))

    }
}