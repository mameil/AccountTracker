package com.kyu9.accountbook.common

import lombok.NoArgsConstructor
import lombok.RequiredArgsConstructor
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

abstract class BaseJpaRepo<T, ID, REPO: JpaRepository<T, ID>> {
    private lateinit var repo: REPO

    constructor()

    constructor(repo: REPO) : this(){
        this.repo = repo
    }


    //Find Entity
    open fun getEntityWithId(id: ID): T{
        //todo exception handling >> in common
        return repo.findById(id).orElseThrow()
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