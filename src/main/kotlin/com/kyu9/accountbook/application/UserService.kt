package com.kyu9.accountbook.application

import com.kyu9.accountbook.application.repository.UserRepoImpl
import com.kyu9.accountbook.common.MyTime
import com.kyu9.accountbook.domain.User
import com.kyu9.accountbook.swagger.model.CreateUserRequestDto
import com.kyu9.accountbook.swagger.model.CreateUserResponseDto
import com.kyu9.accountbook.swagger.model.GetUserResponseDto
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class UserService (
    private val userRepoImpl: UserRepoImpl
) {

    fun storeFromDto(createDto: CreateUserRequestDto): CreateUserResponseDto {

        return User(
            id = createDto.id!!,
            password = createDto.password!!,
            name = createDto.name!!
        )
            .apply {userRepoImpl.storeEntity(this) }
            .let { user ->
            CreateUserResponseDto(
                id = user.id,
                password = user.password,
                name = user.name
            )
        }
    }

    fun getFromDto(id: String): GetUserResponseDto {
        return userRepoImpl.getEntityWithId(id)
            .let { user ->
                GetUserResponseDto(
                    id = user.id,
                    password = user.password,
                    name = user.name,
                    createdAt = MyTime.toYyyymmddhhmmss(user.created),
                    updatedAt = MyTime.toYyyymmddhhmmss(user.updated)
                )
            }
    }
}