package com.kyu9.accountbook.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@RequestMapping("/view")
class ViewController {

//    @GetMapping("/main")
//    fun index(): String {
//        return "index"
//    }

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @GetMapping("/login/found")
    fun findId(): String {
        return "userFound"
    }

    @GetMapping("/tran")
    fun main(): String {
        return "add_transaction"
    }

    @GetMapping("/tran/today")
    fun today(): String {
        return "today_transaction"
    }

}