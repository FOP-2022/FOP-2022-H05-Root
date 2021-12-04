package h05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.IdentifierMatcher;
import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

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
        // var classTester = new ClassTester<>("h05", "FamilyFriendlyZoo", 0.8).resolve();
        // var animalClassTester = new ClassTester<>("h05", "Animal", 0.8).resolveClass();
        // var mt = new MethodTester(classTester, "FamilyFriendlyZoo", 0.8, Modifier.PUBLIC, boolean.class,
        //         new ArrayList<>(List.of(
        //                 new ParameterMatcher("a1", 0.8, animalClassTester.getTheClass()),
        //                 new ParameterMatcher("a2", 0.8, animalClassTester.getTheClass()))))
        //                         .verify();
        // var animalSubtypes = new String[] { "Animal", "Ostrich", "Shark", "SaltWaterCrocodyle" };
        // for (final var name1 : animalSubtypes) {
        //     for (final var name2 : animalSubtypes) {
        //         var ct1 = new ClassTester<>("h05", name1, 0.8).resolve();
        //         var ct2 = new ClassTester<>("h05", name2, 0.8).resolve();
        //         mt.assertReturnValueEquals(true, ct1.getClassInstance(), ct2.getClassInstance());
        //     }
        // }
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
