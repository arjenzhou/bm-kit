plugins {
    id("java")
    id("jacoco")
    id("maven-publish")
    id("signing")
}

jacoco {
    toolVersion = project.property("jcoco.version") as String
}

tasks {
    wrapper {
        gradleVersion = project.property("gradle.wrapper.version") as String
        distributionType = Wrapper.DistributionType.ALL
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java-library")
    apply(plugin = "jacoco")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

    dependencies {
        testImplementation(platform("org.junit:junit-bom:${project.property("junit-bom.version")}"))
        testImplementation("org.junit.jupiter:junit-jupiter")
    }

    tasks {
        test {
            useJUnitPlatform()
        }
        jacocoTestReport {
            reports {
                xml.required.set(true)
                csv.required.set(false)
                html.required.set(false)
                xml.outputLocation.set(File("$rootDir/coverage/reports/${project.name}.xml"))
            }
            dependsOn(test)
        }
        javadoc {
            if (JavaVersion.current().isJava9Compatible) {
                (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
            }
        }
    }

    publishing {
        publications {
            register<MavenPublication>("gpr") {
                groupId = project.property("group.id") as String
                artifactId = project.name
                version = project.property("project.version") as String
                from(components["java"])
            }
        }
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/arjenzhou/bm-kit")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}