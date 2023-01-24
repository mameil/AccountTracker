package com.kyu9.accountbook.swagger.api

import com.kyu9.accountbook.swagger.model.CreateUserRequestDto
import com.kyu9.accountbook.swagger.model.CreateUserResponseDto
import com.kyu9.accountbook.swagger.model.ErrorResponseDto
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.core.io.Resource

import java.util.Optional

/**
 * A delegate to be called by the {@link UserApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@javax.annotation.Generated(value = ["org.openapitools.codegen.languages.KotlinSpringServerCodegen"])
interface UserApiDelegate {

    fun getRequest(): Optional<NativeWebRequest> = Optional.empty()

    /**
     * @see UserApi#createUser
     */
    fun createUser(createUserRequestDto: CreateUserRequestDto): ResponseEntity<CreateUserResponseDto> {
        getRequest().ifPresent { request ->
            for (mediaType in MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    ApiUtil.setExampleResponse(request, "application/json", "{  \"createdAt\" : \"20230124013000\",  \"password\" : \"qwer1234\",  \"name\" : \"kdshim\",  \"id\" : \"loginId\",  \"updatedAt\" : \"20230124013000\"}")
                    break
                }
            }
        }
        return ResponseEntity(HttpStatus.NOT_IMPLEMENTED)

    }

}
