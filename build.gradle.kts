plugins {
  java
}
allprojects {
  apply(plugin = "java")
  repositories {
    mavenLocal()
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
