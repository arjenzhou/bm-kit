dependencies {
    implementation(project(":base"))

    implementation(platform("com.squareup.okhttp3:okhttp-bom:${project.property("okhttp.version")}"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")

    testImplementation("com.squareup.okhttp3:mockwebserver")
}