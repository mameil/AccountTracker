package com.kyu9.accountbook.tag

import com.kyu9.accountbook.AccountBookApplication
import com.kyu9.accountbook.application.TagService
import com.kyu9.accountbook.swagger.model.PostSingleTagDto
import org.junit.Ignore
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [AccountBookApplication::class])
@ActiveProfiles("application.yml")
class RealDBTest{
    @Autowired
    private lateinit var tagService: TagService

    @Test
    @Ignore
    fun deadlock_test() {
        // this is first row
        val tt1 = tagService.storeTag(PostSingleTagDto("135", "first_test"))

        // this is second row
        val tt2 = tagService.storeTag(PostSingleTagDto("405", "second_test"))


        //different thread testing
        val tran1 = Thread {
            tagService.minusTransaction(tt1.id?.toLong()!!, tt2.id?.toLong()!!)
        }

        val trans2 = Thread {
            tagService.plusTransaction(tt2.id?.toLong()!!, tt1.id?.toLong()!!)
        }

        tran1.start()
        trans2.start()

        tran1.join()
        trans2.join()
        //왜 원하는 대로 안되는 거지 ...
        //지금 이상황이면 minusTransaction 내 에서 minus 2개가 일어나기 때문에
        //가장 처음에 실행된 트랜잭션 하나와 각각의 추가적인 트랜잭션에 추가되어서 실행된다


        tagService.getAllTags().forEach {
            println(it)
        }
    }

}