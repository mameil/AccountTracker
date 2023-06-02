package com.kyu9.accountbook.util

import com.kyu9.accountbook.common.TestFrame
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class GenerateJsonTest: TestFrame() {

    @Test
    @DisplayName("json 생성")
    fun generateHtml(){
        val lomboks2 = "CrsTransaction(super=CrsReward(aspId=000137000000000, userId=50000543364, serviceId=000137000257000, rewardDetailTargetId=null, tid=null, hostName=null, failReason=null, status=SUCCESS, rewardCount=1, rewardType=null, p1=null, ms=null, realRewardId=null, yyyymmdd=null), par=Q121B5D2FBFFBD139A5768C573E, acquireType=null, processingCode=211900, mti=0100, merchantId=410226436448501, merchantType=MC, merchantBizNo=null, amount=50000, orgAmount=50000, originOrgAmount=null, orgAmountCancelYn=null, nrNumber=KMN221223031792766, orgNrNumber=null, trIncenAmount=null, acquirerMerchantType=null, trPoint=0, acquirerId=null, adminSend=null, approvalDatetime=null, reversalStatus=null, trPointExp=0, sendPush=Y, merchantName=null, acquirerMerchantName=null, pointList=null, expAmount=null, expTrAmount=null, expPointAmount=0, expTrIncenAmount=null, posEntryMode=null)"
        val lomboks3 = "Link(url=localhost, category=JAVA, name=test link, memo=this is test only link)"

        postPerform(
            "test json1",
            "/util/json/parse",
            lomboks3
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())

        postPerform(
                "test json2",
                "/util/json/parse",
                lomboks2
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())
    }
}