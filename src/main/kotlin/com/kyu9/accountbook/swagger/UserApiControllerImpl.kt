package com.kyu9.accountbook.swagger

import com.kyu9.accountbook.application.UserService
import com.kyu9.accountbook.swagger.api.UserApiDelegate
import com.kyu9.accountbook.swagger.model.CreateUserRequestDto
import com.kyu9.accountbook.swagger.model.CreateUserResponseDto
import com.kyu9.accountbook.swagger.model.GetUserResponseDto
import com.kyu9.accountbook.swagger.model.UpdateUserRequestDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class UserApiControllerImpl(
    private val userService: UserService
)
    : UserApiDelegate {

    override fun createUser(createUserRequestDto: CreateUserRequestDto): ResponseEntity<CreateUserResponseDto> {
        return ResponseEntity.ok(userService.storeFromDto(createUserRequestDto))
    }

    override fun getUser(userId: String): ResponseEntity<GetUserResponseDto> {
        return ResponseEntity.ok(userService.getFromDto(userId))
    }

    override fun updateUser(
        userId: String,
        updateUserRequestDto: UpdateUserRequestDto
    ): ResponseEntity<GetUserResponseDto> {
        return ResponseEntity.ok(userService.updateFromDto(userId, updateUserRequestDto))
    }

    override fun deleteUser(userId: String): ResponseEntity<Unit> {
        userService.removeUserById(userId)
        return ResponseEntity(HttpStatus.OK)
    }
}