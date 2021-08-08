import org.ajoberstar.grgit.Grgit

plugins {
    kotlin("jvm") version "1.5.10"
    application
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("org.ajoberstar.grgit") version "4.1.0"
}

group = "com.eddsteel"
version = Grgit.open()?.head()?.abbreviatedId ?: "0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation(platform("io.ktor:ktor-bom:1.6.1"))

    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty")
    implementation("ch.qos.logback:logback-classic:1.2.3")
}

application {
    mainClass.set("com.eddsteel.rando.MainKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

tasks {
    shadowJar {
        archiveClassifier.set("")
        archiveVersion.set("")
    }

    shadowDistZip {
        archiveBaseName.set("rando")
        archiveVersion.set("")
        exclude("*.bat")
    }

    shadowDistTar {
        archiveBaseName.set("rando")
        archiveVersion.set("")
        exclude("*.bat")
    }
}
