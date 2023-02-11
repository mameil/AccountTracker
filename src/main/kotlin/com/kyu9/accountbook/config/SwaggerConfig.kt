//package com.kyu9.accountbook.config
//
//import io.swagger.v3.oas.models.info.Info
//import io.swagger.v3.oas.models.Components
//import io.swagger.v3.oas.models.OpenAPI
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
////3.0 springboot 에서 안먹힌다..!
//
////요걸 넣어줘야 한다
////@EnableWebMvc
//class SwaggerConfig {
//    @Bean
//    fun openAPI(): OpenAPI? {
//        val info: Info = Info().title("타이틀 입력")
//            .version("v1")
//            .description("API에 대한 설명 부분")
//        return OpenAPI()
//            .components(Components())
//            .info(info)
//    }
//}