package com.kyu9.accountbook.swagger.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
@javax.annotation.Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])

@Controller
@RequestMapping("\${openapi.accountBook.base-path:}")
class CheckApiController(
        @org.springframework.beans.factory.annotation.Autowired(required = false) delegate: CheckApiDelegate?
) : CheckApi {
    private val delegate: CheckApiDelegate

    init {
        this.delegate = Optional.ofNullable(delegate).orElse(object : CheckApiDelegate {})
    }

    override fun getDelegate(): CheckApiDelegate = delegate
}
