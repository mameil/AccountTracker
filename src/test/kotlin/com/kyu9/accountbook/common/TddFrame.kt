package com.kyu9.accountbook.common

import com.kyu9.accountbook.application.repository.TagRepository
import com.kyu9.accountbook.application.repository.UsageTransactionRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@SpringBootTest //전체 bean들을 context에다가 등록해줌
@ActiveProfiles("application-test.yml, application-git.properties")
@TestPropertySource(locations = ["classpath:application-test.yml", "classpath:application-git.properties"])
//@DataJpaTest //jpa 관련된 bean들만 context에다가 등록해줌
class TddFrame {
    @Autowired
    lateinit var tagRepository: TagRepository

    @Autowired
    lateinit var usageTransactionRepository: UsageTransactionRepository

}