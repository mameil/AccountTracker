package com.kyu9.accountbook.elastic

import com.kyu9.accountbook.common.CustomError
import com.kyu9.accountbook.common.DataBridgeAble
import com.kyu9.accountbook.common.DataBridgeRepo
import org.springframework.stereotype.Service

@Service
class TestInfoDataRepository(
        override val esRepo: TestInfoRepository,
        override val jpaRepo: TestInfoEntityJpaRepository
): DataBridgeRepo<
        String,
        TestInfo, TestInfoEntity,
        TestInfoRepository, TestInfoEntityJpaRepository> {

            fun getDataFromEs(id: String): TestInfo{
                return esRepo.findById(id).orElseThrow(CustomError.DATA_NOT_FOUND::doThrow)
            }
}