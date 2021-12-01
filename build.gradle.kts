plugins {
    kotlin("jvm") version "1.6.0" apply false
}

allprojects {
    group = "com.emlett"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    val implementation by configurations
    val testImplementation by configurations

    dependencies {
        implementation(kotlin("stdlib"))
        testImplementation("org.junit.jupiter:junit-jupiter:5.+")
    }
}

tasks {
    wrapper {
        gradleVersion = "7.3"
    }
}
