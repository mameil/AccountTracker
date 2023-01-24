package com.kyu9.accountbook

import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
@ComponentScan(basePackages = ["com.kyu9.accountbook", "com.kyu9.accountbook.swagger.api", "com.kyu9.accountbook.swagger.model"])
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
