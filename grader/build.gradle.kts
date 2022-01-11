dependencies {
    compileOnly("org.sourcegrade:jagr-grader-api:0.3")
    implementation(project(":solution"))
    implementation("org.mockito:mockito-core:4.2.0")
    implementation("org.sourcegrade:docwatcher-api:0.1-SNAPSHOT")
    implementation("fr.inria.gforge.spoon:spoon-core:10.0.0")
}

tasks {
    create<Jar>("buildLibs") {
        group = "build"
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        val runtimeDeps = sourceSets.main.get().runtimeClasspath.mapNotNull {
            if (it.path.toLowerCase().contains("h05")) {
                null
            } else if (it.isDirectory) {
                it
            } else {
                zipTree(it)
            }
        }
        from(runtimeDeps)
        archiveFileName.set("h05-libs.jar")
    }
}
