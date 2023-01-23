import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.graalvm.buildtools.native") version "0.9.18"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"

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

    //test
    testImplementation("org.springframework.boot:spring-boot-starter-test")

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

}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }

}

tasks.register<org.openapitools.generator.gradle.plugin.tasks.GenerateTask>("generateFromYaml"){
    inputSpec.set("${projectDir}/spec/AccountBook.yaml")
    outputDir.set("${projectDir}/generated")
    configFile.set("${projectDir}/spec/config.json")
    generatorName.set("kotlin-spring")
    group = "0.action"

}

task<Delete>("removeGeneratedFromYaml"){
    group = "0.action"
    delete(
        fileTree("${projectDir}/generated/src/main/kotlin/com/kyu9/accountbook/swagger/model"),
        fileTree("${projectDir}/generated/src/main/kotlin/com/kyu9/accountbook/swagger/api")
    )
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

tasks.create("Merge all branches to MASTER"){
    group = "1.git"
    doLast {
        println("Merge into MASTER task run")
    }
    Runtime.getRuntime().exec("./script/merge_all_branch_to_MASTER.sh")
}

tasks.create("Merge master to ALL_BRANCHES"){
    group = "1.git"
    doLast {
        println("Merge all branches task run")
    }
    Runtime.getRuntime().exec("./script/sync_all_branch_with_MASTER.sh")
}

//tasks.named("test").configure{ group = "0.action" }
tasks.named("build").configure{ group = "0.action" }
tasks.named("clean").configure{ group = "0.action" }
tasks.named("bootRun").configure{ group = "0.action" }
tasks.named("compileKotlin").configure{
    group = "0.action"
    dependsOn(tasks.named("generateFromYaml"))
}