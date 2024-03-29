package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.common.BaseJpaRepo
import com.kyu9.accountbook.domain.User
import lombok.extern.log4j.Log4j2
import org.hibernate.validator.internal.util.logging.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*
import java.util.logging.Logger

@Service
class UserRepoImpl(
    private val userRepository: UserRepository
) {

    fun getOptionalWithId(id: String): Optional<User> = userRepository.findById(id)

    fun storeEntity(t: User): User = userRepository.save(t)

    fun getOptionalWithName(name: String): Optional<User> {
        return userRepository.findByName(name)
    }

    fun getEntityWithId(id: String): User = userRepository.getById(id)

    fun removeEntityWithId(id: String) {
        userRepository.deleteById(id)
    }


}