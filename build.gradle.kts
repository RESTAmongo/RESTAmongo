plugins {
    // Application frameworks and dependencies
    id("application")
    id("java")
    id("org.springframework.boot") version "2.7.5"
    // Ease of management
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("net.saliman.properties") version "1.5.2"
    // Code quality and test reports
    id("org.sonarqube") version "3.5.0.2730"
    id("jacoco")
}

// Application info
group = "com.github.restamongo"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Application implementation
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("com.google.guava:guava:31.1-jre")
    // Application testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.javadoc {
    setSource("src/main/java")
}

tasks.test {
    dependsOn(tasks.javadoc)
    useJUnitPlatform()
    // Connect JUnit tests to JaCoCo
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    // Enable xml reports for SonarQube
    reports {
        xml.required.set(true)
    }
}

tasks.sonar {
    dependsOn(tasks.test)
}

sonar {
    properties {
        property("sonar.host.url", project.property("sonar.host.url").toString())
        property("sonar.projectKey", project.property("sonar.projectKey").toString())
        property("sonar.login", project.property("sonar.login").toString())
    }
}