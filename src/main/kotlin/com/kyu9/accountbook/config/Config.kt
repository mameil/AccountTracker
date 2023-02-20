package com.kyu9.accountbook.config

import com.kyu9.accountbook.common.CustomSequenceGenerator
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class Config(
    private val customSequenceGenerator: CustomSequenceGenerator
) {

}