package com.kyu9.accountbook.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@EnableSwagger2
@Configuration
class SwaggerConfig {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
            .groupName("KD 1.0.0")
            .pathMapping("/")
            .apiInfo(apiInfo())
            .useDefaultResponseMessages(false)
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("KDD's Swagger")
            .description("KDD의 스웨거")
            .version("1.0.0")
            .termsOfServiceUrl("")
            .license("")
            .licenseUrl("")
            .build()
    }
}

