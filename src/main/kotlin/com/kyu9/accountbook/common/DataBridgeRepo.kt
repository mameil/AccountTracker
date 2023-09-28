package com.kyu9.accountbook.common

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.data.jpa.repository.JpaRepository

interface DataBridgeRepo<
        ID : Any,
        ES : DataBridgeAble<JPA, ES>, JPA : DataBridgeAble<JPA, ES>,
        ESREPO : ElasticsearchRepository<ES, ID>, JPAREPO : JpaRepository<JPA, ID>> {

    val esRepo: ESREPO
    val jpaRepo: JPAREPO

    fun saveDataBoth(dataBridgeAble: DataBridgeAble<JPA, ES>) {
        jpaRepo.save(dataBridgeAble.toRDB())
        esRepo.save(dataBridgeAble.toES())
    }



}