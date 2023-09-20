package com.kyu9.accountbook.elastic

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "test_info")
data class TestInfo(
        @Id
        val name: String,
        val desc: String
): BaseEsEntity() {
}
