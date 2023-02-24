package com.kyu9.accountbook.transaction

import com.kyu9.accountbook.common.MyTime
import com.kyu9.accountbook.common.TestFrame
import org.junit.jupiter.api.Test

class TransactionMockTest: TestFrame() {

    @Test
    fun tranSaveTest(){

        postPerform(
            "거래 생성 되는거 확인",
            "/transaction",
            "{\n  \"amount\": 1234,\n  \"registeredAt\": \"${MyTime.toYyyymmddhhmmss(MyTime.now())}\",\n  \"title\": \"편의점 - 병 커피\",\n  \"content\": \"병으로된 커피가 먹고 싶으니까..?\",\n  \"categoryId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        postPerform(
            "거래 생성 되는거 확인",
            "/transaction",
            "{\n  \"amount\": 1234,\n  \"registeredAt\": \"${MyTime.toYyyymmddhhmmss(MyTime.now())}\",\n  \"title\": \"편의점 - 병 커피2\",\n  \"content\": \"병으로된 커피가 먹고 싶으니까..?\",\n  \"categoryId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )
    }

    @Test
    fun tranGetTest(){
        postPerform(
            "거래 생성 되는거 확인",
            "/transaction",
            "{\n  \"amount\": 1234,\n  \"registeredAt\": \"${MyTime.toYyyymmddhhmmss(MyTime.now())}\",\n  \"title\": \"편의점 - 병 커피\",\n  \"content\": \"병으로된 커피가 먹고 싶으니까..?\",\n  \"categoryId\": 1234,\n  \"moneyType\": \"MINE\"\n}"
        )

        //아이디 생성 규칙에 맞게 조회하도록 변경해야함
//        getPerform(
//            "거래 조회 되는거 확인",
//            "/transaction/1"
//        )
    }
}