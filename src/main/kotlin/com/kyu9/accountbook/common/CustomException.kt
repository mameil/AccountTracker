package com.kyu9.accountbook.common

class CustomException(error: CustomError): RuntimeException(error.msg) {

}