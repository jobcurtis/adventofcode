import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    application
    alias(libs.plugins.versions)
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
    jvmToolchain(21)

    compilerOptions {
        progressiveMode = true
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<DependencyUpdatesTask> {
    fun isStable(version: String) = listOf("RELEASE", "FINAL", "GA").any { it in version.uppercase() }
    fun isSemver(version: String) = Regex("""^[0-9.]+$""").matches(version)
    fun isStatic(version: String) = isStable(version) || isSemver(version)

    rejectVersionIf {
        !isStatic(candidate.version) && isStatic(currentVersion)
    }
}