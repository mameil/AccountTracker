package com.kyu9.accountbook.common

enum class CustomError(val code: String, val msg: String)   {
    DATA_NOT_FOUND("000_0001", "no data"),
    USER_NOT_FOUND("000_0002", "no user")

    ;

    override fun toString(): String {
        return "[${this.code}] : ${this.msg}"
    }

    private fun toStringWithAdditionalMsg(add: String): String{
        return "$this / $add"
    }

    fun doThrow(): CustomException{
        throw CustomException(this)
    }

//    fun doThrowWithAdditionalMsg(additionalMsg: String): RuntimeException{
//        throw CustomException(this)
//    }

    companion object{
        fun getCodeFromMsg(msg: String): String{
            return CustomError.values()
                .filter {
                    it.msg == msg
                }[0].code
        }
    }
}
