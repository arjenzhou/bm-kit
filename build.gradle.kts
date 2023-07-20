plugins {
    id("java-library")
    id("jacoco")
    id("signing")
    id("maven-publish")
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
    apply(plugin = "signing")
    apply(plugin = "maven-publish")

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

    dependencies {
        implementation("org.slf4j:slf4j-api:${project.property("slf4j.version")}")
        implementation("org.slf4j:slf4j-simple:${project.property("slf4j.version")}")

        testImplementation(platform("org.junit:junit-bom:${project.property("junit-bom.version")}"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
                xml.outputLocation.set(File("$projectDir/build/reports/coverage/jacoco-${project.name}.xml"))
            }
            dependsOn(test)
        }
    }

    java {
        withJavadocJar()
        withSourcesJar()
    }

    publishing {
        publications {
            register<MavenPublication>(project.property("artifact.id") as String) {
                groupId = project.property("group.id") as String
                artifactId = project.property("artifact.id") as String + "-" + project.name
                version = project.property("project.version") as String
                from(components["java"])
                signing {
                    sign(this@register)
                }
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

            maven {
                name = "OSSRH"
                val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                val isSnapshots = project.property("project.version").toString().endsWith("SNAPSHOT")
                url = if (isSnapshots) snapshotsRepoUrl else releasesRepoUrl
                credentials {
                    username = System.getenv("MAVEN_USERNAME")
                    password = System.getenv("MAVEN_PASSWORD")
                }
            }
        }
    }
}