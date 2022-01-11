package h05;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static h05.H05_Class_Testers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

@TestForSubmission("h05")
@DisplayName("H4")
public class TutorTests_H4 {
    // Interface ZOO
    @Test
    @DisplayName("2 | Existenz Interface Zoo")
    public void t01() {
        zooCT.verify(1.0d);
    }

    @Test
    @DisplayName("2 | Zoo - canLiveTogether")
    public void t02() {
        zooCT.resolveClass();
        animalCT.resolveClass();
        new MethodTester(
            zooCT, "canLiveTogether", 0.8, Modifier.PUBLIC | Modifier.ABSTRACT, boolean.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("a1", 0.8, animalCT.getTheClass()),
                new ParameterMatcher("a2", 0.8, animalCT.getTheClass()))))
            .verify();
    }

    @Test
    @DisplayName("3 | Zoo - isAllowed")
    public void t03() {
        new MethodTester(zooCT.assureClassResolved(), "isAllowed", 0.8, Modifier.PUBLIC | Modifier.ABSTRACT,
            boolean.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("a", 0.8, animalCT.assureClassResolved().getTheClass()))))
            .verify();
    }

    // Survival of the Fittest Zoo
    @Test
    @DisplayName("4 | Existenz Klasse SurvivalOfTheFittestZoo")
    public void t04() {
        survivalOfTheFittestZooCT.verify(1.0d);
    }

    @Test
    @DisplayName("5 | SurvivalOfTheFittestZoo - canLiveTogether")
    public void t05() {
        var mt = new MethodTester(survivalOfTheFittestZooCT.resolve(), "canLiveTogether", 0.8, -1,
            boolean.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("a1", 0.8, animalCT.assureClassResolved().getTheClass()),
                new ParameterMatcher("a2", 0.8, animalCT.getTheClass()))))
            .verify();
        var animalSubtypes = new String[]{"Animal", "Ostrich", "Shark", "SaltWaterCrocodile"};
        for (final var name1 : animalSubtypes) {
            for (final var name2 : animalSubtypes) {
                var ct1 = new ClassTester<>("h05", name1, 0.8).resolve();
                var ct2 = new ClassTester<>("h05", name2, 0.8).resolve();
                mt.assertReturnValueEquals(true, ct1.getClassInstance(), ct2.getClassInstance());
            }
        }
    }

    @Test
    @DisplayName("6 | SurvivalOfTheFittestZoo - isAllowed")
    public void t06() {
        var mt = new MethodTester(survivalOfTheFittestZooCT.resolve(), "isAllowed", 0.8, -1, boolean.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("a", 0.8, animalCT.assureClassResolved().getTheClass()))))
            .verify();
        var enumClassTester = new ClassTester<>("h05", "AnimalType", 0.8).resolveClass();
        Field animalTypeField = animalCT.resolveAttribute(
            new AttributeMatcher("animalType", 0.8, -1, enumClassTester.getClass()));

        for (int i = 0; i < 100; i++) {
            animalCT.resolveInstance();
            var animalType = animalCT.setFieldRandom(animalTypeField);
            mt.assertReturnValueEquals(enumClassTester.getEnumValue("AVES", 0.8) != animalType,
                animalCT.getClassInstance());
        }
    }

    // Family Friendly Zoo

    @Test
    @DisplayName("7 | Existenz Klasse FamilyFriendlyZoo")
    public void t07() {
        familyFriendlyZooCT.verify(1.0d);
    }

    @Test
    @DisplayName("8 | FamilyFriendlyZoo - canLiveTogether")
    public void t08() {
        walkingCT.resolveClass();
        familyFriendlyZooCT.resolve();
        animalCT.resolveClass();
        animalTypeCT.resolveClass();
        sharkCT.resolveReal();
        var mt = new MethodTester(familyFriendlyZooCT, "canLiveTogether", minSim, -1, boolean.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("a1", minSim, animalCT.getTheClass()),
                new ParameterMatcher("a2", minSim, animalCT.getTheClass()))))
            .verify();
        Field animalTypeField = animalCT.resolveAttribute(
            new AttributeMatcher("animalType", 0.8, -1, animalTypeCT.getClass()));
        var getAverageSpeeedMT = new MethodTester(walkingCT, "getAverageSpeed", 0.8,
            Modifier.PUBLIC | Modifier.ABSTRACT,
            double.class, new ArrayList<>(List.of(new ParameterMatcher("distance", 0.8, double.class))));
        getAverageSpeeedMT.resolveMethod();

        // Dynamically Generated Test Animals
        var randomAve = mock(animalCT.getTheClass(), CALLS_REAL_METHODS);
        ClassTester.setField(randomAve, animalTypeField, animalTypeCT.getEnumValue("AVES", 0.8));
        var randomMammalia = mock(animalCT.getTheClass(), CALLS_REAL_METHODS);
        ClassTester.setField(randomMammalia, animalTypeField, animalTypeCT.getEnumValue("MAMMALIA", 0.8));
        var randomCrocodilidae = mock(animalCT.getTheClass(), CALLS_REAL_METHODS);
        ClassTester.setField(randomCrocodilidae, animalTypeField, animalTypeCT.getEnumValue("CROCODYLIDAE", 0.8));
        var randomCondrichthy = mock(animalCT.getTheClass(), CALLS_REAL_METHODS);
        ClassTester.setField(randomCondrichthy, animalTypeField, animalTypeCT.getEnumValue("CHONDRICHTHYES", 0.8));
        var fastMammalia = mock(animalCT.getTheClass(),
            withSettings().extraInterfaces(walkingCT.getTheClass()).defaultAnswer(
                CALLS_REAL_METHODS));
        ClassTester.setField(fastMammalia, animalTypeField, animalTypeCT.getEnumValue("MAMMALIA", 0.8));
        assertDoesNotThrow(
            () -> when(getAverageSpeeedMT.getTheMethod().invoke(fastMammalia, ArgumentMatchers.anyDouble()))
                .thenReturn(10d),
            "Could not Overwrite Method.");
        var slowMammalia = mock(animalCT.getTheClass(),
            withSettings().extraInterfaces(walkingCT.getTheClass()).defaultAnswer(
                CALLS_REAL_METHODS));
        ClassTester.setField(slowMammalia, animalTypeField, animalTypeCT.getEnumValue("MAMMALIA", 0.8));
        assertDoesNotThrow(
            () -> when(getAverageSpeeedMT.getTheMethod().invoke(
                slowMammalia, ArgumentMatchers.anyDouble()))
                .thenReturn(2d),
            "Could not Overwrite Method.");

        // Haie sind nicht mit anderen Knorpelfischen kompatibel
        mt.assertReturnValueEquals(false, sharkCT.getClassInstance(), randomCondrichthy);
        mt.assertReturnValueEquals(false, randomCondrichthy, sharkCT.getClassInstance());
        mt.assertReturnValueEquals(true, sharkCT.getClassInstance(), sharkCT.getClassInstance());
        // Krokodile dürfen nicht mit Säugetieren zusammenleben
        mt.assertReturnValueEquals(false, randomCrocodilidae, randomMammalia);
        mt.assertReturnValueEquals(false, randomMammalia, randomCrocodilidae);
        // Vögel dürfen nur mit Tieren zusammenleben, die (falls Sie laufen können) bei
        // einer Strecke von 10 Metern langsamer als 3m/s sind.
        mt.assertReturnValueEquals(true, randomAve, randomCrocodilidae);
        mt.assertReturnValueEquals(true, randomCrocodilidae, randomAve);
        mt.assertReturnValueEquals(true, randomAve, slowMammalia);
        mt.assertReturnValueEquals(true, slowMammalia, randomAve);
        mt.assertReturnValueEquals(false, randomAve, fastMammalia);
        mt.assertReturnValueEquals(false, fastMammalia, randomAve);
    }

    @Test
    @DisplayName("9 | FamilyFriendlyZoo - isAllowed")
    public void t09() {
        var classTester = new ClassTester<>("h05", "FamilyFriendlyZoo", 0.8).resolve();
        var animalClassTester = new ClassTester<>("h05", "Animal", 0.8).resolveClass();
        var mt = new MethodTester(classTester, "isAllowed", 0.8, -1, boolean.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("a", 0.8, animalClassTester.getTheClass()))))
            .verify();
        var enumClassTester = new ClassTester<>("h05", "AnimalType", 0.8).resolveClass();
        Field animalTypeField = animalClassTester.resolveAttribute(
            new AttributeMatcher("animalType", 0.8, -1, enumClassTester.getClass()));

        for (int i = 0; i < 100; i++) {
            animalClassTester.resolveInstance();
            var animalType = animalClassTester.setFieldRandom(animalTypeField);
            mt.assertReturnValueEquals(true,
                animalClassTester.getClassInstance());
        }
    }

    @JagrOnlyTest
    @DisplayName("10 | familyFriendlyZoo - letterOfTheDay")
    public void t10() {
        var mt = new MethodTester(
            familyFriendlyZooCT.resolve(),
            "letterOfTheDay",
            0.8,
            Modifier.PUBLIC,
            char.class)
            .verify();
        ThreadLocalRandomTester.initialize();
        mt.invoke();
        var usedRanges = ThreadLocalRandomTester.current().getUsedRanges();
        assertEquals(usedRanges.size(), 1);
        usedRanges.forEach(r -> {
            assertEquals('a', r.lowerEndpoint());
            assertEquals((int) 'z' + 1, r.upperEndpoint());
        });
        ThreadLocalRandomTester.removeCurrentTester();
    }
}
