package com.kyu9.accountbook.config

import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader


@Component
@Endpoint(id = "git")
class ActuatorGitConfig {

    @ReadOperation
    fun displayGitMessage(): String {
        var process: Process = Runtime.getRuntime().exec("git log --format=%B -n 1")
        var message: String = ""
        BufferedReader(InputStreamReader(process.inputStream)).use {
            reader -> message = reader.readLine()
        }
        message = "{\"git_message\" : \"$message\"}"

        return message
    }
}