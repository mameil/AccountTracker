package com.kyu9.accountbook.application

import com.kyu9.accountbook.application.repository.UserRepoImpl
import com.kyu9.accountbook.application.repository.UserRepository
import com.kyu9.accountbook.domain.User
import com.kyu9.accountbook.swagger.model.CreateUserRequestDto
import com.kyu9.accountbook.swagger.model.CreateUserResponseDto
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserService (
    private val userRepoImpl: UserRepoImpl
) {

    fun storeFromDto(createDto: CreateUserRequestDto): CreateUserResponseDto {

        val user = User(
            id = createDto.id!!,
            password = createDto.password!!,
            name = createDto.name!!
        )
        userRepoImpl.storeEntity(user)

        return CreateUserResponseDto(
            id = user.id,
            password = user.password,
            name = user.name
        )
    }
}