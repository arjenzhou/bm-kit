val quartzVersion = project.property("quertz.version")

dependencies {
    implementation("org.quartz-scheduler:quartz:${quartzVersion}")
}

plugins {
    id("org.gradlex.extra-java-module-info")
}

extraJavaModuleInfo {
    // quartz has not supported JPMS yet
    module("quartz-${quartzVersion}.jar", "org.quartz", "$quartzVersion") {
        requires("org.slf4j")
        requires("java.desktop")
        requires("java.rmi")
        exportAllPackages()
    }
    // quartz related dependencies, whose versions are depend on quartz, not globally used.
    module("HikariCP-java7-2.4.13.jar", "hikari", "2.4.13")
    module("c3p0-0.9.5.4.jar", "c3p0", "0.9.5.4")
    module("mchange-commons-java-0.2.15.jar", "mchange.commons.java", "0.2.15")
}