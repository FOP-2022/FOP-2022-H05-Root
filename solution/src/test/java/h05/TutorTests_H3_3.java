package h05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;
import static h05.H05_Class_Testers.*;

@TestForSubmission("h05")
@DisplayName("H3.3")
public class TutorTests_H3_3 {

    final String class_name = "SaltWaterCrocodile";

    @Test
    @DisplayName("1 | Existenz Klasse " + class_name)
    @SuppressWarnings("unchecked")
    public void t01() {
        saltWaterCrocodileCT.setSuperClass((Class<Object>) animalCT.assureClassResolved().getTheClass());
        saltWaterCrocodileCT.verify(1.0d);
    }

    @Test
    @DisplayName("2 | Attribute averageSpeed")
    public void t02() {

        Field averageSpeedField = saltWaterCrocodileCT.resolve()
            .resolveAttribute(new AttributeMatcher("averageSpeed", 0.8, Modifier.STATIC, short.class));
        saltWaterCrocodileCT.assertHasGetter(averageSpeedField, new ParameterMatcher("distance", 0.8, double.class));
        saltWaterCrocodileCT.assertHasSetter(averageSpeedField);
    }

    @Test
    @DisplayName("3 | canLiveInSaltWater")
    public void t03() {
        MethodTester mt = new MethodTester(
            saltWaterCrocodileCT.resolve(), "canLiveInSaltWater", 0.8, Modifier.PUBLIC, boolean.class)
                .verify();
        mt.assertReturnValueEquals(true);
    }

    @Test
    @DisplayName("4 | canLiveInFreshWater")
    public void t04() {
        MethodTester mt = new MethodTester(
            saltWaterCrocodileCT, "canLiveInFreshWater", 0.8, Modifier.PUBLIC, boolean.class)
                .verify();
        mt.assertReturnValueEquals(true);
    }

    @Test
    @DisplayName("5 | letMeSwim")
    public void t05() {
        MethodTester mt = new MethodTester(
            saltWaterCrocodileCT, "letMeSwim", 0.8,
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
        MethodTester mt = new MethodTester(
            saltWaterCrocodileCT, "letMeMove", 0.8,
            -1, String.class).verify();
        mt.assertReturnValueEquals("");
    }

    @Test
    @DisplayName("7 | Konstruktor")
    @SuppressWarnings("unchecked")
    public void t07() {
        saltWaterCrocodileCT.assureClassResolved();
        var constructor = (Constructor<Object>) saltWaterCrocodileCT
            .resolveConstructor(new ParameterMatcher("specificSpecies", 0.8, short.class));
        ((ClassTester<Object>) saltWaterCrocodileCT).assertConstructorValid(constructor, -1);

        var enumClassTester = new ClassTester<>("h05", "AnimalType", 0.8).resolveClass();
        Field animalTypeField = saltWaterCrocodileCT.resolveAttribute(
            new AttributeMatcher("animalType", 0.8, -1, enumClassTester.getClass(), true));
        // Valid Value
        ((ClassTester<Object>) saltWaterCrocodileCT)
            .setClassInstance(assertDoesNotThrow(() -> constructor.newInstance()));
        saltWaterCrocodileCT.assertFieldEquals(animalTypeField, enumClassTester.getEnumValue("CROCODYLIDAE", 0.8));
    }
}
