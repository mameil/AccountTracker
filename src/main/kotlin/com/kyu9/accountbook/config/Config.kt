package com.kyu9.accountbook.config

import com.kyu9.accountbook.common.CustomSequenceGenerator
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.yml")
class Config(

) {
    @Value("\${spring.datasource.url}")
    lateinit var dbUrl: String

    @Value("\${spring.datasource.username}")
    lateinit var dbUser: String

    @Value("\${spring.datasource.password}")
    lateinit var dbPwd: String

    @Value("\${spring.datasource.driver-class-name}")
    lateinit var dbDriver: String

    @Bean
    fun entityManagerFactory(): LocalContainerEntityManagerFactoryBean {
        val emf = LocalContainerEntityManagerFactoryBean()
        emf.dataSource = dataSource()
        emf.setPackagesToScan("com.kyu9.accountbook")
        emf.setJpaVendorAdapter(HibernateJpaVendorAdapter())
//        emf.setJpaProperties(additionalProperties())
        return emf
    }

    @Bean
    fun dataSource(): DataSource {
        val dataSource = HikariDataSource()
        dataSource.jdbcUrl = dbUrl
        dataSource.username = dbUser
        dataSource.password = dbPwd
        dataSource.driverClassName = dbDriver
        return dataSource
    }

//    private fun additionalProperties(): Properties {
//        val properties = Properties()
//        properties.setProperty("hibernate.hbm2ddl.auto", "update")
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
//        properties.setProperty("hibernate.show_sql", "true")
//        properties.setProperty("hibernate.format_sql", "true")
//        return properties
//    }

    @Bean
    fun transactionManager(): PlatformTransactionManager {
        val transactionManager = JpaTransactionManager()
        transactionManager.entityManagerFactory = entityManagerFactory().getObject()
        return transactionManager
    }
}