package com.kyu9.accountbook

import com.kyu9.accountbook.elastic.TransactionRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.FilterType
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan(basePackages = ["com.kyu9.accountbook", "com.kyu9.accountbook.swagger.api", "com.kyu9.accountbook.swagger.model"])
//@EnableJpaRepositories(
//        excludeFilters = [ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [TransactionRepository::class])]
//)
@EnableCaching
@EnableAspectJAutoProxy   //springboot 2.3.4버전 이후에는 따로 필요없음
class AccountBookApplication

fun main(args: Array<String>) {
    runApplication<AccountBookApplication>(*args)
}
