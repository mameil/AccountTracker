package com.kyu9.accountbook.aop

import lombok.extern.log4j.Log4j2
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.CodeSignature
import org.aspectj.lang.reflect.MethodSignature
import org.json.simple.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*
import java.util.stream.Stream


@Aspect
@Log4j2
class LoggingAspect {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Pointcut("within(com.kyu9.accountbook.swagger.api.*)")
    fun onRequest() {
    }

    @Around("onRequest()")
    fun logRequestAndResponse(joinPoint: ProceedingJoinPoint): Any? {
        val requestAttributes = RequestContextHolder.getRequestAttributes()
        val request = (requestAttributes as? ServletRequestAttributes)?.request

        val methodName = joinPoint.signature.name
        val className = joinPoint.signature.declaringTypeName
        val args = joinPoint.args.joinToString(", ") { it.toString() }
        val requestUrl = getRequestUrl(joinPoint, joinPoint.target.javaClass)

//        logger.info("[REQ] {}::{}({})", className, methodName, args)
        logger.info("[REQ] {}::{}", removePlaceholder(requestUrl), params(joinPoint))

        val result = joinPoint.proceed()

//        logger.info("[RES] {}::{}() => {}", className, methodName, result)
        logger.info("[RES] {}", result)

        return result
    }

    private fun removePlaceholder(input: String?): String {
        val regex = "\\$\\{.*?\\}".toRegex()
        return if(input == null) "Url Parsed Error!!" else regex.replace(input, "")
    }

    /*
    https://gaemi606.tistory.com/entry/Spring-Boot-AOP를-활용해-로그-출력하기-REST-API >> 감사합니당
    * */
    private fun params(joinPoint: JoinPoint): Map<*, *>? {
        val codeSignature = joinPoint.signature as CodeSignature
        val parameterNames = codeSignature.parameterNames
        val args = joinPoint.args
        val params: MutableMap<String, Any> = HashMap()
        for (i in parameterNames.indices) {
            params[parameterNames[i]] = args[i]
        }
        return params
    }


    private fun getRequestUrl(joinPoint: JoinPoint, clazz: Class<*>): String? {
        val methodSignature: MethodSignature = joinPoint.signature as MethodSignature
        val method: Method = methodSignature.method
        val requestMapping = clazz.getAnnotation(RequestMapping::class.java) as RequestMapping
        val baseUrl = requestMapping.value[0]
        return Stream.of(GetMapping::class.java, PutMapping::class.java, PostMapping::class.java,
                PatchMapping::class.java, DeleteMapping::class.java, RequestMapping::class.java)
                .filter { mappingClass -> method.isAnnotationPresent(mappingClass) }
                .map { mappingClass -> getUrl(method, mappingClass, baseUrl) }
                .findFirst().orElse(null)
    }


    private fun getUrl(method: Method, annotationClass: Class<out Annotation>, baseUrl: String): String? {
        val annotation: Annotation = method.getAnnotation(annotationClass)
        val value: Array<String>
        var httpMethod: String? = null
        try {
            value = annotationClass.getMethod("value").invoke(annotation) as Array<String>
            httpMethod = annotationClass.simpleName.replace("Mapping", "").uppercase(Locale.getDefault())
        } catch (e: IllegalAccessException) {
            return null
        } catch (e: NoSuchMethodException) {
            return null
        } catch (e: InvocationTargetException) {
            return null
        }
        return String.format("%s %s%s", httpMethod, baseUrl, if (value.isNotEmpty()) value[0] else "")
    }

}