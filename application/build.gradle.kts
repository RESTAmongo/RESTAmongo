plugins {
    application
    id("org.springframework.boot") version "2.7.5"
}

apply(plugin = "io.spring.dependency-management")

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
}

application {
    mainClass.set("com.github.restamongo.Application")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
