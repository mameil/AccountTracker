package com.kyu9.accountbook.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.kyu9.accountbook.aop.ApiLoggingAop
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate


@Configuration
class Config(
) {
    @Bean
    fun objectMapper(): ObjectMapper {
        //redis configuration 작성하면서 redisTemplate 에 사용될 objectmapper을 구현하면서 이름을 'objectMapper'로 해둬서 모든 api에서 적용되고있었음... ㅠㅠ
        //redis 쪽에서 확실하게 redisObjectMapper로 이름을 바꿔주고, 이쪽에서는 다시 재명시
        val objectMapper = ObjectMapper()
        objectMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true)
        return objectMapper
    }

    @Bean
    fun apiLoggingAop(): ApiLoggingAop {
        return ApiLoggingAop()
    }

    @Bean
    fun acbRestTemplate(): RestTemplate {
        val element = MappingJackson2HttpMessageConverter()
        element.objectMapper = objectMapper()
        val restTemplate = RestTemplate()
        restTemplate.messageConverters = listOf(element)

        return restTemplate
    }
}