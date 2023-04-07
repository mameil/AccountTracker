package com.kyu9.accountbook.config

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory
import java.io.IOException


class YamlPropertySourceFactory : PropertySourceFactory {
    override fun createPropertySource(name: String?, encodedResource: EncodedResource): PropertiesPropertySource {
        val factory = YamlPropertiesFactoryBean()
        factory.setResources(encodedResource.resource)
        val properties = factory.getObject()
        return PropertiesPropertySource(
            encodedResource.resource.filename!!,
            properties!!
        )
    }
}