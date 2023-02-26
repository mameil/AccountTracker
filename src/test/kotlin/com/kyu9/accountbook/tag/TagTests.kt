package com.kyu9.accountbook.tag

import com.kyu9.accountbook.common.TestFrame
import com.kyu9.accountbook.swagger.model.GetSingleTagDto
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.MockMvc
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

    @Test
    fun findAllTest(){
        postPerform(
            desc = "태그를 생성한다",
            url = "/tag",
            req = "{\n  \"name\": \"테스트용 태그이름1\",\n  \"color\": \"RED\"\n}"
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect{
                MockMvcResultMatchers.jsonPath("$.name").value("테스트용 태그이름")
                MockMvcResultMatchers.jsonPath("$.color").value("RED")
            }


        postPerform(
            desc = "태그를 생성한다",
            url = "/tag",
            req = "{\n  \"name\": \"테스트용 태그이름2\",\n  \"color\": \"RED\"\n}"
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect{
                MockMvcResultMatchers.jsonPath("$.name").value("테스트용 태그이름2")
                MockMvcResultMatchers.jsonPath("$.color").value("RED")
            }

        getPerform(
            desc = "모든 태그가 있어야만 한다",
            url = "/tag/list"
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("테스트용 태그이름1"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].color").value("RED"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("테스트용 태그이름2"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].color").value("RED"))

//todo >> 테스트 코드가 각각 돌면서 새로 부트를 띄워야 id에 대한 정리가 잘될 듯한딩 지금은 full test 돌리면 모든 아이디 seq은 계속 올라감
//            .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
//            .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
    }

    @Test
    fun deleteTest(){
        val tagId = postPerform(
            desc = "태그를 생성한다",
            url = "/tag",
            req = "{\n  \"name\": \"테스트용 태그이름\",\n  \"color\": \"RED\"\n}"
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect{
                MockMvcResultMatchers.jsonPath("$.name").value("테스트용 태그이름")
                MockMvcResultMatchers.jsonPath("$.color").value("RED")
            }
            .andReturn().response.contentAsString
            .let{
                objectMapper.readValue(it, GetSingleTagDto::class.java).id
            }

        getPerform(
            desc = "모든 태그가 있어야만 한다",
            url = "/tag/list"
        )
            .andDo(MockMvcResultHandlers.print())

        deletePerform(
            desc = "태그 삭제",
            url = "/tag/$tagId"
        )

        getPerform(
            desc = "모든 태그가 있어야만 한다",
            url = "/tag/list"
        )
            .andDo(MockMvcResultHandlers.print())
    }
}