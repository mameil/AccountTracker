package com.kyu9.accountbook.aop

import lombok.extern.log4j.Log4j2
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.CodeSignature
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*
import java.util.stream.Stream


@Aspect
@Log4j2
class ApiLoggingAop {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Pointcut("within(com.kyu9.accountbook.swagger.api.*)")
    fun onRequest() {
    }

    @Around("onRequest()")
    fun logRequestAndResponse(joinPoint: ProceedingJoinPoint): Any? {
//        val requestAttributes = RequestContextHolder.getRequestAttributes()
//        val request = (requestAttributes as? ServletRequestAttributes)?.request

//        val methodName = joinPoint.signature.name
//        val className = joinPoint.signature.declaringTypeName
//        val args = joinPoint.args.joinToString(", ") { it.toString() }
        val requestUrl = getRequestUrl(joinPoint, joinPoint.target.javaClass)

        logger.info("#######################################API CALLED#######################################")
//        logger.info("[REQ] {}::{}({})", className, methodName, args)
        logger.info("[REQ] {}::{}", removePlaceholder(requestUrl), params(joinPoint))

        val result = joinPoint.proceed()

        //todo 우선 지금 그냥 result 해버리면 좀 불편하게 나옴
        //<200 OK OK,[GetSingleTagDto(id=1, name=음식, color=RED, created=20230327213248, updated=20230327213248), GetSingleTagDto(id=2, name=디저트, color=ORANGE, created=20230327213310, updated=20230327213310), GetSingleTagDto(id=3, name=인터넷쇼핑, color=YELLOW, created=20230327213319, updated=20230327213319), GetSingleTagDto(id=4, name=편의점, color=GREEN, created=20230327213329, updated=20230327213329), GetSingleTagDto(id=5, name=술값, color=BLUE, created=20230327213338, updated=20230327213338), GetSingleTagDto(id=6, name=생활용품, color=NAVY, created=20230327213347, updated=20230327213347), GetSingleTagDto(id=7, name=미용, color=PURPLE, created=20230327213355, updated=20230327213355), GetSingleTagDto(id=8, name=통신비, color=SKYBLUE, created=20230327213406, updated=20230327213406), GetSingleTagDto(id=9, name=의류, color=GRAY, created=20230327213417, updated=20230327213417)],[]>
        //이런식으로 나오는데 파싱 방법에 대해서 고민필요함
//        logger.info("[RES] {}::{}() => {}", className, methodName, result)
        logger.info("[RES] {}", result)
        logger.info("########################################################################################")

        return result
    }

    private fun removePlaceholder(input: String?): String {
        val regex = "\\$\\{.*?\\}".toRegex()
        return if(input == null) "Url Parsed Error!!" else regex.replace(input, "")
    }

    /*
    https://gaemi606.tistory.com/entry/Spring-Boot-AOP를-활용해-로그-출력하기-REST-API >> thanks
    */
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
        var httpMethod: String? = ""
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