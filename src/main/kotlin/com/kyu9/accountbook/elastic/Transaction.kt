package com.kyu9.accountbook.elastic

import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.elasticsearch.annotations.Document
import javax.persistence.Entity
import javax.persistence.Id

@Document(indexName = "transaction")
data class Transaction @PersistenceConstructor constructor(
        @Id
        val id: String,
        val name: String,
        val amt: Int
)
