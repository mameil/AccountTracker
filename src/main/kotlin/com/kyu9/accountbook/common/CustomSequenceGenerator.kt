package com.kyu9.accountbook.common

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.relational.QualifiedName
import org.hibernate.dialect.Dialect
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import org.hibernate.id.enhanced.SequenceStyleGenerator
import org.hibernate.service.ServiceRegistry
import org.springframework.beans.factory.annotation.Autowired
import java.io.Serializable
import java.util.*
import javax.persistence.EntityManager

class CustomSequenceGenerator: IdentifierGenerator {

    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Serializable {
        val prefix = "ABC"
        //get sequence value from oracle sequence
        return "$prefix${MyTime.now()}"
    }
}