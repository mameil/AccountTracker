package com.kyu9.accountbook

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.EnableAspectJAutoProxy

@SpringBootApplication
@ComponentScan(basePackages = ["com.kyu9.accountbook", "com.kyu9.accountbook.swagger.api", "com.kyu9.accountbook.swagger.model"])
@EnableCaching
@EnableAspectJAutoProxy   //springboot 2.3.4버전 이후에는 따로 필요없음
class AccountBookApplication

fun main(args: Array<String>) {
    runApplication<AccountBookApplication>(*args)
}
