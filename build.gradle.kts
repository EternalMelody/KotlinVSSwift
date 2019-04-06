import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
    kotlin("kapt") version "1.3.21"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

group = "MARIOANDHIKA"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile("org.eclipse.mylyn.github:org.eclipse.egit.github.core:2.1.5")
    compile("org.eclipse.jgit:org.eclipse.jgit:5.2.1.201812262042-r")
    compile("org.slf4j:slf4j-log4j12:1.7.2")
    compile("org.slf4j:slf4j-api:1.7.2")
    implementation("com.github.kittinunf.fuel:fuel:2.0.1")
    implementation("com.github.kittinunf.fuel:fuel-moshi:2.0.1")
    implementation("com.squareup.moshi:moshi-kotlin:1.8.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.8.0")
    compile("org.nield:kotlin-statistics:1.2.1")
    compile("de.mpicbg.scicomp:krangl:0.11")
    compile("com.github.holgerbrandl:kravis:0.5")
    compile("no.tornado:tornadofx:1.7.18")
    compile("org.apache.commons:commons-math3:3.6.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}