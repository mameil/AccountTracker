package com.kyu9.accountbook.config

import com.kyu9.accountbook.elastic.TransactionRepository
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories


@Configuration
@EnableElasticsearchRepositories(
        basePackageClasses = [TransactionRepository::class]
)
class ElasticsearchConfig{
//    @Value("\${elasticsearch.url}")
    private lateinit var elasticsearchUrl: String

//    @Value("\${elasticsearch.port}")
    private lateinit var elasticsearchPort: String


    @Bean
    fun elasticsearchClient(): RestHighLevelClient {
        return RestClients
//                .create(ClientConfiguration.builder().connectedTo("$elasticsearchUrl:$elasticsearchPort").build())
                .create(ClientConfiguration.builder().connectedTo("localhost:9200").build())
                .rest()
    }
}