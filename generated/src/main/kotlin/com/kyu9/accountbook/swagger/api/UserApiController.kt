package com.kyu9.accountbook.swagger.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
@javax.annotation.Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])

@Controller
@RequestMapping("\${openapi.accountBook.base-path:}")
class UserApiController(
        @org.springframework.beans.factory.annotation.Autowired(required = false) delegate: UserApiDelegate?
) : UserApi {
    private val delegate: UserApiDelegate

    init {
        this.delegate = Optional.ofNullable(delegate).orElse(object : UserApiDelegate {})
    }

    override fun getDelegate(): UserApiDelegate = delegate
}
