package com.kyu9.accountbook.domain.properties

import com.fasterxml.jackson.annotation.JsonCreator
import java.lang.Exception

enum class MoneyType {
    //Todo 이름 어떻게든 생각은 해보자.. 아무튼 내 진짜 돈이 아닌 금액에 대해서 확인 필요
    FREE,
    MINE,

    DEFAULT
    ;

    companion object {
        @JsonCreator
        fun getMoneyType(moneyType: String): MoneyType? {
            try{
                return MoneyType.valueOf(moneyType)
            }catch (e: Exception){
                return null;
            }
        }


    }
}
