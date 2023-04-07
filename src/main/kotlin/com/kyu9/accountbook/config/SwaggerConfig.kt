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
            .
            .info(Info().version("0.0.0").title("title").description("description"))
    }

    @Bean
    fun mainGroupedApi(
    ) : GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("My API Controller")
            .pathsToExclude("/monitoring/**")
            .build()
    }

    @Bean
    fun actuatorGroupedApi() : GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("Actuator API Controller")
            .pathsToMatch("/monitoring/**")
            .build()
    }

}
