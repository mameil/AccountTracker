package com.kyu9.accountbook.config

import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
class Config(
    @PersistenceContext
    private val entityManager: EntityManager
) {


}