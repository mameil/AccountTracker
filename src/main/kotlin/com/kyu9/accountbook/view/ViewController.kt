package com.kyu9.accountbook.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class ViewController {

    @GetMapping("/view/main")
    fun mainView(): String{
        return "index"
    }
}