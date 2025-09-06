plugins {
    id("java")
    id("org.springframework.boot") version "3.5.5"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.learn.newsfeed"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    implementation(libs.solr.solrj)
    implementation(project(":libs:event-log-lib"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.eclipse.jetty:jetty-client:11.0.15")
    implementation("org.eclipse.jetty:jetty-http:11.0.15")
    implementation("org.eclipse.jetty:jetty-io:11.0.15")
    implementation("org.eclipse.jetty:jetty-util:11.0.15")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation(libs.mockito.core)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}