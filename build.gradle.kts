import org.gradle.kotlin.dsl.accessors.projectSchemaResourceDiscontinuedWarning
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val springBootVersion = extra("2.1.2.RELEASE")

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin")
    }

    val kotlinVersion by extra("1.3.20")

    repositories {
        mavenCentral()
        jcenter()
    }

}


val arrow_version = "0.8.2"




repositories {
    jcenter()
}

val project = rootProject


plugins {
    val kotlinVersion  = "1.3.20"
    java
    kotlin("jvm") version kotlinVersion
    id("org.springframework.boot") version "2.1.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

dependencies {

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-web:2.1.2.RELEASE")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.google.guava:guava:27.0-jre")

}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-Xjvm-default=enable", "-Xjsr305=strict")
}
