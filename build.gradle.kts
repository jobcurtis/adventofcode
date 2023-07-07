import org.jetbrains.kotlin.gradle.utils.notCompatibleWithConfigurationCacheCompat

plugins {
    application
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "com.emlett"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.emlett.aoc.AOCKt")
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.property)
    testImplementation(libs.kotest.framework.datatest)
}

kotlin {
    jvmToolchain(20)

    compilerOptions {
        progressiveMode = true
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.refreshVersions {
    notCompatibleWithConfigurationCacheCompat("")
}