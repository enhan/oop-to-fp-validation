import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin")
    }

    repositories {
        mavenCentral()
        jcenter()
    }

}


val arrow_version = "0.8.2"




repositories {
    jcenter()
    maven { url = uri("https://oss.jfrog.org/artifactory/oss-snapshot-local/") }
}

val project = rootProject


plugins {
    val kotlinVersion  = "1.3.31"
    java
    kotlin("jvm") version kotlinVersion
    id("org.springframework.boot") version "2.1.4.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
}

dependencies {

    val arrow_version = "0.9.1-SNAPSHOT"

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.springframework.boot:spring-boot-starter-web:2.1.4.RELEASE")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.google.guava:guava:27.0-jre")

    // Arrow
    compile("io.arrow-kt:arrow-core-data:$arrow_version")
    compile( "io.arrow-kt:arrow-core-extensions:$arrow_version")
    compile( "io.arrow-kt:arrow-syntax:$arrow_version")
    compile( "io.arrow-kt:arrow-typeclasses:$arrow_version")
    compile( "io.arrow-kt:arrow-extras-data:$arrow_version")
    compile( "io.arrow-kt:arrow-extras-extensions:$arrow_version")

}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-Xjvm-default=enable", "-Xjsr305=strict")
}


