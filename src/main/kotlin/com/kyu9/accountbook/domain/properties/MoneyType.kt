package com.kyu9.accountbook.domain.properties

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue
import java.lang.Exception

enum class MoneyType {
    //Todo 이름 어떻게든 생각은 해보자.. 아무튼 내 진짜 돈이 아닌 금액에 대해서 확인 필요
    FREE,
    MINE,

    @JsonEnumDefaultValue
    DEFAULT
    ;

    companion object {
        @JsonCreator
        fun getMoneyType(value: String): MoneyType? {
            return try {
                valueOf(value)
            } catch (e: Exception) {
                null
            }
        }

    }


}
