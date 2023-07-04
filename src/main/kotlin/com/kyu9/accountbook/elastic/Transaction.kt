package com.kyu9.accountbook.elastic

import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.elasticsearch.annotations.Document
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@Document(indexName = "transaction")
data class Transaction(
        @Id
        val id: String,
        val name: String,
        val amt: Int
) {
    //해당 에노테이션을 생성자에 붙혀서 >> 저장된 document가 aggregate 으로 재구성됨
    @PersistenceConstructor
    constructor(name: String, amt: Int): this("", name, amt)

}
