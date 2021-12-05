repositories {
    mavenLocal()
  maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
  implementation("org.sourcegrade:jagr-grader-api:0.3-SNAPSHOT")
  implementation(project(":solution"))
  implementation("net.bytebuddy:byte-buddy:1.12.2")
  implementation("com.google.guava:guava:31.0.1-jre")
}
