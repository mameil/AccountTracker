package com.kyu9.accountbook.tag

import com.kyu9.accountbook.common.TestFrame
import com.kyu9.accountbook.swagger.model.GetSingleTagDto
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class TagTests: TestFrame(){

    @Test
    fun postTest(){

        postPerform(
            desc = "태그를 생성한다",
            url = "/tag",
            req = "{\n  \"name\": \"테스트용 태그이름\",\n  \"color\": \"RED\"\n}"
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect{
                MockMvcResultMatchers.jsonPath("$.name").value("테스트용 태그이름")
                MockMvcResultMatchers.jsonPath("$.color").value("RED")
            }
    }

    @Test
    fun autoIdCreationTest(){
        val idList: MutableList<Int> = mutableListOf()

        for (i in 1..100){
            postPerform(
                desc = "태그를 생성한다",
                url = "/tag",
                req = "{\n  \"name\": \"테스트$i\",\n  \"color\": \"RED\"\n}"
            )
                .andDo(MockMvcResultHandlers.print())
                .andExpect{
                    MockMvcResultMatchers.jsonPath("$.name").value("테스트$i")
                    MockMvcResultMatchers.jsonPath("$.color").value("RED")
                }
                .andReturn().response.contentAsString
                .let {
                    objectMapper.readValue(it, GetSingleTagDto::class.java)
                        .let {
                            idList.add(it.id!!)
                        }
                }
        }

        for((idx, id) in idList.withIndex()){
            assert(id == idx + 1)
        }
    }
}