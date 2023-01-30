package com.kyu9.accountbook.utils

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class HtmlGenerator {
    lateinit var mapper: ObjectMapper

    fun generateHtmlFromJson(keys: List<String>, values: List<String>): String {
        if(keys.size != values.size) {
            throw IllegalArgumentException("keys and values size must be same")
        }

        mapper = ObjectMapper()

        val html = StringBuilder()


        html.append("<html><body>")
        html.append("<table>")
        html.append("<tr>")
        keys.forEach {
            html.append("<th>$it</th>")
        }
        html.append("</tr>")
        html.append("<tr>")
        values.forEach {
            html.append("<td>$it</td>")
        }
        html.append("</tr>")
        html.append("</table>")
        html.append("</body></html>")

        return html.toString()
    }
}