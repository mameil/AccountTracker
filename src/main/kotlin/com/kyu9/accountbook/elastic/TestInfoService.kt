package com.kyu9.accountbook.elastic

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TestInfoService(
        private val testInfoRepository: TestInfoRepository
) {
    val log = LoggerFactory.getLogger(TestInfoService::class.java)

    fun save(testInfo: TestInfo): TestInfo {
        val testInfo = testInfoRepository.save(testInfo)
        log.info("=====================================")
        log.info(testInfo.toString())
        log.info("=====================================")
        return testInfo
    }

    fun getTestInfo(name: String): TestInfo {
        val get = testInfoRepository.findById(name).get()
        log.info("=====================================")
        log.info(get.toString())
        log.info("=====================================")
        return get
    }
}