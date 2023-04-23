package com.kyu9.accountbook.aop

import lombok.extern.log4j.Log4j2
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*
import java.util.stream.Stream
import javax.servlet.http.HttpServletRequest


@Aspect
@Log4j2
class LoggingAspect {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Pointcut("within(com.kyu9.accountbook.swagger..*)")
    fun onRequest() {
    }

    @Around("onRequest()")
    @Throws(Throwable::class)
    fun logAction(joinPoint: ProceedingJoinPoint): Any? {
        val clazz: Class<*> = joinPoint.target.javaClass
        val logger = LoggerFactory.getLogger(clazz)
        var result: Any? = null
        return try {
            result = joinPoint.proceed(joinPoint.args)
            result
        } finally {
            logger.info(getRequestUrl(joinPoint, clazz))
            logger.info("parameters: ${joinPoint.args.forEach { _ -> println() }}")
            logger.info("response: $result")
        }
    }


    private fun getRequestUrl(joinPoint: JoinPoint, clazz: Class<*>): String? {
        val methodSignature: MethodSignature = joinPoint.signature as MethodSignature
        val method: Method = methodSignature.getMethod()
        val requestMapping = clazz.getAnnotation(RequestMapping::class.java) as RequestMapping
        val baseUrl = requestMapping.value[0]
        return Stream.of(GetMapping::class.java, PutMapping::class.java, PostMapping::class.java,
                PatchMapping::class.java, DeleteMapping::class.java, RequestMapping::class.java)
                .filter { mappingClass -> method.isAnnotationPresent(mappingClass) }
                .map { mappingClass -> getUrl(method, mappingClass, baseUrl) }
                .findFirst().orElse(null)
    }


    /* httpMETHOD + requestURI 를 반환 */
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
        return String.format("%s %s%s", httpMethod, baseUrl, if (value.size > 0) value[0] else "")
    }

}