package com.kyu9.accountbook.swagger.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;
@javax.annotation.Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])

@Controller
@RequestMapping("\${openapi.accountBook.base-path:}")
class SampleApiController(
        @org.springframework.beans.factory.annotation.Autowired(required = false) delegate: SampleApiDelegate?
) : SampleApi {
    private val delegate: SampleApiDelegate

    init {
        this.delegate = Optional.ofNullable(delegate).orElse(object : SampleApiDelegate {})
    }

    override fun getDelegate(): SampleApiDelegate = delegate
}
