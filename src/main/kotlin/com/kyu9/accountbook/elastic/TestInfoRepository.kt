package com.kyu9.accountbook.elastic

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository


interface TestInfoRepository: ElasticsearchRepository<TestInfo, String> {
}