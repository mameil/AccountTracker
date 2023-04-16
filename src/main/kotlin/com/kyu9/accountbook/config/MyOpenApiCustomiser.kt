package com.kyu9.accountbook.config

import io.swagger.v3.oas.models.OpenAPI
import org.springdoc.core.customizers.OpenApiCustomiser
import org.springframework.stereotype.Component

@Component
class MyOpenApiCustomiser : OpenApiCustomiser {
    override fun customise(openApi: OpenAPI?) {
        println("==============================================")
        println("OPEN API CUSTOMIZER")
        println("==============================================")
    }
}