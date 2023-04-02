package com.kyu9.accountbook.swagger

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import springfox.documentation.annotations.ApiIgnore

@Controller
@ApiIgnore
class SwaggerRedirectController {

//    @GetMapping("/swagger-ui.html")
//    fun swaggerUi(): String {
//        return "redirect:/webjars/swagger-ui/index.html?url=/spec/AccountBook.yaml"
//    }
}