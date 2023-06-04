package com.kyu9.accountbook.utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service


@Service
class JsonGenerator {

    fun parseJson(toStringValue: String): String {
        var propertyValues = toStringValue.substringAfter("(").substringBeforeLast(")").split(", ")

        val jsonObject = mutableMapOf<String, Any>()

        for (propertyValue in propertyValues) {
            var filteredValue = propertyValue
            if(propertyValue.contains("(")) {
                filteredValue = propertyValue.substring(propertyValue.lastIndexOf("(")+1)
            }else if(propertyValue.contains(")")) {
                filteredValue = propertyValue.substringBeforeLast(")")
            }

            var (propertyName, propertyStringValue) = filteredValue.split("=")

            if(propertyStringValue.contains("null")) continue


            val pattern = Regex("-?\\d+")
            val propertyValueObject = if (propertyStringValue.matches(pattern)) {
                try {
                    propertyStringValue.toInt()
                } catch (e: NumberFormatException) {
                    propertyStringValue
                }
            } else if(propertyStringValue.contains("true") || propertyStringValue.contains("false")) {
                propertyStringValue.toBoolean()
            }
            else {
                if(propertyStringValue.contains("(")) {
                    while(propertyStringValue.contains("(")) {
                        propertyStringValue = propertyStringValue.substringAfter("(")
                    }
                }
                else {
                    propertyStringValue
                }
            }

            jsonObject[propertyName] = propertyValueObject
        }

        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(jsonObject)
    }
}