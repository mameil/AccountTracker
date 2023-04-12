package com.kyu9.accountbook.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.SpringDocConfigProperties
import org.springdoc.core.SpringDocConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


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

    /*
    swagger property
springdoc.swagger-ui.path=swagger-ui.html
##
springdoc.swagger-ui.display-request-duration=false
springdoc.swagger-ui.groups-order=DESC
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.disable-swagger-default-url=true
springdoc.show-actuator=true

springdoc.default-consumes-media-type=application/json
springdoc.default-produces-media-type=application/json

logging property
logging.level.com.kona.rfs=debug
#logging.level.org.hibernate.SQL=debug
asdf

     */

}
