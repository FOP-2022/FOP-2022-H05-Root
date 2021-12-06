package h05;

import org.sourcegrade.jagr.api.rubric.*;
import org.sourcegrade.jagr.api.testing.TestCycle;

@RubricForSubmission("h05")
public class H05_RubricProvider implements RubricProvider {

    ///////////////////////////////////////////////// H1.1

    // public static final Criterion H1_1_T1 = Criterion.builder()
    // .shortDescription("Existenz Interface Walking")
    // .grader(Grader.testAwareBuilder()
    // .requirePass(JUnitTestRef
    // .ofMethod(() -> TutorTests_H1_1.class.getMethod("t01",
    // TestCycle.class)))
    // .pointsPassedMax()
    // .pointsFailedMin()
    // .build())
    // .build();
    // public static final Criterion H1_1_T2 = Criterion.builder()
    // .shortDescription("Methode getNumberOfLegs() ist korrekt deklariert.")
    // .grader(Grader.testAwareBuilder()
    // .requirePass(JUnitTestRef
    // .ofMethod(() -> TutorTests_H1_1.class.getMethod("t02",
    // TestCycle.class)))
    // .pointsPassedMax()
    // .pointsFailedMin()
    // .build())
    // .build();
    // public static final Criterion H1_1_T3 = Criterion.builder()
    // .shortDescription("Methode getAverageSpeed() ist korrekt deklariert.")
    // .grader(Grader.testAwareBuilder()
    // .requirePass(JUnitTestRef
    // .ofMethod(() -> TutorTests_H1_1.class.getMethod("t03",
    // TestCycle.class)))
    // .pointsPassedMax()
    // .pointsFailedMin()
    // .build())
    // .build();

    public static final Criterion H1_1_T1 = Criterion.builder()
            .shortDescription("Interface Walking ist vollständig korrekt.")
            .grader(Grader.descendingPriority(
                    Grader.testAwareBuilder()
                            .requirePass(JUnitTestRef
                                    .ofMethod(() -> TutorTests_H1_1.class.getMethod("t01")))
                            .pointsPassedMax()
                            .pointsFailedMin()
                            .build(),
                    Grader.testAwareBuilder()
                            .requirePass(JUnitTestRef
                                    .ofMethod(() -> TutorTests_H1_1.class.getMethod("t02")))
                            .pointsPassedMax()
                            .pointsFailedMin()
                            .build(),
                    Grader.testAwareBuilder()
                            .requirePass(JUnitTestRef
                                    .ofMethod(() -> TutorTests_H1_1.class.getMethod("t03")))
                            .pointsPassedMax()
                            .pointsFailedMin()
                            .build()))
            .build();

    public static final Criterion H1_1 = Criterion.builder()
            .shortDescription("H1.1 Interface Walking")
            .addChildCriteria(H1_1_T1)
            .build();

    ///////////////////////////////////////////////// H1.2

    public static final Criterion H1_2_T1 = Criterion.builder()
            .shortDescription("Existenz Interface Swimming")
            .grader(Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef
                            .ofMethod(() -> TutorTests_H1_2.class.getMethod("t01")))
                    .pointsPassedMax()
                    .pointsFailedMin()
                    .build())
            .build();
    public static final Criterion H1_2_T2 = Criterion.builder()
            .shortDescription("Methode canLiveInSaltWater() ist korrekt deklariert.")
            .grader(Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef
                            .ofMethod(() -> TutorTests_H1_2.class.getMethod("t02")))
                    .pointsPassedMax()
                    .pointsFailedMin()
                    .build())
            .build();
    public static final Criterion H1_2_T3 = Criterion.builder()
            .shortDescription("Methode canLiveInFreshWater() ist korrekt deklariert.")
            .grader(Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef
                            .ofMethod(() -> TutorTests_H1_2.class.getMethod("t03")))
                    .pointsPassedMax()
                    .pointsFailedMin()
                    .build())
            .build();
    public static final Criterion H1_2_T4 = Criterion.builder()
            .shortDescription("Methode letMeSwim() ist korrekt deklariert.")
            .grader(Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef
                            .ofMethod(() -> TutorTests_H1_2.class.getMethod("t04")))
                    .pointsPassedMax()
                    .pointsFailedMin()
                    .build())
            .build();

    public static final Criterion H1_2 = Criterion.builder()
            .shortDescription("H1.2 Interface Swimming")
            .addChildCriteria(
                    H1_2_T1,
                    H1_2_T2,
                    H1_2_T3,
                    H1_2_T4)
            .build();

    ///////////////////////////////////////////////// H1.2

    public static final Criterion H1_3_T1 = Criterion.builder()
            .shortDescription("Existenz Interface Amphibean")
            .grader(Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef
                            .ofMethod(() -> TutorTests_H1_3.class.getMethod("t01")))
                    .pointsPassedMax()
                    .pointsFailedMin()
                    .build())
            .build();
    public static final Criterion H1_3_T2 = Criterion.builder()
            .shortDescription("Enum EnvironmentType ist korrekt deklariert.")
            .grader(Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef
                            .ofMethod(() -> TutorTests_H1_3.class.getMethod("t02")))
                    .pointsPassedMax()
                    .pointsFailedMin()
                    .build())
            .build();
    public static final Criterion H1_3_T3 = Criterion.builder()
            .shortDescription("Methode getPreferredEnvironment() ist korrekt deklariert.")
            .grader(Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef
                            .ofMethod(() -> TutorTests_H1_3.class.getMethod("t03")))
                    .pointsPassedMax()
                    .pointsFailedMin()
                    .build())
            .build();

    public static final Criterion H1_3 = Criterion.builder()
            .shortDescription("H1.3 Interface Amphibean")
            .addChildCriteria(
                    H1_3_T1,
                    H1_3_T2,
                    H1_3_T3)
            .build();

    ///////////////////////////////////////////////// H2
    public static final Criterion H2_T1 = Criterion.builder()
            .shortDescription("Enum AnimalType wurde korrekt deklariert.")
            .grader(Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef
                            .ofMethod(() -> TutorTests_H2.class.getMethod("t01")))
                    .pointsPassedMax()
                    .pointsFailedMin()
                    .build())
            .build();
    public static final Criterion H2_T2 = Criterion.builder()
            .shortDescription("Klasse Animal wurde korrekt deklariert.")
            .grader(Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef
                            .ofMethod(() -> TutorTests_H2.class.getMethod("t02")))
                    .pointsPassedMax()
                    .pointsFailedMin()
                    .build())
            .build();
    public static final Criterion H2_T3 = Criterion.builder()
            .shortDescription("Attribut animalType wurde korrekt deklariert und hat eine Getter-Methode.")
            .grader(Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef
                            .ofMethod(() -> TutorTests_H2.class.getMethod("t03")))
                    .pointsPassedMax()
                    .pointsFailedMin()
                    .build())
            .build();
    public static final Criterion H2_T4 = Criterion.builder()
            .shortDescription("Methode toString() wurde korrekt überschrieben.")
            .grader(Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef
                            .ofMethod(() -> TutorTests_H2.class.getMethod("t04")))
                    .pointsPassedMax()
                    .pointsFailedMin()
                    .build())
            .build();

    ///////////////////////////////////////////////// H3

    ///////////////////////////////////////////////// H3.1

    ///////////////////////////////////////////////// H3.2
    public static final Criterion H3_2_T6 = Criterion.builder()
            .shortDescription("Methode letMeMove() wurde korrekt implementiert.")
            .grader(Grader.testAwareBuilder()
                    .requirePass(JUnitTestRef
                            .ofMethod(() -> TutorTests_H3_2.class.getMethod("t06")))
                    .pointsPassedMax()
                    .pointsFailedMin()
                    .build())
            .build();
    ///////////////////////////////////////////////// H3.3

    ///////////////////////////////////////////////// H3.4

    ///////////////////////////////////////////////// H4

    ///////////////////////////////////////////////// Tasks
    public static final Criterion H1 = Criterion.builder()
            .shortDescription("H1 Drei Interfaces")
            .addChildCriteria(
                    H1_1,
                    H1_2,
                    H1_3)
            .build();
    public static final Criterion H2 = Criterion.builder()
            .shortDescription("H2 Klasse Animal")
            .addChildCriteria(
                    H2_T1,
                    H2_T2,
                    H2_T3,
                    H2_T4)
            .build();

    public static final Rubric RUBRIC = Rubric.builder()
            .title("h05")
            .addChildCriteria(H1, H2, H3_2_T6)
            .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }

}
