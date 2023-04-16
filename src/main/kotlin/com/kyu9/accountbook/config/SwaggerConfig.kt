package com.kyu9.accountbook.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import io.swagger.v3.oas.models.tags.Tag
import org.springdoc.core.GroupedOpenApi
import org.springdoc.core.SpringDocConfigProperties
import org.springdoc.core.SpringDocConfiguration
import org.springdoc.core.customizers.OpenApiCustomiser
import org.springdoc.core.providers.ObjectMapperProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.PropertySource


@Configuration
@PropertySource("classpath:specs/AccountBook.yaml", factory = YamlPropertySourceFactory::class)
class SwaggerConfig(){

    @Bean
    @Primary
    fun openApi(
            openApiCustomisers: List<OpenApiCustomiser>
    ): OpenAPI {
        val openApi = OpenAPI()
                .components(Components())
                .info(Info().version("0.0.0").title("AccountBook").description("내가 사용하기 위해 만든 가계부 project"))
                .addTagsItem(Tag().name("com.kyu9.accountbook"))
                .addServersItem(Server().url("http://localhost:10001"))

        openApiCustomisers.forEach { customiser ->
            customiser.customise(openApi)
        }

        return openApi
    }


    @Bean
    fun mainApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
                .group("accountbook")
                .packagesToScan("com.kyu9.accountbook")
                .build()
    }


    @Bean
    fun springDocConfigProperties(): SpringDocConfigProperties? {
        return SpringDocConfigProperties()
    }

    @Bean
    fun objectMapperProvider(springDocConfigProperties: SpringDocConfigProperties?): ObjectMapperProvider? {
        return ObjectMapperProvider(springDocConfigProperties)
    }




}
