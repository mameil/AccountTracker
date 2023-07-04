package com.kyu9.accountbook.elastic

import org.springframework.data.domain.Page
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.lang.Nullable
import java.awt.print.Pageable

@NoRepositoryBean
interface ElasticsearchRepository<T, ID>: PagingAndSortingRepository<T, ID> {
    fun searchSimilar(entity: T, @Nullable fields: Array<String>, pageable: Pageable): Page<T>
}