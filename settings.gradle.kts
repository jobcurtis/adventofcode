rootProject.name = "adventofcode"

plugins {
    id("de.fayard.refreshVersions") version "0.40.2"
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage") versionCatalogs {
        create("libs") {
            val kotlinVersion = version("kotlin", "1.7.10")

            library("kotlin-test", "org.jetbrains.kotlin", "kotlin-test").versionRef(kotlinVersion)
            library("kotlin-test-junit5", "org.jetbrains.kotlin", "kotlin-test-junit5").versionRef(kotlinVersion)

            val coroutines = version("coroutines", "1.6.4")
            library("kotlinx-coroutines", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef(coroutines)

            val kotestVersion = version("kotest", "5.4.2")
            library("kotest-runner-junit5", "io.kotest", "kotest-runner-junit5").versionRef(kotestVersion)
            library("kotest-assertions-core", "io.kotest", "kotest-assertions-core").versionRef(kotestVersion)
            library("kotest-property", "io.kotest", "kotest-property").versionRef(kotestVersion)
            library("kotest-datatest", "io.kotest", "kotest-framework-datatest").versionRef(kotestVersion)
        }
    }
}
