package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.common.BaseJpaRepo
import com.kyu9.accountbook.domain.User
import lombok.extern.log4j.Log4j2
import org.hibernate.validator.internal.util.logging.LoggerFactory
import org.springframework.boot.actuate.autoconfigure.health.HealthEndpointProperties.Logging
import org.springframework.stereotype.Service
import java.util.*
import java.util.logging.Logger

@Service
class UserRepoImpl(
    private val userRepository: UserRepository
): BaseJpaRepo<User, String, UserRepository>(userRepository) {

    override fun getOptionalWithId(id: String): Optional<User> {
        return super.getOptionalWithId(id)
    }

    override fun storeEntity(t: User): User {
        return super.storeEntity(t)
    }


}