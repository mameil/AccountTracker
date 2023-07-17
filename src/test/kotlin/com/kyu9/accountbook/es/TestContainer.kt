package com.kyu9.accountbook.es

import org.testcontainers.elasticsearch.ElasticsearchContainer
import org.testcontainers.junit.jupiter.Container

class TestContainer {
    companion object{
        //todo 1 >> 우선 embedded 테스트 코드에 있는 companion object를 이렇게 외부로 선언해두고 > testConfiguration에서 여기 포트를 사용해서 가져갈 수 있도록 해보자
        //todo 2 >> 다음으로는 아래의 코드를 그대로 사용해보자 > 플러그인은 일단 둘째로 두고 > 돌려보자.. genericContainer 같은 경우에는 인터넷을 통해서 가져오니 ElasticContainer 을 사용해서 가능한지보고
        //todo 3 >> 가능하면 ElasticContainer 을 실행할 때 플러그인을 같이 실행할 수 있도록 dev3에 컨트롤러를 만들어서 플러그인을 가져가서 실행할 수 있는지 보자
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