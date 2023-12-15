package com.kyu9.accountbook

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK
//        , classes = [AccountBookApplication::class]
)
class AccountBookApplicationTests {

    @Test
    fun contextLoads() {
    }

}
