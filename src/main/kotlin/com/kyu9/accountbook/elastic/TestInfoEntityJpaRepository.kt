package com.kyu9.accountbook.elastic

import org.springframework.data.jpa.repository.JpaRepository

interface TestInfoEntityJpaRepository: JpaRepository<TestInfoEntity, String> {
}