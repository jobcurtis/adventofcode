import java.time.LocalDate

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

    testImplementation(libs.bundles.junit)
}

kotlin {
    jvmToolchain(21)

    compilerOptions {
        progressiveMode = true
        freeCompilerArgs.add("-Xcontext-receivers")
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    fun isStable(version: String) = listOf("RELEASE", "FINAL", "GA").any { it in version.uppercase() }
    fun isSemver(version: String) = Regex("""^[0-9.]+$""").matches(version)
    fun isStatic(version: String) = isStable(version) || isSemver(version)

    rejectVersionIf {
        !isStatic(candidate.version) && isStatic(currentVersion)
    }

    gradleReleaseChannel = "current"
}

tasks.withType<JavaExec> {
    val aocSession: String by project

    systemProperty("aoc.session", aocSession)

    @Suppress("UNNECESSARY_SAFE_CALL")
    if (args?.isEmpty() == true) {
        with(LocalDate.now()) {
            args(year, dayOfMonth)
        }
    }
}
