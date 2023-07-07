rootProject.name = "adventofcode"

plugins {
    id("de.fayard.refreshVersions") version "0.51.0"
}

refreshVersions {
    versionsPropertiesFile = file("build/tmp/versions.properties")
}