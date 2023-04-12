package com.kyu9.accountbook.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.springframework.boot.env.YamlPropertySourceLoader
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import io.swagger.v3.oas.models.Paths
import org.springdoc.core.*
import org.springframework.context.annotation.Bean
import org.springframework.util.ResourceUtils
import org.springframework.web.context.WebApplicationContext
import org.springdoc.core.SpringDocConfiguration


@Configuration
@PropertySource("classpath:spec/AccountBook.yaml", factory = YamlPropertySourceFactory::class)
class SwaggerConfig(private val applicationContext: WebApplicationContext) : SpringDocConfiguration() {

    override fun openAPI(): OpenAPI {
        val openAPI = OpenAPI()
        openAPI.components(Components())
        openAPI.info(Info().title("Swagger API Documentation").version("1.0.0"))
        openAPI.addServersItem(Server().url("http://localhost:8080"))

        // Load YAML file and map it to OpenAPI model
        val environment = applicationContext.environment
        val yamlMapper = ObjectMapper(YAMLFactory())
        val yamlPropertySource = environment.getPropertySources().get("swagger")?.let {
            YamlPropertySourceLoader().load("swagger", it).firstOrNull()
        }
        yamlPropertySource?.source?.let { source ->
            val yamlNode = yamlMapper.readTree(source as String)
            val pathsNode = yamlNode.at("/paths")
            val componentsNode = yamlNode.at("/components")
            if (!pathsNode.isMissingNode) {
                val paths = yamlMapper.convertValue(pathsNode, Paths::class.java)
                openAPI.paths(paths)
            }
            if (!componentsNode.isMissingNode) {
                val components = yamlMapper.convertValue(componentsNode, Components::class.java)
                openAPI.components(components)
            }
        }

        return openAPI
    }


    @Bean
    fun api(): GroupedOpenApi = GroupedOpenApi.builder()
            .group("Accountbook")
            .packagesToScan("com.kyu9.accountbook")
            .build()
}
