import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
  java
  application
  id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.learn.newsfeed"


repositories {
  mavenCentral()
}

val vertxVersion = "5.0.1"
val junitJupiterVersion = "5.9.1"

val mainVerticleName = "com.learn.newsfeed.SqsConsumerVerticle"
val launcherClassName = "io.vertx.launcher.application.VertxApplication"

application {
  mainClass.set(launcherClassName)
}

dependencies {
  // https://mvnrepository.com/artifact/net.sf.saxon/Saxon-HE
  implementation(libs.saxon.parser)
  implementation(libs.snakeyaml)
  implementation(libs.jackson.databind)
  implementation(project(":libs:content-fetcher"))
  implementation(platform(libs.amazon.software.bom))
  implementation("software.amazon.awssdk:sqs")
  implementation("software.amazon.awssdk:s3")
  implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
  implementation("io.vertx:vertx-launcher-application")
  implementation("io.vertx:vertx-web")
//  implementation("io.vertx:vertx-config")
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
