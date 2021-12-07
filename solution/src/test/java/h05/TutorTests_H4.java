package h05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.IdentifierMatcher;
import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import static h05.H05_Class_Testers.*;

@TestForSubmission("h05")
@DisplayName("H4")
public class TutorTests_H4 {
    // Interface ZOO
    @Test
    @DisplayName("2 | Existenz Interface Zoo")
    public void t01() {
        new ClassTester<>("h05", "Zoo", 1.0, Modifier.PUBLIC | Modifier.ABSTRACT | Modifier.INTERFACE).verify();
    }

    @Test
    @DisplayName("2 | Zoo - canLiveTogether")
    public void t02() {
        var classTester = new ClassTester<>("h05", "Zoo", 0.8).resolveClass();
        var animalClassTester = new ClassTester<>("h05", "Animal", 0.8).resolveClass();
        new MethodTester(classTester, "canLiveTogether", 0.8, Modifier.PUBLIC | Modifier.ABSTRACT, boolean.class,
                new ArrayList<>(List.of(
                        new ParameterMatcher("a1", 0.8, animalClassTester.getTheClass()),
                        new ParameterMatcher("a2", 0.8, animalClassTester.getTheClass()))))
                                .verify();
    }

    @Test
    @DisplayName("3 | Zoo - isAllowed")
    public void t03() {
        var classTester = new ClassTester<>("h05", "Zoo", 0.8).resolveClass();
        var animalClassTester = new ClassTester<>("h05", "Animal", 0.8).resolveClass();
        new MethodTester(classTester, "isAllowed", 0.8, Modifier.PUBLIC | Modifier.ABSTRACT, boolean.class,
                new ArrayList<>(List.of(
                        new ParameterMatcher("a", 0.8, animalClassTester.getTheClass()))))
                                .verify();
    }

    // Survival of the Fittest Zoo
    @Test
    @DisplayName("4 | Existenz Klasse SurvivalOfTheFittestZoo")
    public void t04() {
        new ClassTester<>("h05", "SurvivalOfTheFittestZoo", 1.0, Modifier.PUBLIC, null, new ArrayList<>(List.of(
                new IdentifierMatcher("Zoo", 0.8)))).verify();
    }

    @Test
    @DisplayName("5 | SurvivalOfTheFittestZoo - canLiveTogether")
    public void t05() {
        var classTester = new ClassTester<>("h05", "SurvivalOfTheFittestZoo", 0.8).resolve();
        var animalClassTester = new ClassTester<>("h05", "Animal", 0.8).resolveClass();
        var mt = new MethodTester(classTester, "canLiveTogether", 0.8, Modifier.PUBLIC, boolean.class,
                new ArrayList<>(List.of(
                        new ParameterMatcher("a1", 0.8, animalClassTester.getTheClass()),
                        new ParameterMatcher("a2", 0.8, animalClassTester.getTheClass()))))
                                .verify();
        var animalSubtypes = new String[] { "Animal", "Ostrich", "Shark", "SaltWaterCrocodyle" };
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
        var classTester = new ClassTester<>("h05", "SurvivalOfTheFittestZoo", 0.8).resolve();
        var animalClassTester = new ClassTester<>("h05", "Animal", 0.8).resolveClass();
        var mt = new MethodTester(classTester, "isAllowed", 0.8, Modifier.PUBLIC, boolean.class,
                new ArrayList<>(List.of(
                        new ParameterMatcher("a", 0.8, animalClassTester.getTheClass()))))
                                .verify();
        var enumClassTester = new ClassTester<>("h05", "AnimalType", 0.8).resolveClass();
        Field animalTypeField = animalClassTester.resolveAttribute(
                new AttributeMatcher("animalType", 0.8, -1, enumClassTester.getClass()));

        for (int i = 0; i < 100; i++) {
            animalClassTester.resolveInstance();
            var animalType = animalClassTester.setFieldRandom(animalTypeField);
            mt.assertReturnValueEquals(enumClassTester.getEnumValue("AVES", 0.8) == animalType,
                    animalClassTester.getClassInstance());
        }
    }

    // Family Friendly Zoo

    @Test
    @DisplayName("7 | Existenz Klasse FamilyFriendlyZoo")
    public void t07() {
        new ClassTester<>("h05", "FamilyFriendlyZoo", 1.0, Modifier.PUBLIC, null, new ArrayList<>(List.of(
                new IdentifierMatcher("Zoo", 0.8)))).verify();
    }

    @Test
    @DisplayName("8 | FamilyFriendlyZoo - canLiveTogether")
    public void t08() {
        walkingCT.resolveClass();
        familyFriendlyZooCT.resolve();
        animalCT.resolveClass();
        animalTypeCT.resolveClass();
        sharkCT.resolve();
        saltWaterCrocodileCT.resolve();
        var mt = new MethodTester(familyFriendlyZooCT, "canLiveTogether", minSim, Modifier.PUBLIC, boolean.class,
                new ArrayList<>(List.of(
                        new ParameterMatcher("a1", minSim, animalCT.getTheClass()),
                        new ParameterMatcher("a2", minSim, animalCT.getTheClass()))))
                                .verify();
        Field animalTypeField = animalCT.resolveAttribute(
                new AttributeMatcher("animalType", 0.8, Modifier.PROTECTED, animalTypeCT.getClass()));
        var getAverageSpeeedMT = new MethodTester(walkingCT, "getAverageSpeed", 0.8,
                Modifier.PUBLIC | Modifier.ABSTRACT,
                double.class, new ArrayList<>(List.of(new ParameterMatcher("distance", 0.8, double.class))));
        getAverageSpeeedMT.resolveMethod();

        // Dynamically Generated Test Animals
        var random_ave = mock(animalCT.getTheClass(), CALLS_REAL_METHODS);
        ClassTester.setField(random_ave, animalTypeField, animalTypeCT.getEnumValue("AVES", 0.8));
        var random_mammalia = mock(animalCT.getTheClass(), CALLS_REAL_METHODS);
        ClassTester.setField(random_mammalia, animalTypeField, animalTypeCT.getEnumValue("MAMMALIA", 0.8));
        var random_crocodilidae = mock(animalCT.getTheClass(), CALLS_REAL_METHODS);
        ClassTester.setField(random_crocodilidae, animalTypeField, animalTypeCT.getEnumValue("CROCODYLIDAE", 0.8));
        var random_condrichthy = mock(animalCT.getTheClass(), CALLS_REAL_METHODS);
        ClassTester.setField(random_condrichthy, animalTypeField, animalTypeCT.getEnumValue("CHONDRICHTHYES", 0.8));
        var fast_mammalia = mock(animalCT.getTheClass(),
                withSettings().extraInterfaces(walkingCT.getTheClass()).defaultAnswer(
                        CALLS_REAL_METHODS));
        ClassTester.setField(fast_mammalia, animalTypeField, animalTypeCT.getEnumValue("MAMMALIA", 0.8));
        assertDoesNotThrow(
                () -> when(getAverageSpeeedMT.getTheMethod().invoke(fast_mammalia, ArgumentMatchers.anyDouble()))
                        .thenReturn(10d),
                "Could not Overwrite Method.");
        var slow_mammalia = mock(animalCT.getTheClass(),
                withSettings().extraInterfaces(walkingCT.getTheClass()).defaultAnswer(
                        CALLS_REAL_METHODS));
        ClassTester.setField(slow_mammalia, animalTypeField, animalTypeCT.getEnumValue("MAMMALIA", 0.8));
        assertDoesNotThrow(
                () -> when(getAverageSpeeedMT.getTheMethod().invoke(
                        slow_mammalia, ArgumentMatchers.anyDouble()))
                                .thenReturn(2d),
                "Could not Overwrite Method.");

        // Haie sind nicht mit anderen Knorpelfischen kompatibel
        mt.assertReturnValueEquals(false, sharkCT.getClassInstance(), random_condrichthy);
        mt.assertReturnValueEquals(false, random_condrichthy, sharkCT.getClassInstance());
        mt.assertReturnValueEquals(true, sharkCT.getClassInstance(), sharkCT.getClassInstance());
        // Krokodile dürfen nicht mit Säugetieren zusammenleben
        mt.assertReturnValueEquals(false, random_crocodilidae, random_mammalia);
        mt.assertReturnValueEquals(false, random_mammalia, random_crocodilidae);
        // Vögel dürfen nur mit Tieren zusammenleben, die (falls Sie laufen können) bei
        // einer Strecke von 10 Metern langsamer als 3m/s sind.
        mt.assertReturnValueEquals(true, random_ave, random_crocodilidae);
        mt.assertReturnValueEquals(true, random_crocodilidae, random_ave);
        mt.assertReturnValueEquals(true, random_ave, slow_mammalia);
        mt.assertReturnValueEquals(true, slow_mammalia, random_ave);
        mt.assertReturnValueEquals(false, random_ave, fast_mammalia);
        mt.assertReturnValueEquals(false, fast_mammalia, random_ave);
    }

    @Test
    @DisplayName("9 | FamilyFriendlyZoo - isAllowed")
    public void t09() {
        var classTester = new ClassTester<>("h05", "FamilyFriendlyZoo", 0.8).resolve();
        var animalClassTester = new ClassTester<>("h05", "Animal", 0.8).resolveClass();
        var mt = new MethodTester(classTester, "isAllowed", 0.8, Modifier.PUBLIC, boolean.class,
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

    @Test
    @DisplayName("10 | SurvivalOfTheFittestZoo - letterOfTheDay")
    public void t10() {
        // var classTester = new ClassTester<>("h05", "SurvivalOfTheFittestZoo",
        // 0.8).resolve();
        // var animalClassTester = new ClassTester<>("h05", "Animal",
        // 0.8).resolveClass();
        // var mt = new MethodTester(classTester, "isAllowed", 0.8, Modifier.PUBLIC,
        // boolean.class,
        // new ArrayList<>(List.of(
        // new ParameterMatcher("a", 0.8, animalClassTester.getTheClass()))))
        // .verify();
        // var enumClassTester = new ClassTester<>("h05", "AnimalType",
        // 0.8).resolveClass();
        // Field animalTypeField = animalClassTester.resolveAttribute(
        // new AttributeMatcher("animalType", 0.8, -1, enumClassTester.getClass()));

        // for (int i = 0; i < 100; i++) {
        // animalClassTester.resolveInstance();
        // var animalType = animalClassTester.setFieldRandom(animalTypeField);
        // mt.assertReturnValueEquals(enumClassTester.getEnumValue("AVES", 0.8) ==
        // animalType,
        // animalClassTester.getClassInstance());
    }
}
