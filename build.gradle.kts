@file:Suppress("SuspiciousCollectionReassignment")

import org.jetbrains.kotlin.gradle.tasks.*

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

    testImplementation(Kotlin.test.junit5)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-opt-in=kotlin.ExperimentalStdlibApi"
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    wrapper {
        gradleVersion = "7.3"
    }
}
