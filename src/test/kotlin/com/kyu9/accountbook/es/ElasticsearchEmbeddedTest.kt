package com.kyu9.accountbook.es

import com.kyu9.accountbook.AccountBookApplication
import com.kyu9.accountbook.common.MyTime
import com.kyu9.accountbook.elastic.Transaction
import com.kyu9.accountbook.elastic.TransactionRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.allegro.tech.embeddedelasticsearch.EmbeddedElastic
import pl.allegro.tech.embeddedelasticsearch.PopularProperties
import java.io.IOException
import java.net.ServerSocket
import java.net.URL
import java.util.*

@SpringBootTest(classes = [AccountBookApplication::class], properties = ["elasticsearch.url=localhost", "elasticsearch.port=9200"])
class ElasticsearchEmbeddedTest {
    companion object{
        private val ELASTIC_VERSION = "6.5.4"
        private var embeddedElastic: EmbeddedElastic = EmbeddedElastic.builder()
                .withElasticVersion(ELASTIC_VERSION)
                .withSetting(PopularProperties.HTTP_PORT, "9300")
                .withSetting(PopularProperties.CLUSTER_NAME, UUID.randomUUID())
                .withPlugin("analysis-nori")
                .withDownloadUrl(URL("http://xbib.org/repository/org/xbib/elasticsearch/plugin/elasticsearch-analysis-decompound/${ELASTIC_VERSION}.0/elasticsearch-analysis-decompound-${ELASTIC_VERSION}.0-plugin.zip"))
                .build()
        private var port: Int? = null

        @JvmStatic
        @Throws(IOException::class)
        private fun findRandomPort(): Int? {
            ServerSocket(0).use { socket -> return socket.localPort }
        }
    }

    @Autowired
    lateinit var transactionRepository: TransactionRepository

    @Test
    fun test(){
        embeddedElastic.start()

        val tr = Transaction(
                id = "1",
                userId = "qwer",
                amt = "1000",
                registered = MyTime.now()
        )

        transactionRepository.save(tr)

        val found = transactionRepository.findById("1").get()
        Assertions.assertEquals(tr, found)

        embeddedElastic.stop()

    }
}