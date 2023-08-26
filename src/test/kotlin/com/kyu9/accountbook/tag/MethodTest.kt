package com.kyu9.accountbook.tag

import com.kyu9.accountbook.application.TagService
import com.kyu9.accountbook.common.TestFrame
import com.kyu9.accountbook.swagger.model.PostSingleTagDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


class MethodTest: TestFrame(){
    @Autowired
    private lateinit var tagService: TagService

    @Test
    fun deadlock_test() {
        // this is first row
        val tt1 = tagService.storeTag(PostSingleTagDto("135", "first_test"))

        // this is second row
        val tt2 = tagService.storeTag(PostSingleTagDto("405", "second_test"))


        //different thread testing
        val tran1 = Thread {
            tagService.forDeadLockTest1(tt1.id?.toLong()!!)

            tagService.forDeadLockTest2(tt2.id?.toLong()!!)
        }

        val trans2 = Thread {
            tagService.forDeadLockTest1(tt2.id?.toLong()!!)

            Thread.sleep(100)

            tagService.forDeadLockTest2(tt1.id?.toLong()!!)
        }

        tran1.start()
        trans2.start()

        tran1.join()
        trans2.join()
    }

}