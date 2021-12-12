plugins {
  java
}
allprojects {
  apply(plugin = "java")
  repositories {
    mavenLocal()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    mavenCentral()
  }
  java {
    withSourcesJar()
  }
  tasks {
    jar {
      archiveFileName.set("${rootProject.name}-${project.name}.jar")
    }
    named<Jar>("sourcesJar") {
      archiveFileName.set("${rootProject.name}-${project.name}-sources.jar")
    }
  }
}
