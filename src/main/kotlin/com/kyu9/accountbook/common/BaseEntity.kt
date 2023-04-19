package com.kyu9.accountbook.common

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import lombok.Data
import java.time.LocalDateTime
import javax.persistence.MappedSuperclass
import javax.persistence.PostUpdate

@Data
@MappedSuperclass
abstract class BaseEntity(
    @JsonDeserialize @JsonSerialize open val created: LocalDateTime = LocalDateTime.now(),
    @JsonDeserialize @JsonSerialize open var updated: LocalDateTime = LocalDateTime.now()
) {
    @PostUpdate
    fun postUpdate() {
        updated = LocalDateTime.now()
    }
}