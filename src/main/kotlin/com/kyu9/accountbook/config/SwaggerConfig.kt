package com.kyu9.accountbook.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.PropertySource


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

}
