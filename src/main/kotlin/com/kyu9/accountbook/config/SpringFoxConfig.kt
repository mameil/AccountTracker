package com.kyu9.accountbook.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.VendorExtension
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.TagsSorter
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SpringFoxConfig {
    @Bean
    fun api(): Docket? {
        return Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kyu9.accountbook.swagger"))
                .paths(PathSelectors.any())
                .build()
    }

    private fun apiInfo(): ApiInfo? {
        return ApiInfoBuilder()
                .title("AccountTracker API")
                .description("Kyudo's AccountBook by kotlin springboot <br>main views<br> localhost:10001/view/login")
                .version("1.0")
                .build()
    }
}