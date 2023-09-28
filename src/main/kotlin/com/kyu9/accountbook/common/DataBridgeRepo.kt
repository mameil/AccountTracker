package com.kyu9.accountbook.common

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.data.jpa.repository.JpaRepository

interface DataBridgeRepo<
        ID : Any,
        ES : DataBridgeAble<RDB, ES>, RDB : DataBridgeAble<RDB, ES>,
        ES_REPO : ElasticsearchRepository<ES, ID>, RDB_REPO : JpaRepository<RDB, ID>> {

    val esRepo: ES_REPO
    val jpaRepo: RDB_REPO

    fun saveDataBoth(dataBridgeAble: DataBridgeAble<RDB, ES>) {
        jpaRepo.save(dataBridgeAble.toRDB())
        esRepo.save(dataBridgeAble.toES())
    }



}