package com.kyu9.accountbook.schedule

import com.kyu9.accountbook.application.TransactionService
import com.kyu9.accountbook.common.MyTime
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.lang.Exception

@Service
@RequiredArgsConstructor
class EsMigrationService(
        private val transactionService: TransactionService
) {
    private val log: Logger = LoggerFactory.getLogger(EsMigrationService::class.java)


//    @Scheduled(cron = "0 0 0 * * *")
    fun dailyMigration(){
        log.info("=== Daily Migration - transactions ===")
        log.info("started at ${MyTime.now()}")
        try{
            transactionService.migrateAllToElasticSearch()
        }catch (e: Exception){
            log.error("error occurred while migrating transactions to elastic search")
            log.error(e.message)
        }

        log.info("migration succeed")
        log.info("ended at ${MyTime.now()}")
    }
}