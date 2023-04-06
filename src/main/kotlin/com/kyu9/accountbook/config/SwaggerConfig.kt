package com.kyu9.accountbook.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.DocExpansion
import springfox.documentation.swagger.web.UiConfiguration
import springfox.documentation.swagger.web.UiConfigurationBuilder
import springfox.documentation.swagger2.annotations.EnableSwagger2
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc


@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.OAS_30) // select() : ApiSelectorBuilder 를 활성화 하여, Builder 형태로 합니다.
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kyu9.accountbook.swagger")) // paths()
                .paths(PathSelectors.any())
                .build();
    }

    private fun uiConfiguration(): UiConfiguration {
        return UiConfigurationBuilder.builder()
                .deepLinking(true)
                .displayRequestDuration(true)
                .defaultModelsExpandDepth(1)
                .defaultModelExpandDepth(1)
                .docExpansion(DocExpansion.LIST)
                .filter(true)
                .maxDisplayedTags(null)
                .showExtensions(false)
                .build()
    }
}
