package com.kyu9.accountbook.domain.dto

import com.kyu9.accountbook.domain.properties.MoneyType
import com.querydsl.core.annotations.QueryProjection

data class MonthlyTran(
        val registeredYYYYMM: String?,
        val moneyType: MoneyType?,
        var sumAmt: Long?
){

}


