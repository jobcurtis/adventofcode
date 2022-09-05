@file:Suppress("SuspiciousCollectionReassignment")

import kotlinx.benchmark.gradle.*
import org.gradle.kotlin.dsl.benchmark
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    application
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("org.jetbrains.kotlinx.benchmark")
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
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:_")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-opt-in=kotlin.ExperimentalStdlibApi"
            freeCompilerArgs += "-opt-in=kotlin.time.ExperimentalTime"
            freeCompilerArgs += "-opt-in=kotlin.ExperimentalUnsignedTypes"
        }
    }

    wrapper {
        gradleVersion = "7.3.2"
    }
}

allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}

benchmark {
    targets {
        register("main") {
            this as JvmBenchmarkTarget
            jmhVersion = "1.21"
        }
    }

    configurations {
        named("main") {
            warmups = 2
            iterations = 5
            iterationTime = 1
            mode = "AverageTime"
            outputTimeUnit = "ms"
        }
    }
}
