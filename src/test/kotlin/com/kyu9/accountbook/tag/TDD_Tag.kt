package com.kyu9.accountbook.tag

import com.kyu9.accountbook.common.TddFrame
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TDD_Tag: TddFrame() {

    @Test
    fun `tag repository 를 잘 가지고 오는가`() {
        assertThat(tagRepository).isNotNull()
    }


}