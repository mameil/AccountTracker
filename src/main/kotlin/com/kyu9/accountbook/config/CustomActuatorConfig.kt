package com.kyu9.accountbook.config

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader


@Component
class CustomActuatorConfig : HealthIndicator {
    override fun health(): Health {
        //todo : 이거 로컬에서는 깃이 연결되어 있어서 로컬에서는 되는데 도커에서는 안됨 >> 빌드할 때 미리 프로젝트 어딘가에 박아두고 그걸 가지고 사용하도록 수정필요
        val map: MutableMap<String, String> = HashMap()

        var commitMsgFromGit: Process = Runtime.getRuntime().exec("git log --format=%B -n 1")
        var gitMsg: String = ""
        BufferedReader(InputStreamReader(commitMsgFromGit.inputStream)).use {
            reader -> gitMsg = reader.readLine()
        }

        var commitHashShortFromGit: Process = Runtime.getRuntime().exec("git log -n 1 --abbrev-commit --format=%h")
        var gitHashShort: String = ""
        BufferedReader(InputStreamReader(commitHashShortFromGit.inputStream)).use {
            reader -> gitHashShort = reader.readLine()
        }

        map["git_message"] = gitMsg
        map["git_hash_short"] = gitHashShort
        return Health.Builder().up().withDetail("git", map).build()
    }
}