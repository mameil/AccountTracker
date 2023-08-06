package com.kyu9.accountbook.elastic

import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.elasticsearch.annotations.Document
import javax.persistence.Entity
import javax.persistence.Id

@Document(indexName = "transaction")
data class Transaction @PersistenceConstructor constructor(
        @Id
        val id: String,
        val userId: String,
        val amt: String,
        val registered: String,
        val usedAt: String,
        val used: String,
        var category: String
) {
    constructor(
            id: String,
            userId: String,
            amt: String
    ) : this(
            id = id,
            userId = userId,
            amt = amt,
            registered = "",
            usedAt = "",
            used = "",
            category = ""
    )
}
