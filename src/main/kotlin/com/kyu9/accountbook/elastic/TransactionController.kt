package com.kyu9.accountbook.elastic

import com.kyu9.accountbook.application.TransactionService
import com.kyu9.accountbook.elastic.dto.PostTranReqDto
import com.kyu9.accountbook.swagger.model.PostTranRequestDto
import com.kyu9.accountbook.swagger.model.PostTransResponseDto
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RequestMapping("/es")
@Controller
@RequiredArgsConstructor
class TransactionController(
        private val transactionService: ETService
) {

    @PostMapping(
            value = ["/transaction"],
            produces = ["application/json"],
            consumes = ["application/json"]
    )
    fun postTransaction( @Valid @RequestBody dto: PostTranReqDto
    ): ResponseEntity<String> {
        return ResponseEntity.ok(transactionService.postTransaction(dto.userId, dto.userName, dto.amt))
    }

    @PostMapping(
            value = ["/transaction/list"],
            produces = ["application/json"],
            consumes = ["application/json"]
    )
    fun postListTransaction( @Valid @RequestBody dto: List<PostTranReqDto>
    ): ResponseEntity<Void> {
        dto.forEach { transactionService.postTransaction(it.userId, it.userName, it.amt) }
        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/transaction/{id}")
    fun getSingleTransaction(@PathVariable id: String): ResponseEntity<Transaction> {
        return ResponseEntity.ok(transactionService.getTransactionById(id))
    }
}