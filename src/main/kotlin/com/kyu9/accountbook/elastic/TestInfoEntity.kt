package com.kyu9.accountbook.elastic

import com.kyu9.accountbook.common.DataBridgeAble
import com.kyu9.accountbook.common.MyTime
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class TestInfoEntity(
        @Id
        val name: String,
        var desc: String,
        val created: LocalDateTime = MyTime.now(),
        val creator: String = "KD.SHIM"
): DataBridgeAble<TestInfoEntity, TestInfo>{
    override fun toES(): TestInfo {
        return TestInfo(name, desc, creator, created)
    }

    override fun toRDB(): TestInfoEntity {
        return this
    }
}
