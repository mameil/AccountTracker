package com.kyu9.accountbook.es

import org.testcontainers.elasticsearch.ElasticsearchContainer
import org.testcontainers.junit.jupiter.Container

class TestContainer {
    companion object{
        @Container
        @JvmStatic
        val CONTAINER = ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:7.15.2")
                .apply {
                    withCreateContainerCmdModifier{
                        it.withCmd(
                                *arrayOf(
                                        "bash",
                                        "-c",
//                                        """/usr/share/elasticsearch/bin/elasticsearch-plugin install URL &&
                                        """/usr/share/elasticsearch/bin/elasticsearch-plugin install analysis-nori &&
                                            su elasticsearch -s /usr/share/elasticsearch/bin/elasticsearch
                                        """.trimIndent()
                                )
                        )
                    }
                }
                .apply { start() }
    }
}