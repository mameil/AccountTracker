package com.kyu9.accountbook.config

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component


@Component
class CustomActuatorConfig : HealthIndicator {
    override fun health(): Health {
        val map: MutableMap<String, String> = HashMap()
        map["foo"] = "bar"
        map["my"] = "value"
        return Health.Builder().up().withDetail("simple", map).build()
    }
}