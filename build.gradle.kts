plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.serialization") version "2.0.20"
    id("tech.medivh.plugin.publisher") version "1.0.0"
}

group = "today.movatech"
version = "0.0.3"

repositories {
    mavenCentral()
}

val ktorVersion = "3.0.1"

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.5.12")

    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-client-mock:$ktorVersion")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

medivhPublisher {
    pom {
        url = "https://github.com/indegro/kollama.git"
        developers {
            developer {
                id = "indegro"
                name = "Denys Dushyn"
                email = "denys.dushyn@gmail.com"
            }
        }
        licenses {
            license {
                name = "MIT License"
                url = "https://github.com/indegro/kollama/blob/main/LICENSE"
            }
        }
    }
}