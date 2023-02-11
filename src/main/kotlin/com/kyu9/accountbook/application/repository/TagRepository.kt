package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.domain.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag, Long> {
}