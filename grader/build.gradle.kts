repositories {
    mavenLocal()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    compileOnly("org.sourcegrade:jagr-grader-api:0.3-SNAPSHOT")
    implementation(project(":solution"))
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.mockito:mockito-core:4.1.0")
}

tasks {
    create<Jar>("buildLibs") {
        group = "build"
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        //println(sourceSets.main.get().allSource.srcDirs.contains("grader"))
        from(sourceSets.main.get().runtimeClasspath.mapNotNull {
            if (it.path.toLowerCase().contains("h05")) {
                null
            } else if (it.isDirectory) {
                it
            } else {
                zipTree(it)
            }
        })
        archiveFileName.set("h05-libs.jar")
    }
}
