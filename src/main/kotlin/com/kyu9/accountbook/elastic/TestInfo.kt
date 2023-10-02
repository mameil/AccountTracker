package com.kyu9.accountbook.elastic

import com.kyu9.accountbook.common.DataBridgeAble
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import java.time.LocalDateTime

@Document(indexName = "test_info")
data class TestInfo(
        @Id
        val name: String,
        var desc: String,
        val creator: String,
        val created: LocalDateTime
): DataBridgeAble<TestInfoEntity, TestInfo> {
        override fun toES(): TestInfo {
                return this
        }

        override fun toRDB(): TestInfoEntity {
                return TestInfoEntity(name, desc, created, creator)
        }

        constructor(name: String, desc: String): this(name, desc, "CLIENT", LocalDateTime.now())
}
