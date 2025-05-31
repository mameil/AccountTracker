import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("org.springframework.boot") version "2.5.7"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.18"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
    kotlin("kapt") version "1.7.21"

    //git information
    id("com.gorylenko.gradle-git-properties") version "2.4.1"

    //swagger plugin
    id("org.openapi.generator") version "5.1.1"
    id("com.ewerk.gradle.plugins.querydsl") version "1.0.10"

    jacoco
}

group = "com.kyu9"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

val queryDslVersion = "5.0.0"

jacoco {
    toolVersion = "0.8.7" // 사용하려는 Jacoco 버전
}

configurations {
    all {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
    }
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }

}

repositories {
    mavenCentral()
    maven{
        url = uri("https://repo.maven.apache.org/maven2/")
        isAllowInsecureProtocol = true
    }
}

dependencies {
    //generated from https://start.spring.io/
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.boot:spring-boot-starter-log4j2:2.7.9")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework:spring-aop") //2.3.4 이후부터는 따로 필요 없음
    implementation ("org.springframework:spring-context:5.3.15")

    //object mapper
    implementation("com.fasterxml.jackson.core:jackson-databind")

    //guava library
    implementation("com.google.guava:guava:30.1.1-jre")

    //gson
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    compile("com.fasterxml.jackson.module:jackson-module-kotlin")

    //view
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")


    //cache
    implementation("org.springframework.boot:spring-boot-starter-cache")
//    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")


    //db
    runtimeOnly("com.oracle.database.jdbc:ojdbc8")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("mysql:mysql-connector-java")
    implementation("org.hibernate:hibernate-core:5.4.1.Final")
    implementation("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.0.Final")

    //queryDSL
    implementation ("com.querydsl:querydsl-jpa:${queryDslVersion}")
    implementation ("com.querydsl:querydsl-apt:${queryDslVersion}")
    implementation ("com.querydsl:querydsl-core:${queryDslVersion}")
    kapt("com.querydsl:querydsl-apt:${queryDslVersion}:jpa")


    //kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")


    //common
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.slf4j:jcl-over-slf4j")
    implementation("ch.qos.logback:logback-classic")
    implementation("ch.qos.logback:logback-core")
    implementation("org.slf4j:slf4j-api")
    implementation("org.apache.logging.log4j:log4j-api")
    implementation("org.apache.logging.log4j:log4j-to-slf4j")


    //Swagger - plugin
//    implementation("org.openapitools:openapi-generator-gradle-plugin:6.0.0")

    //Swagger - SpringDoc(not used)
//    implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
//    implementation("org.springdoc:springdoc-openapi-common:1.6.9")
//    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.9")
//    implementation("jakarta.annotation:jakarta.annotation-api:1.3.5")
//    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.6.9")

    //Swagger - SpringFox
    implementation("io.springfox:springfox-swagger2:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    //Openapi Generator
    implementation("io.swagger:swagger-annotations:1.6.2")
    implementation(group="javax.validation", name="validation-api", version="2.0.1.Final")
    implementation(group="org.openapitools", name="jackson-databind-nullable", version="0.2.1")
    //config > https://openapi-generator.tech/docs/generators/spring/


    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2:2.0.202")

    //kotest
//    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.4.3")
//    testImplementation("io.kotest:kotest-runner-junit5:4.4.3")
//    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
//    testImplementation("io.kotest:kotest-assertions-core-jvm:4.4.3")
//    implementation("io.kotest:kotest-extensions-spring:4.4.3")
    //mockk
//    testImplementation("io.mockk:mockk:1.12.4")

    //elastic search
    implementation("org.springframework.boot:spring-boot-starter-data-elasticsearch")
    testImplementation("org.testcontainers:elasticsearch:1.16.3")
    testImplementation("org.testcontainers:junit-jupiter:1.16.3")

    testImplementation("pl.allegro.tech:embedded-elasticsearch:2.7.0")

}

springBoot{
    //kotlin 파일 같은 경우에는 컴파일될 때 파일명에 자동으로 뒤에 Kt가 붙음
    mainClass.set("com.kyu9.accountbook.ApplicationKt")
}

buildscript {
    dependencies {
        "classpath"("org.eclipse.jgit:org.eclipse.jgit:5.13.0.202109080827-r") {
            isForce = true
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }

}


tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generateFromYaml"){
    inputSpec.set("${projectDir}/src/main/resources/specs/AccountBook.yaml")
    outputDir.set("${projectDir}/generated")
    configFile.set("${projectDir}/src/main/resources/specs/spec.json")
    generatorName.set("kotlin-spring")
    group = "0.action"

    //이거 config.json에 넣어도 안되던데 >> 코틀린 스프링부트에서는 적용이 안됨
    //생성된 ApiUtils에서 javax을 못읽어서 보니까 boot3.0.0부터는 javax가 아니라 jakarata로 바뀌었음 그래서 이걸로 바꿀러고했는데 아무튼 코틀린에선 사용 못함
//    configOptions.put("useSpringBoot3", "true")
}

val querydslDir = "$buildDir/generated/querydsl"

sourceSets.getByName("main") {
    java.srcDir(querydslDir)
}

configurations {
    named("querydsl") {
        extendsFrom(configurations.compileClasspath.get())
    }
}

task<Delete>("removeGeneratedFromYaml"){
    group = "0.action"
    delete(
        fileTree("${projectDir}/generated/src/main/kotlin/com/kyu9/accountbook/swagger/model"),
        fileTree("${projectDir}/generated/src/main/kotlin/com/kyu9/accountbook/swagger/api"),
        fileTree("${projectDir}/generated/src/main/kotlin/com/kyu9/accountbook/SpringDocConfigurations.kt")
    )
}

configure<SourceSetContainer>{
    named("main"){
        java.srcDir("${projectDir}/generated/src/main/kotlin")
    }
}

tasks.named<JavaCompile>("compileJava") {
    enabled = false
}

tasks.named("build").configure {
    dependsOn("generateGitProperties")
}

tasks.named("processResources").configure {
    dependsOn("generateGitProperties")
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.named("generateFromYaml").configure {
    dependsOn("removeGeneratedFromYaml")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.create<Test>("fullTest"){
    useJUnitPlatform()
    group = "0.action"
}

task<Exec>("Merge all branches into MASTER"){
    group = "1.git"

    commandLine("./script/merge_all_branch_into_MASTER.sh")
    //for windows
//    commandLine("cmd", "/c", "merge_all_branch_into_MASTER.sh")
}

task<Exec>("Merge master into ALL_BRANCHES"){
    group = "1.git"

    commandLine("./script/sync_all_branch_by_MASTER.sh")
    //for windows
//    commandLine("cmd", "/c", "merge_master_to_all_branches.sh")
}

task<Exec>("Sync Branches"){
    group = "0.action"

    commandLine("./script/integration_sync.sh")
}

task<Exec>("publish_to_docker"){
    group = "0.action"

    commandLine("./script/docker_compose.sh")
}

tasks.withType<Jar>().configureEach {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}


tasks.test {
    finalizedBy("jacocoTestReport") // 테스트가 완료된 후에 Jacoco 보고서를 생성합니다.
}

tasks.withType<JacocoReport>() {
    dependsOn("test") // 테스트를 실행한 후에 보고서를 생성합니다.
    reports {
        xml.required.set(true)
        csv.required.set(true)
        html.required.set(true)

    }
}



tasks.named("generateGitProperties"){
    gitProperties.gitPropertiesResourceDir.set(file("${projectDir}/src/main/resources"))
    gitProperties.gitPropertiesName = "application-git.properties"
}


//tasks.named("test").configure{ group = "0.action" }
tasks.named("build").configure{ group = "0.action" }
tasks.named("clean").configure{ group = "0.action" }
tasks.named("check").configure{ enabled = false }
tasks.named("bootRun").configure{ group = "0.action" }
tasks.named("compileKotlin").configure{
    group = "0.action"
    dependsOn(tasks.named("generateFromYaml"))
}