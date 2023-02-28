package com.kyu9.accountbook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.kyu9.accountbook", "com.kyu9.accountbook.swagger.api", "com.kyu9.accountbook.swagger.model"])
@EnableCaching
class AccountBookApplication

fun main(args: Array<String>) {
    runApplication<AccountBookApplication>(*args)
}
