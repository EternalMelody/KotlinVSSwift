import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
}

group = "MARIOANDHIKA"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile("org.eclipse.mylyn.github:org.eclipse.egit.github.core:2.1.5")
    compile("org.eclipse.jgit:org.eclipse.jgit:5.2.1.201812262042-r")
    compile("org.slf4j:slf4j-log4j12:1.7.2")
    compile("org.slf4j:slf4j-api:1.7.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}