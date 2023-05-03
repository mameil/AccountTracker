package com.kyu9.accountbook.aop

import com.kyu9.accountbook.common.CustomError
import com.kyu9.accountbook.common.CustomException
import com.kyu9.accountbook.swagger.model.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception

@ControllerAdvice
class CustomExceptionAop: ResponseEntityExceptionHandler(){

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<ErrorResponseDto>{

        return ResponseEntity(
            ErrorResponseDto(
            exception.message, exception.message?.let { CustomError.getCodeFromMsg(it) }
        ), HttpStatus.BAD_REQUEST)
    }

//    @ExceptionHandler(Exception::class)
//    fun handleCommonException(exception: Exception): ResponseEntity<ErrorResponseDto>{
//        return ResponseEntity(
//            ErrorResponseDto(
//                "000_000", exception.message
//            ), HttpStatus.BAD_REQUEST
//        )
//    }
}