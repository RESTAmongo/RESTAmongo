plugins {
    id("application")
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("net.saliman.properties") version "1.5.2"
    id("org.sonarqube") version "3.5.0.2730"
    id("jacoco")
}

group = "com.github.restamongo"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
    }
}

tasks.sonar {
    dependsOn(tasks.test)
}

sonar {
    properties{
        property("sonar.host.url", project.property("sonar.host.url").toString())
        property("sonar.projectKey", project.property("sonar.projectKey").toString())
        property("sonar.login", project.property("sonar.login").toString())
    }
}