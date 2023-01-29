package com.kyu9.accountbook.common

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class LogService {
    private val log: Logger = LoggerFactory.getLogger(LogService::class.java) as Logger

    fun info(message: String) {
        log.info(message)
    }
}