repositories {
    mavenLocal()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
}

// val docGradingOnly by configurations.creating {
//     // canBeResolved=false
// }

dependencies {
    compileOnly("org.sourcegrade:jagr-grader-api:0.3")
    implementation(project(":solution"))
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.mockito:mockito-core:4.1.0")
    implementation("org.sourcegrade:docwatcher-api:0.1-SNAPSHOT")
    implementation("fr.inria.gforge.spoon:spoon-core:10.0.0")
    // For Java-Doc Grading (used for buildLibs task if exists locally)
    compileOnly("org.sourcegrade:docwatcher-core:0.1.0-SNAPSHOT", {})
}

tasks {
    create<Jar>("buildLibs") {
        group = "build"
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        var jars =
                sourceSets.main.get().runtimeClasspath.mapNotNull {
                    if (it.path.toLowerCase().contains("h05")) {
                        null
                    } else if (it.isDirectory) {
                        it
                    } else {
                        zipTree(it)
                    }
                }

        try {
            configurations["compileOnly"].setCanBeResolved(true)
            jars +=
                    configurations["compileOnly"].mapNotNull {
                        if (it.isDirectory) {
                            it
                        } else {
                            zipTree(it)
                        }
                    }
        } catch (e: Exception) {
            // Doc Grading Dependencies not found, skipping
        }

        from(
                // Regular Dependencies
                jars
        )
        archiveFileName.set("h05-libs.jar")
    }
}
