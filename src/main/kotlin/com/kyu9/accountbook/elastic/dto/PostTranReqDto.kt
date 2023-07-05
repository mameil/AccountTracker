package com.kyu9.accountbook.elastic.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.io.Serializable

data class PostTranReqDto(
        @JsonProperty("userId")
        val userId: String?,
        @JsonProperty("userName")
        val userName: String?,
        @JsonProperty("amt")
        val amt: Int?
)
