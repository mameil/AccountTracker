//package com.kyu9.accountbook.es
//
//import org.elasticsearch.client.RestHighLevelClient
//import org.springframework.boot.test.context.TestConfiguration
//import org.springframework.context.annotation.Bean
//import org.springframework.data.elasticsearch.client.ClientConfiguration
//import org.springframework.data.elasticsearch.client.RestClients
//import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories
//import org.testcontainers.containers.GenericContainer
//import org.testcontainers.images.builder.ImageFromDockerfile
//import org.testcontainers.images.builder.dockerfile.DockerfileBuilder
//
//
//@TestConfiguration
//@EnableElasticsearchRepositories
//class ElasticTestContainer{
//
//    @Bean
//    fun testClient(): RestHighLevelClient {
//        val hostAddress = StringBuilder()
//                .append(container!!.host)
//                .append(":")
//                .append(container!!.getMappedPort(9200))
//                .toString()
//        val clientConfiguration = ClientConfiguration.builder()
//                .connectedTo(hostAddress)
//                .build()
//        return RestClients.create(clientConfiguration).rest()
//    }
//
//    companion object {
//        private var container: GenericContainer<*>? = null
//
//        init {
//            container = GenericContainer(
//                    ImageFromDockerfile()
//                            .withDockerfileFromBuilder { builder: DockerfileBuilder ->
//                                builder // ES 이미지 가져오기
//                                        .from("docker.elastic.co/elasticsearch/elasticsearch:7.15.2") // nori 분석기 설치
//                                        .run("bin/elasticsearch-plugin install analysis-nori")
//                                        .build()
//                            }
//            ).withExposedPorts(9200, 9300)
//                    .withEnv("discovery.type", "single-node")
//            container!!.start()
//        }
//    }
//}