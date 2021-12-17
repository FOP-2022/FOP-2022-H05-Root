package h05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import h05.ReflectionUtils.IdentifierMatcher;
import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;
import static h05.H05_Class_Testers.*;

@TestForSubmission("h05")
@DisplayName("H3.4")
public class TutorTests_H3_4 {

    final String class_name = "SaltWaterCrocodileAsAmphibean";

    @Test
    @DisplayName("1 | Existenz Klasse " + class_name)
    public void t01() {
        saltWaterCrocodileAsAmphibeanCT.verify(1.0d);
    }

    @Test
    @DisplayName("1-Alt | Existenz Klasse " + class_name)
    public void t01_alt() {
        new ClassTester<>("h05",
            "SaltWaterCrocodileAsAmphibean", minSim, Modifier.PUBLIC, null,
            new ArrayList<>(List.of(new IdentifierMatcher("Amphibean", "h05", minSim)))).verify(1.0d);
    }

    @Test
    @DisplayName("2 | Methode Clone")
    public void t02() {
        saltWaterCrocodileAsAmphibeanCT.resolveReal();
        var mt = new MethodTester(
            saltWaterCrocodileAsAmphibeanCT, "clone", 0.8, -1, Object.class, null, false, true).verify();
        // Hier gab es mehrere korrekte Möglichkeiten, wodurch der Modifier nicht exakt
        // vorgegeben war. Public muss es aber sein
        assertTrue(Modifier.isPublic(mt.getAccessModifier()), "Methode Clone ist nicht public.");
        Field saltyField = saltWaterCrocodileAsAmphibeanCT
            .resolveAttribute(new AttributeMatcher("salty", 0.8, -1, short.class));

        var saltyInstance = saltWaterCrocodileCT.resolve().getClassInstance();

        saltWaterCrocodileAsAmphibeanCT.setField(saltyField, saltyInstance);

        var clonedSWCAA = mt.invoke();
        assertNotSame(saltyInstance, clonedSWCAA);
        assertNotNull(clonedSWCAA, "Falsche Rückgabe bei Methode Clone: ");
        assertSame(saltyInstance, ClassTester.getFieldValue(clonedSWCAA, saltyField));
    }

    @Test
    @DisplayName("2,5 | Methode Equals")
    public void t02_two() {
        saltWaterCrocodileAsAmphibeanCT.resolveClass();
        var mt = new MethodTester(
            saltWaterCrocodileAsAmphibeanCT, "equals", 0.8, Modifier.PUBLIC | Modifier.VOLATILE, boolean.class,
            new ArrayList<>(List.of(new ParameterMatcher(Object.class, true))), false).verify();
        Field saltyField = saltWaterCrocodileAsAmphibeanCT
            .resolveAttribute(new AttributeMatcher("salty", 0.8, Modifier.PRIVATE, short.class));
        var saltyInstance = saltWaterCrocodileCT.resolve().getClassInstance();
        saltWaterCrocodileAsAmphibeanCT.resolveRealInstance();
        saltWaterCrocodileAsAmphibeanCT.setField(saltyField, saltyInstance);
        mt.assertReturnValueEquals(true, "Aufruf mit sich selbst.", saltWaterCrocodileAsAmphibeanCT.getClassInstance());
        var newSWCInstance = saltWaterCrocodileAsAmphibeanCT.getNewRealInstance();
        ClassTester.setField(newSWCInstance, saltyField, saltyInstance);
        mt.assertReturnValueEquals(true, "Aufruf mit Kopie.", newSWCInstance);
    }

    @Test
    @DisplayName("3 | canLiveInSaltWater")
    public void t03() {
        saltWaterCrocodileAsAmphibeanCT.resolve();
        saltWaterCrocodileCT.resolve();
        Field saltyField = saltWaterCrocodileAsAmphibeanCT
            .resolveAttribute(new AttributeMatcher("salty", 0.8, short.class));
        saltWaterCrocodileAsAmphibeanCT.setFieldRandom(saltyField);
        MethodTester mt = new MethodTester(
            saltWaterCrocodileAsAmphibeanCT, "canLiveInSaltWater", 0.8, Modifier.PUBLIC, boolean.class)
                .verify();
        MethodTester saltyMt = new MethodTester(
            saltWaterCrocodileCT, "canLiveInSaltWater", 0.8, -1, boolean.class)
                .verify();
        mt.assertReturnValueEquals(saltyMt.invoke());
    }

    @Test
    @DisplayName("4 | canLiveInFreshWater")
    public void t04() {
        saltWaterCrocodileAsAmphibeanCT.resolve();
        saltWaterCrocodileCT.resolve();
        Field saltyField = saltWaterCrocodileAsAmphibeanCT
            .resolveAttribute(new AttributeMatcher("salty", 0.8, short.class));
        saltWaterCrocodileAsAmphibeanCT.setFieldRandom(saltyField);
        MethodTester mt = new MethodTester(
            saltWaterCrocodileAsAmphibeanCT, "canLiveInFreshWater", 0.8, Modifier.PUBLIC, boolean.class)
                .verify();
        MethodTester saltyMt = new MethodTester(
            saltWaterCrocodileCT, "canLiveInFreshWater", 0.8, -1, boolean.class)
                .verify();
        mt.assertReturnValueEquals(saltyMt.invoke());
    }

    @Test
    @DisplayName("5 | letMeSwim")
    public void t05() {
        saltWaterCrocodileAsAmphibeanCT.resolve();
        saltWaterCrocodileCT.resolve();
        Field saltyField = saltWaterCrocodileAsAmphibeanCT
            .resolveAttribute(new AttributeMatcher("salty", 0.8, short.class));
        saltWaterCrocodileAsAmphibeanCT.setFieldRandom(saltyField);
        MethodTester mt = new MethodTester(
            saltWaterCrocodileAsAmphibeanCT, "letMeSwim", 0.8,
            Modifier.PUBLIC, void.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("distance", 0.8, char.class),
                new ParameterMatcher("x", 1.0, double.class),
                new ParameterMatcher("y", 1.0, double.class)))).verify();
        MethodTester saltyMt = new MethodTester(
            saltWaterCrocodileCT, "letMeSwim", 0.8,
            -1, void.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("distance", 0.8, char.class),
                new ParameterMatcher("x", 1.0, double.class),
                new ParameterMatcher("y", 1.0, double.class)))).verify();
        var params = mt.getRandomParams();
        mt.assertReturnValueEquals(saltyMt.invoke(params), params);
    }

    @Test
    @DisplayName("6 | Konstruktor")
    @SuppressWarnings("unchecked")
    public void t06() {
        saltWaterCrocodileAsAmphibeanCT.resolveClass();
        animalCT.resolve();
        saltWaterCrocodileCT.resolve();

        var constructor = (Constructor<Object>) saltWaterCrocodileAsAmphibeanCT
            .resolveConstructor(new ParameterMatcher("animal", 0.8, animalCT.getTheClass()));
        ((ClassTester<Object>) saltWaterCrocodileAsAmphibeanCT).assertConstructorValid(constructor, Modifier.PUBLIC,
            new ParameterMatcher("animal", 0.8, animalCT.getTheClass()));

        Field saltyField = saltWaterCrocodileAsAmphibeanCT
            .resolveAttribute(new AttributeMatcher("salty", 0.8, short.class));
        // Valid Value
        ((ClassTester<Object>) saltWaterCrocodileAsAmphibeanCT).setClassInstance(
            assertDoesNotThrow(() -> constructor.newInstance(saltWaterCrocodileCT.getClassInstance())));
        saltWaterCrocodileAsAmphibeanCT.assertFieldEquals(saltyField, saltWaterCrocodileCT.getClassInstance());
        // Invalid Value
        ((ClassTester<Object>) saltWaterCrocodileAsAmphibeanCT).setClassInstance(
            assertDoesNotThrow(() -> constructor.newInstance(animalCT.getClassInstance())));
        saltWaterCrocodileAsAmphibeanCT.assertFieldEquals(saltyField, null);
    }
}
