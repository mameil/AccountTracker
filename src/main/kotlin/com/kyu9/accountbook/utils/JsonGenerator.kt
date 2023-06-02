package com.kyu9.accountbook.utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service


@Service
class JsonGenerator {

    fun parseJson(toStringValue: String): String {
        val propertyValues = toStringValue.substringAfter("(").substringBeforeLast(")").split(", ")

        val jsonObject = mutableMapOf<String, Any>()

        for (propertyValue in propertyValues) {
            val (propertyName, propertyStringValue) = propertyValue.split("=")

            val pattern = Regex("-?\\d+")
            val propertyValueObject = if (propertyStringValue.matches(pattern)) {
                propertyStringValue.toLong()
            } else {
                propertyStringValue
            }

            jsonObject[propertyName] = propertyValueObject
        }

        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(jsonObject)
    }
}