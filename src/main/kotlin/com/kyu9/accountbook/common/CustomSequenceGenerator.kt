package com.kyu9.accountbook.common

import jakarta.annotation.PostConstruct
import lombok.NoArgsConstructor
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.persistence.PersistenceContext

@Service
class CustomSequenceGenerator @Autowired constructor(
): IdentifierGenerator {
    //todo why this not working??
//    @PersistenceContext
//    private lateinit var entityManager: EntityManager


    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): String {
        val emf = Persistence.createEntityManagerFactory("default")
        val em = emf.createEntityManager()

        val query = em.createNativeQuery("""SELECT usage_transaction_seq.next_val FROM dual""")
        val result = query.singleResult as BigDecimal
        val nextVal = result.toLong()

        return "UT${MyTime.toYyyymmddhhmmss(MyTime.now())}$nextVal"
    }
}