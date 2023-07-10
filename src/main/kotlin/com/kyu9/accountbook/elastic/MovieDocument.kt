package com.kyu9.accountbook.elastic

import org.springframework.data.elasticsearch.annotations.Document
import java.time.LocalDateTime
import javax.persistence.Id

@Document(indexName = "movie")
data class MovieDocument(
        @Id
        val id: Long, //elasticsearch 에서의 index id 값
        var title: String, //영화 제목
        var genre: List<String>, //영화 장르
        var country: String, //영화 나라
        var runningTime: Int, //영화 런타임 시간 minute
        var releaseYear: Int, //영화 개봉일 yyyymmdd
        var reviewRate: Double, //네이버 평점
)
