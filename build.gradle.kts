@file:Suppress("SuspiciousCollectionReassignment")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
}

group = "com.emlett"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.emlett.aoc.AOCKt")
}

dependencies {
    implementation(Kotlin.stdlib)
    implementation(kotlin("reflect"))
    implementation(KotlinX.serialization.json)

    implementation(libs.kotlinx.coroutines)

    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.property)
    testImplementation(libs.kotest.datatest)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs += "-opt-in=kotlin.ExperimentalStdlibApi"
            freeCompilerArgs += "-opt-in=kotlin.time.ExperimentalTime"
            freeCompilerArgs += "-opt-in=kotlin.ExperimentalUnsignedTypes"
        }
    }

    test {
        useJUnitPlatform()
    }

    wrapper {
        gradleVersion = "7.5.1"
    }
}