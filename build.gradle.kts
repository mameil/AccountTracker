import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.hidetake.gradle.swagger.generator.GenerateSwaggerCode

plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.18"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"

    //gradle-swagger-generator-plugin
    //from https://github.com/int128/gradle-swagger-generator-plugin/issues/121
    id("org.hidetake.swagger.generator") version "2.18.2"
}

group = "com.kyu9"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    //generated from https://start.spring.io/
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.oracle.database.jdbc:ojdbc8")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //Swagger
    implementation ("io.swagger.core.v3:swagger-annotations:2.2.0")
    implementation ("io.swagger:swagger-annotations:1.6.6")
    implementation ("io.springfox:springfox-boot-starter:3.0.0")

    //gradle-swagger-generator-plugin
    swaggerCodegen("io.swagger.codegen.v3:swagger-codegen-cli:3.0.25")
    // https://mvnrepository.com/artifact/com.github.jknack/handlebars
    implementation("com.github.jknack:handlebars:4.3.1")

}


//gradle-swagger-generator-plugin
//tasks {
//    register<GenerateSwaggerCode>("GenerateSwaggerCode") {
//        group = "0.action"
//
//        language = "spring"
//        inputFile = projectDir.resolve("spec/AccountBook.yaml")
//        configFile = projectDir.resolve("spec/config.json")
//        outputDir = projectDir.resolve("$buildDir/generated/swagger")
//        wipeOutputDir = false
//        components = listOf("models", "apis", "apiTests")
//    }
//}

swaggerSources {
    // Gradle recommends `register` over `create` to avoid eagerly creating/configuring items.
    register("AccountBook") {
        // Part of the "petstore" SwaggerSource
        // Need to save a reference of it here because the `this` scope changes below.
        val validationTask = validation

        setInputFile(file("$rootDir/spec/AccountBook.yaml"))

        // The method signiature is `code(@DelegatesTo(GenerateSwaggerCode) Closure closure)`
        // So best to use `delegateClosureOf<GenerateSwaggerCode>`
        // https://docs.gradle.org/current/userguide/kotlin_dsl.html#groovy_closures_from_kotlin
        code(delegateClosureOf<GenerateSwaggerCode> {
            language = "spring"
            configFile = file("$rootDir/spec/config.json")
            components = listOf("models", "apis", "invoker")
            dependsOn(validationTask)
            group = "0.action"
        })
    }
}

sourceSets {
    val main by getting
    val AccountBook by swaggerSources.getting
    main.java.srcDir("${AccountBook.code.outputDir}/src/main/java")
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
        dependsOn(tasks.named("generateSwaggerCode"))
    }

}

tasks.withType<Test> {
    useJUnitPlatform()
}
