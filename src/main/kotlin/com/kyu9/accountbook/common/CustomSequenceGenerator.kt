package com.kyu9.accountbook.common

import lombok.NoArgsConstructor
import org.hibernate.HibernateException
import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.relational.QualifiedName
import org.hibernate.dialect.Dialect
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import org.hibernate.id.enhanced.SequenceStyleGenerator
import org.hibernate.service.ServiceRegistry
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.io.Serializable
import java.math.BigDecimal
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
@NoArgsConstructor
class CustomSequenceGenerator(
    private val entityManager: EntityManager
): IdentifierGenerator {

    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Serializable {

        val conn = session!!.connection()
        val stmt = conn.createStatement()
        val rs = stmt.executeQuery("SELECT usage_transaction_seq.next_val FROM dual")
        if (rs.next()) {
            val nextVal = rs.getLong(1)
            return "UT${MyTime.toYyyymmddhhmmss(MyTime.now())}$nextVal"
        }
        throw HibernateException("Unable to generate sequence ID")

//        val query = entityManager.createNativeQuery("SELECT usage_transaction_seq.next_val FROM dual")
//        val result = query.singleResult as BigDecimal
//        val nextVal = result.toLong()

//        return "UT${MyTime.toYyyymmddhhmmss(MyTime.now())}$nextVal"
    }
}