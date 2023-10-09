package com.kyu9.accountbook.config

import com.kyu9.accountbook.common.MyTime
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Component
class CustomActuatorConfig : HealthIndicator {
    @Value("\${git.commit.user.name}")
    lateinit var userName: String

    @Value("\${git.commit.id.abbrev}")
    lateinit var gitHashShort: String

    @Value("\${git.commit.message.short}")
    lateinit var gitMsg: String

    @Value("\${git.commit.time}")
    lateinit var gitCommitTime: String

    override fun health(): Health {
        val map: MutableMap<String, String> = HashMap()

        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
        val dateTime: LocalDateTime = LocalDateTime.parse(gitCommitTime, inputFormatter)

        map["git_user"] = userName
        map["git_hash_short"] = gitHashShort
        map["git_msg"] = gitMsg
        map["git_commit_time"] = MyTime.of(dateTime).toYyyymmddhhmmss()
        return Health.Builder().up().withDetail("git", map).build()
    }
}