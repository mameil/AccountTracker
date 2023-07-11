package com.kyu9.accountbook.elastic

import org.springframework.data.elasticsearch.annotations.Query
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository

interface TransactionRepository: ElasticsearchRepository<Transaction, String>{
    @Query("{\"match\": {\"name\": \"?0\"}}")
    fun getSimilarWithName(name: String): Transaction

}