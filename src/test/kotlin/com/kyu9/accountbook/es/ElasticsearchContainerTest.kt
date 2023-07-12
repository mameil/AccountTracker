package com.kyu9.accountbook.es

import com.kyu9.accountbook.AccountBookApplication
import com.kyu9.accountbook.elastic.Transaction
import com.kyu9.accountbook.elastic.TransactionRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.testcontainers.containers.GenericContainer
import org.testcontainers.elasticsearch.ElasticsearchContainer
import org.testcontainers.images.builder.ImageFromDockerfile
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

//이게 테스트가 돌면서 properties 들을 읽지 못해서 이렇게 properties 를 명시해줘야 잘 돔
@SpringBootTest(classes = [AccountBookApplication::class], properties = ["elasticsearch.url=localhost", "elasticsearch.port=9200"])
@Testcontainers
class ElasticsearchContainerTest {
    companion object{
        @JvmStatic
        @Container
        private val elasticsearchContainer = GenericContainer(
                ImageFromDockerfile()
                        .withDockerfileFromBuilder { builder ->
                            builder
                                    //일단 인터넷망에서는 이렇게까지만 하면 정상적으로 테스트 돌아감
                                    .from("docker.elastic.co/elasticsearch/elasticsearch:7.15.2")
                                    .run("bin/elasticsearch-plugin install analysis-nori")
                                    .build()
                        }
        )
                .withExposedPorts(9200, 9300)
                .withEnv("discovery.type", "single-node")


        @BeforeAll
        @JvmStatic
        fun beforeAll(){
            elasticsearchContainer.start()
        }

        @AfterAll
        @JvmStatic
        fun afterAll(){
            elasticsearchContainer.stop()
        }
    }

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @Test
    fun test(){
        val tr = Transaction(
                id = "1",
                name = "qwer",
                amt = 1000
        )

        transactionRepository.save(tr)

        val found = transactionRepository.findById("1").get()
        Assertions.assertEquals(tr, found)
    }
}