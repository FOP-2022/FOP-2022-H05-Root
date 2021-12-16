package h05;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.sourcegrade.docwatcher.grading.DocumentationCriterion;
import org.sourcegrade.docwatcher.grading.DocumentationGrader;
import org.sourcegrade.jagr.api.rubric.*;
import org.sourcegrade.jagr.api.testing.ClassTransformer;
import org.sourcegrade.jagr.api.testing.RubricConfiguration;

import h05.ReflectionUtils.MethodTester;

import static h05.H05_Class_Testers.*;

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
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.and(
                    JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod(
                        "t01")),
                    JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod(
                        "t02")),
                    JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod(
                        "t03"))))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_1 = Criterion.builder()
        .shortDescription("H1.1 Interface Walking")
        .addChildCriteria(H1_1_T1)
        .build();

    ///////////////////////////////////////////////// H1.2

    public static final Criterion H1_2_T1 = Criterion.builder()
        .shortDescription("Methoden canLiveInSaltWater() und canLiveInFreshWater() sind korrekt deklariert.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.and(
                JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod("t02")),
                JUnitTestRef.ofMethod(() -> TutorTests_H1_2.class.getMethod("t03"))))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H1_2_T2 = Criterion.builder()
        .shortDescription("Methode letMeSwim() ist korrekt deklariert.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H1_2.class.getMethod("t04")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();

    public static final Criterion H1_2_T3 = Criterion.builder()
        .shortDescription("Interface Swimming ist korrekt deklariert.")
        .maxPoints(0)
        .minPoints(-1)
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H1_2.class.getMethod("t01")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();

    public static final Criterion H1_2 = Criterion.builder()
        .shortDescription("H1.2 Interface Swimming")
        .minPoints(0)
        .maxPoints(2)
        .addChildCriteria(
            H1_2_T1,
            H1_2_T2,
            H1_2_T3)
        .build();

    ///////////////////////////////////////////////// H1.2

    public static final Criterion H1_3_T1 = Criterion.builder()
        .shortDescription("Enum EnvironmentType ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H1_3.class.getMethod("t02")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();

    public static final Criterion H1_3_T2 = Criterion.builder()
        .shortDescription("Das Interface Amphibean ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.and(
                JUnitTestRef.or(
                    JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod("t01")),
                    JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod("t01_alt1"))),
                JUnitTestRef.ofMethod(() -> TutorTests_H1_3.class.getMethod("t03"))))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();

    public static final Criterion H1_3 = Criterion.builder()
        .shortDescription("H1.3 Interface Amphibean")
        .addChildCriteria(
            H1_3_T1,
            H1_3_T2)
        .build();

    ///////////////////////////////////////////////// H2
    public static final Criterion H2_T1 = Criterion.builder()
        .shortDescription("Enum AnimalType ist vollständig korrekt.")
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
    public static final Criterion H3_1_T1 = Criterion.builder()
        .shortDescription("Attribut distanceSoFar wurde korrekt deklariert und hat eine Getter-Methode.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_1.class.getMethod("t02")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_1_T2 = Criterion.builder()
        .shortDescription("Methode getNumberOfLegs() ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_1.class.getMethod("t03")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_1_T3 = Criterion.builder()
        .shortDescription("Methode getAverageSpeed() ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_1.class.getMethod("t04")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_1_T4 = Criterion.builder()
        .shortDescription("Methode letMeMove() ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_1.class.getMethod("t05")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_1_T5 = Criterion.builder()
        .shortDescription("Der Konstruktor ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_1.class.getMethod("t06")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_1_T6 = Criterion.builder()
        .shortDescription("Klasse Ostrick ist korrekt deklariert.")
        .maxPoints(0)
        .minPoints(-1)
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_1.class.getMethod("t01")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();

    public static final Criterion H3_1 = Criterion.builder()
        .shortDescription("H3.1 Walking durch Ostrich implementieren")
        .minPoints(0)
        // .maxPoints(5)
        .addChildCriteria(
            H3_1_T1,
            H3_1_T2,
            H3_1_T3,
            H3_1_T4,
            H3_1_T5,
            H3_1_T6)
        .build();

    ///////////////////////////////////////////////// H3.2
    public static final Criterion H3_2_T1 = Criterion.builder()
        .shortDescription(
            "Attribute specificSpecies, x, y und degreeOfHunger wurden korrekt deklariert und haben eine Getter-Methode.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_2.class.getMethod("t02")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_2_T2 = Criterion.builder()
        .shortDescription("Methode canLiveInSaltWater() ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_2.class.getMethod("t03")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_2_T3 = Criterion.builder()
        .shortDescription("Methode canLiveInFreshWater() ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_2.class.getMethod("t04")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_2_T4 = Criterion.builder()
        .shortDescription("Methode letMeSwim() ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_2.class.getMethod("t05")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_2_T5 = Criterion.builder()
        .shortDescription("Methode letMeMove() ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_2.class.getMethod("t06")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_2_T6 = Criterion.builder()
        .shortDescription("Methode setSpecificSpecies() ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_2.class.getMethod("t07")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_2_T7 = Criterion.builder()
        .shortDescription("Der Konstruktor ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_2.class.getMethod("t08")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_2_T8 = Criterion.builder()
        .shortDescription("Klasse Shark ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.and(
                JUnitTestRef.ofMethod(() -> TutorTests_H3_2.class.getMethod("t01")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_2.class.getMethod("t02")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_2.class.getMethod("t03")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_2.class.getMethod("t04")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_2.class.getMethod("t05")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_2.class.getMethod("t06")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_2.class.getMethod("t07")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_2.class.getMethod("t08"))))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();

    public static final Criterion H3_2 = Criterion.builder()
        .shortDescription("H3.2 Swimming durch Shark implementieren")
        .addChildCriteria(
            H3_2_T1,
            H3_2_T2,
            H3_2_T3,
            H3_2_T4,
            H3_2_T5,
            H3_2_T6,
            H3_2_T7,
            H3_2_T8)
        .build();
    ///////////////////////////////////////////////// H3.3

    public static final Criterion H3_3_T1 = Criterion.builder()
        .shortDescription("Klasse SaltWaterCrocodile ist korrekt deklariert.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef
                .ofMethod(() -> TutorTests_H3_3.class.getMethod("t01")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();

    public static final Criterion H3_3_T2 = Criterion.builder()
        .shortDescription(
            "Attribut averageSpeed wird korrekt deklariert und die Methoden getAverageSpeed() und setAverageSpeed() sind vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H3_3.class.getMethod("t02")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_3_T3 = Criterion.builder()
        .shortDescription("Die geerbten Methoden sowie der Konstruktor sind korrekt implementiert.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.and(
                JUnitTestRef.ofMethod(() -> TutorTests_H3_3.class.getMethod("t03")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_3.class.getMethod("t04")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_3.class.getMethod("t05")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_3.class.getMethod("t06")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_3.class.getMethod("t07"))))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();

    public static final Criterion H3_3 = Criterion.builder()
        .shortDescription("H3.3 Walking und Swimming implementieren")
        .addChildCriteria(
            H3_3_T1,
            H3_3_T2,
            H3_3_T3)
        .build();

    ///////////////////////////////////////////////// H3.4

    public static final Criterion H3_4_T1 = Criterion.builder()
        .shortDescription(
            "Der Konstruktor ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H3_4.class.getMethod("t06")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_4_T2 = Criterion.builder()
        .shortDescription(
            "Methoden equals() und clone() sind vollständig korrekt. // TODO: Equals Test")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.and(
                JUnitTestRef.ofMethod(() -> TutorTests_H3_4.class.getMethod("t02"))
            // JUnitTestRef.ofMethod(() -> TutorTests_H3_4.class.getMethod("t03")),
            ))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_4_T3 = Criterion.builder()
        .shortDescription(
            "Attribut averageSpeed wird korrekt deklariert und die Methoden getAverageSpeed() und setAverageSpeed() sind vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.and(
                JUnitTestRef.ofMethod(() -> TutorTests_H3_4.class.getMethod("t03")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_4.class.getMethod("t04")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_4.class.getMethod("t05"))
            // JUnitTestRef.ofMethod(() -> TutorTests_H3_4.class.getMethod("t03")),
            ))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H3_4_T4 = Criterion.builder()
        .shortDescription(
            "Klasse SaltWaterCrocodileAsAmphibean ist korrekt deklariert.")
        .maxPoints(0)
        .minPoints(-1)
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.or(
                JUnitTestRef.ofMethod(() -> TutorTests_H3_4.class.getMethod("t01")),
                JUnitTestRef.ofMethod(() -> TutorTests_H3_4.class.getMethod("t01_alt"))))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();

    public static final Criterion H3_4 = Criterion.builder()
        .shortDescription("H3.4 Amphibean implementieren")
        .minPoints(0)
        .maxPoints(3)
        .addChildCriteria(
            H3_4_T1,
            H3_4_T2,
            H3_4_T3)
        .build();
    ///////////////////////////////////////////////// H4

    ///////////////////////////////////////////////// H4.1
    public static final Criterion H4_1_T1 = Criterion.builder()
        .shortDescription(
            "Interface Zoo ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.and(
                JUnitTestRef.ofMethod(() -> TutorTests_H4.class.getMethod("t01")),
                JUnitTestRef.ofMethod(() -> TutorTests_H4.class.getMethod("t02")),
                JUnitTestRef.ofMethod(() -> TutorTests_H4.class.getMethod("t03"))))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();

    public static final Criterion H4_1 = Criterion.builder()
        .shortDescription("H4.1 Interface Zoo")
        .addChildCriteria(
            H4_1_T1)
        .build();

    ///////////////////////////////////////////////// H4.2

    public static final Criterion H4_2_T1 = Criterion.builder()
        .shortDescription(
            "SurvivalOfTheFittestZoo wurde korrekt deklariert und Methode canLiveTogether() ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.and(
                JUnitTestRef.ofMethod(() -> TutorTests_H4.class.getMethod("t04")),
                JUnitTestRef.ofMethod(() -> TutorTests_H4.class.getMethod("t05"))))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H4_2_T2 = Criterion.builder()
        .shortDescription(
            "Methode isAllowed() ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(
                JUnitTestRef.ofMethod(() -> TutorTests_H4.class.getMethod("t06")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H4_2 = Criterion.builder()
        .shortDescription("H4.2 SurvivalOfTheFittestZoo")
        .addChildCriteria(
            H4_2_T1,
            H4_2_T2)
        .build();

    ///////////////////////////////////////////////// H4.3

    public static final Criterion H4_3_T1 = Criterion.builder()
        .shortDescription(
            "Klasse FamilyFriendlyZoo wurde korrekt deklariert und Methode isAllowed() ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(JUnitTestRef.and(
                JUnitTestRef.ofMethod(() -> TutorTests_H4.class.getMethod("t07")),
                JUnitTestRef.ofMethod(() -> TutorTests_H4.class.getMethod("t09"))))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H4_3_T2 = Criterion.builder()
        .shortDescription(
            "Methode canLiveTogether() ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(
                JUnitTestRef.ofMethod(() -> TutorTests_H4.class.getMethod("t08")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H4_3_T3 = Criterion.builder()
        .shortDescription(
            "Methode letterOfTheDay() ist vollständig korrekt.")
        .grader(Grader.testAwareBuilder()
            .requirePass(
                JUnitTestRef.ofMethod(() -> TutorTests_H4.class.getMethod("t10")))
            .pointsPassedMax()
            .pointsFailedMin()
            .build())
        .build();
    public static final Criterion H4_3 = Criterion.builder()
        .shortDescription("H4.3 FamilyFriendlyZoo")
        .addChildCriteria(
            H4_3_T1,
            H4_3_T2,
            H4_3_T3)
        .build();

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
    public static final Criterion H3 = Criterion.builder()
        .shortDescription("H3 Abgeleitete / Implementierende Klassen")
        .addChildCriteria(
            H3_1,
            H3_2,
            H3_3,
            H3_4)
        .build();
    public static final Criterion H4 = Criterion.builder()
        .shortDescription("H4 Zoo")
        .addChildCriteria(
            H4_1,
            H4_2,
            H4_3)
        .build();
    public static final Criterion JAVADOC;
    static {
        try {
            Class.forName("org.sourcegrade.docwatcher.DocWatcherModule");
        } catch (ClassNotFoundException e) {
            // ignore
        }
        JAVADOC = DocumentationCriterion.forGrader(
            DocumentationGrader.builder()
                .addJavaDoc(new MethodTester(walkingCT, "getNumberOfLegs")::getMethodDocumentation, H1_1)
                .addJavaDoc(new MethodTester(walkingCT, "getAverageSpeed")::getMethodDocumentation, H1_1)
                .addJavaDoc(new MethodTester(swimmingCT, "canLiveInSaltWater")::getMethodDocumentation, H1_2)
                .addJavaDoc(new MethodTester(swimmingCT, "canLiveInFreshWater")::getMethodDocumentation, H1_2)
                .addJavaDoc(new MethodTester(swimmingCT, "letMeSwim")::getMethodDocumentation, H1_2)
                .addJavaDoc(new MethodTester(amphibeanCT, "getPreferredEnvironmentType")::getMethodDocumentation, H1_3)
                .addJavaDoc(new MethodTester(animalCT, "getAnimalType")::getMethodDocumentation, H2)
                .addJavaDoc(new MethodTester(animalCT, "letMeMove")::getMethodDocumentation, H2)
                .addJavaDoc(new MethodTester(ostrichCT, "getDistanceSoFar")::getMethodDocumentation, H3_1)
                .addJavaDoc(sharkCT::getConstructorDocumentation, H3_2)
                .addJavaDoc(new MethodTester(sharkCT, "getSpecificSpecies")::getMethodDocumentation, H3_2)
                .addJavaDoc(new MethodTester(sharkCT, "getX")::getMethodDocumentation, H3_2)
                .addJavaDoc(new MethodTester(sharkCT, "getY")::getMethodDocumentation, H3_2)
                .addJavaDoc(new MethodTester(sharkCT, "getDegreeOfHunger")::getMethodDocumentation, H3_2)
                .addJavaDoc(new MethodTester(sharkCT, "setSpecificSpecies")::getMethodDocumentation, H3_2)
                .addJavaDoc(saltWaterCrocodileCT::getConstructorDocumentation, H3_3)
                .addJavaDoc(new MethodTester(sharkCT, "setAverageSpeed")::getMethodDocumentation, H3_3)
                .addJavaDoc(new MethodTester(sharkCT, "setAverageSpeed")::getMethodDocumentation, H3_3)
                .addJavaDoc(saltWaterCrocodileAsAmphibeanCT::getConstructorDocumentation, H3_4)
                .addJavaDoc(new MethodTester(zooCT, "canLiveTogether")::getMethodDocumentation, H4)
                .addJavaDoc(new MethodTester(zooCT, "isAllowed")::getMethodDocumentation, H4)
                .addJavaDoc(new MethodTester(zooCT, "letterOfTheDay")::getMethodDocumentation, H4)
                .build());
    }

    public static final Rubric RUBRIC = Rubric.builder()
        .title("h05")
        .addChildCriteria(H1, H2, H3, H4, JAVADOC)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }

    @Override
    public void configure(RubricConfiguration configuration) {
        configuration
            .addTransformer(ClassTransformer.replacement(ThreadLocalRandomTester.class, ThreadLocalRandom.class));
    }
}
