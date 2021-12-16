import org.sourcegrade.submitter.submit

plugins {
  id("org.sourcegrade.submitter") version "0.4.0"
}

submit {
  assignmentId = "h05"
  studentId = "ab12cdef"
  firstName = "sol_first"
  lastName = "sol_last"
}

dependencies {
  testImplementation("org.sourcegrade:jagr-grader-api:0.3-SNAPSHOT")
  testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
  testImplementation("org.mockito:mockito-core:4.1.0")
  testImplementation("com.google.guava:guava:31.0.1-jre")
  testImplementation("org.sourcegrade:docwatcher-api:0.1.0-SNAPSHOT")
    testImplementation("org.sourcegrade:docwatcher-core:0.1.0-SNAPSHOT")
}
