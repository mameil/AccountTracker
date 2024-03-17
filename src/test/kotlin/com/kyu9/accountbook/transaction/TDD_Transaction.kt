package com.kyu9.accountbook.transaction

import com.kyu9.accountbook.common.TddFrame
import com.kyu9.accountbook.domain.Tag
import com.kyu9.accountbook.domain.UsageTransaction
import com.kyu9.accountbook.swagger.TransactionApiControllerImpl
import org.junit.Ignore
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime

class TDD_Transaction: TddFrame() {
    @Autowired
    lateinit var transactionApiControllerImpl: TransactionApiControllerImpl

    @Test
    @Disabled
    fun `태그별_금액합산_컨트롤러_테스트`(){
        //arrange
        val tagList: List<Tag> = arrayListOf(
                Tag(id = 1L, name = "테스트 태그1", color = "RED"),
                Tag(id = 2L, name = "테스트 태그2", color = "GREEN"),
                Tag(id = 3L, name = "테스트 태그3", color = "BLUE"),
                Tag(id = 4L, name = "테스트 태그4", color = "BLACK"),
        )
        tagRepository.saveAll(tagList)

        val transactionList = arrayListOf(
                UsageTransaction(userId = "mameil", amount = 1000, registered = LocalDateTime.now(), title = "test", content = "test", tagId = 1, moneyType = "MINE"),
                UsageTransaction(userId = "mameil", amount = 1000, registered = LocalDateTime.now(), title = "test", content = "test", tagId = 2, moneyType = "MINE"),
                UsageTransaction(userId = "mameil", amount = 1000, registered = LocalDateTime.now(), title = "test", content = "test", tagId = 2, moneyType = "MINE"),
                UsageTransaction(userId = "mameil", amount = 1000, registered = LocalDateTime.now(), title = "test", content = "test", tagId = 3, moneyType = "MINE"),
                UsageTransaction(userId = "mameil", amount = 1000, registered = LocalDateTime.now(), title = "test", content = "test", tagId = 3, moneyType = "MINE"),
                UsageTransaction(userId = "mameil", amount = 1000, registered = LocalDateTime.now(), title = "test", content = "test", tagId = 3, moneyType = "MINE"),
                UsageTransaction(userId = "mameil", amount = 1000, registered = LocalDateTime.now(), title = "test", content = "test", tagId = 4, moneyType = "MINE"),
                UsageTransaction(userId = "mameil", amount = 1000, registered = LocalDateTime.now(), title = "test", content = "test", tagId = 4, moneyType = "MINE"),
                UsageTransaction(userId = "mameil", amount = 1000, registered = LocalDateTime.now(), title = "test", content = "test", tagId = 4, moneyType = "MINE"),
                UsageTransaction(userId = "mameil", amount = 1000, registered = LocalDateTime.now(), title = "test", content = "test", tagId = 4, moneyType = "MINE")
        )
        usageTransactionRepository.saveAll(transactionList)

        //act
        val response = transactionApiControllerImpl.getTransactionsGroupByTag()

        //assert
        Assertions.assertEquals(1, response.body?.groupList?.get(0)?.tagId)
        Assertions.assertEquals("태스트 태그1", response.body?.groupList?.get(0)?.name)
        Assertions.assertEquals(1000, response.body?.groupList?.get(0)?.amountSum)

        Assertions.assertEquals(2, response.body?.groupList?.get(1)?.tagId)
        Assertions.assertEquals("태스트 태그2", response.body?.groupList?.get(1)?.name)
        Assertions.assertEquals(2000, response.body?.groupList?.get(1)?.amountSum)

        Assertions.assertEquals(3, response.body?.groupList?.get(2)?.tagId)
        Assertions.assertEquals("태스트 태그3", response.body?.groupList?.get(2)?.name)
        Assertions.assertEquals(3000, response.body?.groupList?.get(2)?.amountSum)

        Assertions.assertEquals(4, response.body?.groupList?.get(3)?.tagId)
        Assertions.assertEquals("태스트 태그4", response.body?.groupList?.get(3)?.name)
        Assertions.assertEquals(4000, response.body?.groupList?.get(3)?.amountSum)
    }
}