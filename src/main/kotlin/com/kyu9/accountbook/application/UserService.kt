package com.kyu9.accountbook.application

import com.kyu9.accountbook.application.repository.UserRepoImpl
import com.kyu9.accountbook.common.MyTime
import com.kyu9.accountbook.domain.User
import com.kyu9.accountbook.swagger.model.*
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

    fun updateFromDto(userId: String, updateDto: UpdateUserRequestDto): GetUserResponseDto{
        return userRepoImpl.getEntityWithId(userId)
            .apply {
                if(updateDto.name!=null) name = updateDto.name
                if(updateDto.password!=null) password = updateDto.password
            }.let(userRepoImpl::storeEntity)
            .let {
                GetUserResponseDto(
                    id = it.id,
                    name = it.name,
                    password = it.password,
                    createdAt = MyTime.toYyyymmddhhmmss(it.created),
                    updatedAt = MyTime.toYyyymmddhhmmss(it.updated)
                )
            }
    }

    fun removeUserById(id: String){
        userRepoImpl.removeEntityWithId(id)
    }
}