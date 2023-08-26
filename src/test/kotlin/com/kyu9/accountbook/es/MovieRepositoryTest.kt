//package com.kyu9.accountbook.es
//
//import com.kyu9.accountbook.elastic.Transaction
//import com.kyu9.accountbook.elastic.TransactionRepository
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//
//@SpringBootTest(classes = [TestConfiguration::class, TransactionRepository::class])
//class MovieRepositoryTest {
//    @Autowired(required = false)
//    lateinit var transactionRepository: TransactionRepository
//
//    @Test
//    fun `save movie`() {
//        val transaction = Transaction("99", "2", "1234")
//        val saved = transactionRepository.save(transaction)
//        Assertions.assertEquals(transaction, saved)
//
//
//    }
//}