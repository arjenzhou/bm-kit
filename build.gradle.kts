plugins {
    id("java-library")
}

allprojects {
    group = { project.property("project.group.id") }
    version = { project.property("project.version") }
    apply(plugin = "java-library")
    repositories {
        mavenCentral()
    }
}

subprojects {
    dependencies {
        testImplementation(platform("org.junit:junit-bom:${project.property("junit-bom.version")}"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }
    tasks.test {
        useJUnitPlatform()
    }
}