package com.kyu9.accountbook.swagger.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import javax.validation.Valid

/**
 * 
 * @param id user login id(String)
 * @param name user name
 * @param password user password
 * @param createdAt user create date(yyyymmddHHmmss)
 * @param updatedAt user update date(yyyymmddHHmmss)
 */
data class CreateUserResponseDto(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:JsonProperty("name") val name: kotlin.String? = null,

    @field:JsonProperty("password") val password: kotlin.String? = null,

    @field:JsonProperty("createdAt") val createdAt: kotlin.String? = null,

    @field:JsonProperty("updatedAt") val updatedAt: kotlin.String? = null
) {

}

