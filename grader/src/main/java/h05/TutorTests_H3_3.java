package h05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.IdentifierMatcher;
import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;

@TestForSubmission("h05")
@DisplayName("H3.3")
public class TutorTests_H3_3 {

    final String class_name = "SaltWaterCrocodile";

    @Test
    @DisplayName("1 | Existenz Klasse " + class_name)
    public void t01() {
        var animalClassTester = new ClassTester<>("h05", "Animal", 0.8).resolveClass();
        new ClassTester<>("h05", class_name, 1.0, Modifier.PUBLIC,
                animalClassTester.getTheClass(),
                new ArrayList<>(List.of(new IdentifierMatcher("Swimming", "h05", 0.8),
                        new IdentifierMatcher("Walking", "h05", 0.8)))).verify();
    }

    @Test
    @DisplayName("2 | Attribute averageSpeed")
    public void t02() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolve();

        Field averageSpeedField = classTester
                .resolveAttribute(new AttributeMatcher("averageSpeed", 0.8, Modifier.STATIC, short.class));
        classTester.assertHasGetter(averageSpeedField, new ParameterMatcher("distance", 0.8, double.class));
        classTester.assertHasSetter(averageSpeedField);
    }

    @Test
    @DisplayName("3 | canLiveInSaltWater")
    public void t03() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolve();
        MethodTester mt = new MethodTester(classTester, "canLiveInSaltWater", 0.8, Modifier.PUBLIC, boolean.class)
                .verify();
        mt.assertReturnValueEquals(true);
    }

    @Test
    @DisplayName("4 | canLiveInFreshWater")
    public void t04() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolve();
        MethodTester mt = new MethodTester(classTester, "canLiveInFreshWater", 0.8, Modifier.PUBLIC, boolean.class)
                .verify();
        mt.assertReturnValueEquals(true);
    }

    @Test
    @DisplayName("5 | letMeSwim")
    public void t05() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolve();
        MethodTester mt = new MethodTester(classTester, "letMeSwim", 0.8,
                Modifier.PUBLIC, void.class,
                new ArrayList<>(List.of(
                        new ParameterMatcher("distance", 0.8, char.class),
                        new ParameterMatcher("x", 1.0, double.class),
                        new ParameterMatcher("y", 1.0, double.class)))).verify();
        mt.invoke('f', 42d, 69d);
    }

    @Test
    @DisplayName("6 | letMeMove mit Hungerreduktion")
    public void t06() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolve();
        MethodTester mt = new MethodTester(classTester, "letMeMove", 0.8,
                Modifier.PUBLIC, String.class).verify();
        mt.assertReturnValueEquals("");
    }

    @Test
    @DisplayName("7 | Konstruktor")
    public void t07() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolveClass();
        var constructor = classTester.resolveConstructor(new ParameterMatcher("specificSpecies", 0.8, short.class));
        classTester.assertConstructorValid(constructor, Modifier.PUBLIC);

        var enumClassTester = new ClassTester<>("h05", "AnimalType", 0.8).resolveClass();
        Field animalTypeField = classTester.resolveAttribute(
                new AttributeMatcher("animalType", 0.8, Modifier.PROTECTED, enumClassTester.getClass(), true));
        // Valid Value
        classTester.setClassInstance(assertDoesNotThrow(() -> constructor.newInstance()));
        classTester.assertFieldEquals(animalTypeField, enumClassTester.getEnumValue("CROCODYLIDAE", 0.8));
    }
}
