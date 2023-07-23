package com.kyu9.accountbook.elastic

import com.kyu9.accountbook.common.CustomError
import lombok.RequiredArgsConstructor
import org.elasticsearch.action.search.SearchRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentBuilder
import org.elasticsearch.common.xcontent.XContentFactory
import org.elasticsearch.search.aggregations.Aggregation
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms
import org.elasticsearch.search.aggregations.bucket.terms.Terms
import org.elasticsearch.search.aggregations.metrics.Avg
import org.elasticsearch.search.builder.SearchSourceBuilder
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.stereotype.Service


@Service
@RequiredArgsConstructor
class ETService(
        private val transactionRepository: TransactionRepository,
        private val elasticsearchOperations: ElasticsearchOperations,
        private val elasticsearchClient: RestHighLevelClient
) {


    fun postTransaction(userId: String?, userName: String?, amt: Int?): String? {
        return transactionRepository
                .save(Transaction(userId!!, userName!!, amt!!.toString()))
                .id
    }

    fun getTransactionById(id: String): Transaction? {
        return transactionRepository.findById(id).orElseThrow(CustomError.DATA_NOT_FOUND::doThrow)
    }

    fun getTransactionByName(name: String): Transaction? {
        return transactionRepository.getSimilarWithName(name)
    }

    fun getAllAmtAvg(): Double {
        val builder = SearchSourceBuilder()
                .aggregation(AggregationBuilders
                        .avg("avg_amt")
                        .field("amt"))

        val searchReq = SearchRequest()
                .indices("transaction")
                .source(builder)

        val searchRes = elasticsearchClient.search(searchReq, RequestOptions.DEFAULT)
        println("==============================================")
        println(searchRes.toString())
        println("==============================================")

//        https://stackoverflow.com/questions/21018493/how-to-access-aggregations-result-with-elasticsearch-java-api-in-searchresponse
        val avg: Avg = searchRes.aggregations.get("avg_amt")
        println(avg)
        return avg.value


        return 0.0
    }


}