//package com.kyu9.accountbook.es
//
//import org.elasticsearch.client.RestHighLevelClient
//import org.springframework.boot.test.context.TestConfiguration
//import org.springframework.context.annotation.Bean
//import org.springframework.data.elasticsearch.client.ClientConfiguration
//import org.springframework.data.elasticsearch.client.RestClients
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
//
//@TestConfiguration
//@EnableElasticsearchRepositories
//class TestConfiguration : AbstractElasticsearchConfiguration() {
//    @Bean
//    override fun elasticsearchClient(): RestHighLevelClient {
//        val config = ClientConfiguration.builder()
//                .connectedTo(TestContainer.CONTAINER.httpHostAddress)
//                .build()
//
//        return RestClients.create(config).rest()
//    }
//}