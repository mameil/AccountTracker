package com.kyu9.accountbook.common

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.data.jpa.repository.JpaRepository


/**
 * 해당 인터페이스는 Elasticsearch, JPA 두 데이터베이스에 데이터를 함께 관리 시 사용
 * 필요사항
 * Elastic 객체 / Jpa 객체
 * ElasticRepository / JpaRepository
 *
 * 각각의 객체 클래스에 DataBridgeAble 인터페이스를 상속받아 ES / JPA 간 데이터 변환을 위한 메소드 필수 구현
 * 이후엔 원하는 데이터의 레포지토리라는 이름의 클래스를 생성하고 요걸 상속받아서 사용해주면 된다
 * repository 도 자식으로 가지고 있기 떄문에 하나의 클래스에서 두 데이터 저장소 모두에 접근이 가능
 */
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