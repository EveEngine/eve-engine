plugins {
    id("org.springframework.boot") version "2.3.0.RELEASE"
    kotlin("jvm") version "1.3.72"
}

group = "net.legio"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.httpcomponents:httpcore:4.4.13")
    implementation("org.apache.httpcomponents:httpclient:4.5.12")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.+")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.+")
    implementation("javax.xml.bind:jaxb-api")

    implementation("javax.servlet:javax.servlet-api:4.0.1")

    implementation("org.springframework.boot:spring-boot-starter:2.3.0.RELEASE")
    implementation("org.springframework.shell:spring-shell-starter:2.0.1.RELEASE")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")

    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    bootRun {
        standardInput = System.`in`
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}