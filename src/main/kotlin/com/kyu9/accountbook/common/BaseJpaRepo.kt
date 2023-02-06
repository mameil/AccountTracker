package com.kyu9.accountbook.common

import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import lombok.extern.log4j.Log4j2
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Log4j2
abstract class BaseJpaRepo<T : Any, ID : Any, REPO: JpaRepository<T, ID>> {
    private lateinit var repo: REPO

    constructor()

    constructor(repo: REPO) : this(){
        this.repo = repo
    }


    //Find Entity
    open fun getEntityWithId(id: ID): T{
        //todo exception handling >> in common
        return repo.findById(id).orElseThrow(CustomException.DATA_NOT_FOUND::doThrow)
    }

    open fun getOptionalWithId(id: ID): Optional<T> {
        return repo.findById(id)
    }


    //Save Entity
    open fun storeEntity(t: T): T {
        return repo.save(t)
    }



    //Delete Entity
    open fun removeEntityWithId(id: ID) {
        repo.deleteById(id)
    }

    open fun removeEntity(t: T) {
        repo.delete(t)
    }
}