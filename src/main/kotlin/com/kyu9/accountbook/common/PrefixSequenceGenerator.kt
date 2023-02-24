package com.kyu9.accountbook.common

import org.hibernate.HibernateException
import org.hibernate.MappingException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.enhanced.SequenceStyleGenerator
import org.hibernate.internal.util.config.ConfigurationHelper
import org.hibernate.service.ServiceRegistry
import org.hibernate.type.LongType
import org.hibernate.type.Type
import java.awt.print.Book
import java.io.Serializable
import java.util.*


class PrefixSequenceGenerator: SequenceStyleGenerator() {
    private var format: String? = null

    @Throws(HibernateException::class)
    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Serializable {
        return String.format(
            format!!,
            super.generate(session, `object`)
        )
    }

    @Throws(MappingException::class)
    override fun configure(type: Type?, params: Properties?, serviceRegistry: ServiceRegistry?) {
        super.configure(LongType.INSTANCE, params, serviceRegistry)
        val codeNumberSeparator: String =
            ConfigurationHelper.getString(CODE_NUMBER_SEPARATOR_PARAMETER, params, CODE_NUMBER_SEPARATOR_DEFAULT)
        val numberFormat: String =
            ConfigurationHelper.getString(NUMBER_FORMAT_PARAMETER, params, NUMBER_FORMAT_DEFAULT)


        //default success
        format = "UT${MyTime.toYyyymmddhhmmss(MyTime.now())}$codeNumberSeparator${numberFormat}"
    }

    companion object{
        const val CODE_NUMBER_SEPARATOR_PARAMETER = "codeNumberSeparator"
        const val CODE_NUMBER_SEPARATOR_DEFAULT = "_"

        const val NUMBER_FORMAT_PARAMETER = "numberFormat"
        const val NUMBER_FORMAT_DEFAULT = "%05d"
    }
}