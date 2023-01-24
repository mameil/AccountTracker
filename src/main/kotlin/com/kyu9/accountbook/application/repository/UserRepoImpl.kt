package com.kyu9.accountbook.application.repository

import com.kyu9.accountbook.common.BaseJpaRepo
import com.kyu9.accountbook.domain.User
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserRepoImpl(
    private val userRepository: UserRepository
): BaseJpaRepo<User, Long, UserRepository>(userRepository) {

    override fun getOptionalWithId(id: Long): Optional<User> {
        return super.getOptionalWithId(id)
    }

    override fun storeEntity(t: User): User {
        return super.storeEntity(t)
    }


}