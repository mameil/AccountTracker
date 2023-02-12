import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("org.springframework.boot") version "2.5.7"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.18"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
    kotlin("kapt") version "1.7.21"

    //swagger plugin
    id("org.openapi.generator") version "5.1.1"
}

group = "com.kyu9"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

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
}

dependencies {
    //generated from https://start.spring.io/
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-web-services")
    implementation("org.springframework.boot:spring-boot-starter-log4j2")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("javax.annotation:javax.annotation-api:1.3.2")

    //view
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.0.2")



    //db
    runtimeOnly("com.oracle.database.jdbc:ojdbc8")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("mysql:mysql-connector-java")


    //kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")


    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")

    //Swagger - plugin
    implementation("org.openapitools:openapi-generator-gradle-plugin:6.0.0")

    //Swagger - ui
    implementation("org.springdoc:springdoc-openapi-ui:1.6.8")
    implementation("org.springdoc:springdoc-openapi-common:1.6.8")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.8")
    implementation("jakarta.annotation:jakarta.annotation-api:2.1.0")


    //Openapi Generator
    implementation("io.swagger:swagger-annotations:1.6.2")
    implementation(group="javax.validation", name="validation-api", version="2.0.1.Final")
    implementation(group="org.openapitools", name="jackson-databind-nullable", version="0.2.1")
    //config > https://openapi-generator.tech/docs/generators/spring/


    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2:2.0.202")
    //kotest
    testImplementation("io.kotest:kotest-runner-junit5-jvm:4.4.3")
    testImplementation("io.kotest:kotest-assertions-core-jvm:4.4.3")
    implementation("io.kotest:kotest-extensions-spring:4.4.3")
    //mockk
    testImplementation("io.mockk:mockk:1.9.3")



    //mockk
    testImplementation("io.mockk:mockk:1.12.4")


}

springBoot{
    //kotlin 파일 같은 경우에는 컴파일될 때 파일명에 자동으로 뒤에 Kt가 붙음
    mainClass.set("com.kyu9.accountbook.AccountBookApplicationKt")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }

}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generateFromYaml"){
    inputSpec.set("${projectDir}/src/main/resources/spec/AccountBook.yaml")
    outputDir.set("${projectDir}/generated")
    configFile.set("${projectDir}/src/main/resources/spec/config.json")
    generatorName.set("kotlin-spring")
    group = "0.action"

    //이거 config.json에 넣어도 안되던데 >> 코틀린 스프링부트에서는 적용이 안됨
    //생성된 ApiUtils에서 javax을 못읽어서 보니까 boot3.0.0부터는 javax가 아니라 jakarata로 바뀌었음 그래서 이걸로 바꿀러고했는데 아무튼 코틀린에선 사용 못함
//    configOptions.put("useSpringBoot3", "true")
}

task<Delete>("removeGeneratedFromYaml"){
    group = "0.action"
    delete(
        fileTree("${projectDir}/generated/src/main/kotlin/com/kyu9/accountbook/swagger/model"),
        fileTree("${projectDir}/generated/src/main/kotlin/com/kyu9/accountbook/swagger/api")
    )
}

configure<SourceSetContainer>{
    named("main"){
        java.srcDir("${projectDir}/generated/src/main/kotlin")
    }
}

tasks.named("generateFromYaml").configure {
    dependsOn("removeGeneratedFromYaml")
}

tasks.withType<Test> {
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

//tasks.named("test").configure{ group = "0.action" }
tasks.named("build").configure{ group = "0.action" }
tasks.named("clean").configure{ group = "0.action" }
tasks.named("check").configure{ enabled = false }
tasks.named("bootRun").configure{ group = "0.action" }
tasks.named("compileKotlin").configure{
    group = "0.action"
    dependsOn(tasks.named("generateFromYaml"))
}