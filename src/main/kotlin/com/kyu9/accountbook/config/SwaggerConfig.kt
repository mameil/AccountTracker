package com.kyu9.accountbook.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory


@Configuration
@PropertySource("classpath:spec/AccountBook.yaml", factory = YamlPropertySourceFactory::class)
class SwaggerConfig {
    @Bean
    @Primary
    fun openApi(
    ): OpenAPI {
        return OpenAPI()
            .components(Components())
            .info(Info().version("0.0.0").title("AccountBook").description("내가 사용하기 위해 만든 가계부 project"))
    }

    @Bean
    fun mainGroupedApi(
    ) : GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("AccountBook")
            .packagesToScan("com.kyu9.accountbook.swagger")
            .build()
    }

//    @Bean
//    fun actuatorGroupedApi() : GroupedOpenApi {
//        return GroupedOpenApi.builder()
//            .group("Actuator API Controller")
//            .build()
//    }

}
