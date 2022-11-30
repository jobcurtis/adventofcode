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
    implementation(KotlinX.coroutines.core)

    testImplementation(Testing.Kotest.runner.junit5)
    testImplementation(Testing.Kotest.assertions.core)
    testImplementation(Testing.Kotest.property)
    testImplementation("io.kotest:kotest-framework-datatest:_")
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlin.ExperimentalStdlibApi",
                "-opt-in=kotlin.time.ExperimentalTime",
                "-opt-in=kotlin.ExperimentalUnsignedTypes"
            )
        }
    }

    test {
        useJUnitPlatform()
    }

    wrapper {
        gradleVersion = "7.6"
    }
}
