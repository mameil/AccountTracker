package com.kyu9.accountbook.application

import com.kyu9.accountbook.application.repository.UserRepoImpl
import com.kyu9.accountbook.application.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserService (
    private val userRepoImpl: UserRepoImpl
) {

    fun storeFromDto(){
        //todo
    }
}