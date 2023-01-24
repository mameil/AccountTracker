package com.kyu9.accountbook.swagger

import com.kyu9.accountbook.application.UserService
import com.kyu9.accountbook.swagger.api.UserApiDelegate
import com.kyu9.accountbook.swagger.model.CreateUserRequestDto
import com.kyu9.accountbook.swagger.model.CreateUserResponseDto
import org.springframework.http.ResponseEntity

class UserApiControllerImpl: UserApiDelegate {
    lateinit var userService: UserService


    override fun createUser(createUserRequestDto: CreateUserRequestDto): ResponseEntity<CreateUserResponseDto> {
        return ResponseEntity.ok(userService.storeFromDto(createUserRequestDto))
    }
}