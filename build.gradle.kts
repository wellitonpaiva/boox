plugins {
    kotlin("jvm") version "2.0.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:5.26.1.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.webjars.npm:htmx.org:1.9.2")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.1")
    testImplementation(kotlin("test"))
}

application {
    mainClass = "boox.ApplicationKt"
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}