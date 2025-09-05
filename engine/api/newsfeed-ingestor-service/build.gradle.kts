import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    id("java")
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.learn.newsfeed"

repositories {
    mavenCentral()
}

val vertxVersion = "5.0.1"
val junitJupiterVersion = "5.9.1"

val mainVerticleName = "com.learn.newsfeed.MainVerticle"
val launcherClassName = "io.vertx.launcher.application.VertxApplication"

application {
    mainClass.set(launcherClassName)
}

dependencies {
    implementation(libs.saxon.parser)
    implementation(libs.snakeyaml)
    implementation(libs.jackson.databind)
    implementation(libs.solr.solrj)
    implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
    implementation(project(":libs:event-log-lib"))
    implementation("io.vertx:vertx-launcher-application")
    implementation("io.vertx:vertx-web")
    implementation("io.vertx:vertx-config-yaml")
    testImplementation("io.vertx:vertx-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("fat")
    manifest {
        attributes(mapOf("Main-Verticle" to mainVerticleName))
    }
    mergeServiceFiles()
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events = setOf(PASSED, SKIPPED, FAILED)
    }
}

tasks.withType<JavaExec> {
    args = listOf(mainVerticleName)
}
