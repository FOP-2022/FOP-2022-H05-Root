package h05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.IdentifierMatcher;
import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;

@DisplayName("H2")
public class TutorTests_H3_1 {

    final String class_name = "Ostrich";

    @Test
    @DisplayName("2 | Existenz Klasse " + class_name)
    public void t02() {
        var classTester = new ClassTester<>("h05", class_name, 1.0, Modifier.PUBLIC, null,
                new ArrayList<>(List.of(new IdentifierMatcher("Walking", "h05", 0.8))));
        classTester.resolveClass();
        classTester.assertIsPlainClass();
        classTester.assertAccessModifier();
        var animalClassTester = new ClassTester<>("h05", "Animal", 0.8);
        animalClassTester.resolveClass();
        classTester.setSuperClass(animalClassTester.getTheClass());
        classTester.assertImplementsInterfaces();
    }

    @Test
    @DisplayName("3 | Attribut distanceSoFar + Getter")
    public void t03() {
        var classTester = new ClassTester<>("h05", "Ostrich", 0.8);
        classTester.resolveClass();
        Field distanceSoFarField = classTester
                .resolveAttribute(new AttributeMatcher("distanceSoFar", 0.8, Modifier.PRIVATE, double.class));

        classTester.assertHasGetter(distanceSoFarField);
    }

    @Test
    @DisplayName("4 | Methode getNumberOfLegs()")
    public void t04() {
        var classTester = new ClassTester<>("h05", "Ostrich", 0.8);
        classTester.resolveClass();
        var methodTester = new MethodTester(classTester, "getNumberOfLegs", 0.8, Modifier.PUBLIC, byte.class,
                new ArrayList<>());
        methodTester.resolveMethod();
        methodTester.assertAccessModifier();
        methodTester.assertParametersMatch();
        methodTester.assertReturnType();

        classTester.resolveInstance();
        var returnValue = methodTester.invoke();
        assertEquals((byte) 2, returnValue, "Falsche Rückgabe der Methode getNumberOfLegs.");
    }

    @Test
    @DisplayName("5 | Methode getAverageSpeed()")
    public void t05() {
        var classTester = new ClassTester<>("h05", "Ostrich", 0.8);
        classTester.resolveClass();
        var methodTester = new MethodTester(classTester, "getAverageSpeed", 0.8, Modifier.PUBLIC, double.class,
                new ArrayList<>(List.of(new ParameterMatcher("distance", .8, double.class))));
        methodTester.resolveMethod();
        methodTester.assertAccessModifier();
        methodTester.assertParametersMatch();
        methodTester.assertReturnType();

        classTester.resolveInstance();
        // Test 100 Times
        for (int i = 0; i < 100; i++) {
            double distance = -3.0d + i * 0.1d;
            var returnValue = methodTester.invoke(distance);
            String fs = String.format("Falsche Rückgabe bei distance %s.", distance);
            if (distance <= 0) {
                assertEquals(0d, returnValue, fs);
            } else if (distance <= 1.337d) {
                assertEquals(6.900d, returnValue, fs);
            } else if (distance <= 4.200d) {
                assertEquals(3.14d, returnValue, fs);
            } else {
                assertEquals(1d, returnValue, fs);
            }
        }
    }

    @Test
    @DisplayName("6 | Methode letMeMove()")
    public void t06() {
        var classTester = new ClassTester<>("h05", "Ostrich", 0.8);
        classTester.resolveClass();
        var methodTester = new MethodTester(classTester, "letMeMove", 0.8, Modifier.PUBLIC, String.class);
        methodTester.resolveMethod();
        methodTester.assertAccessModifier();
        methodTester.assertParametersMatch();
        methodTester.assertReturnType();

        Field distanceSoFarField = classTester
                .resolveAttribute(new AttributeMatcher("distanceSoFar", 0.8, Modifier.PRIVATE, double.class));

        assertDoesNotThrow(() -> distanceSoFarField.setAccessible(true));

        Object ostrichInstance = classTester.resolveInstance();
        classTester.setClassInstance(ostrichInstance);

        // var instance = classTester.resolveInstance
        double initialDistance = (double) ClassTester.getRandomValue(double.class);
        assertDoesNotThrow(() -> distanceSoFarField.set(ostrichInstance, initialDistance));

        var returnValue = methodTester.invoke();
        assertEquals(String.format("Distance so far: %s", initialDistance + 1d), returnValue,
                "Falsche Rückgabe bei Methode distanceSoFar:");
        assertEquals(initialDistance + 1d, assertDoesNotThrow(() -> distanceSoFarField.get(ostrichInstance)));
    }

    @Test
    @DisplayName("7 | Konstruktor")
    public void t07() {
        var classTester = new ClassTester<>("h05", "Ostrich", 0.8);
        classTester.resolveClass();
        var constructor = classTester.resolveConstructor();
        classTester.assertConstructorValid(constructor, Modifier.PUBLIC);

        var enumClassTester = new ClassTester<>("h05", "AnimalType", 0.8);
        enumClassTester.resolveClass();
        Field animalTypeField = classTester.resolveAttribute(
                new AttributeMatcher("animalType", 0.8, Modifier.PROTECTED, enumClassTester.getClass(), true));
        classTester.setClassInstance(assertDoesNotThrow(() -> constructor.newInstance()));
        classTester.assertFieldEquals(animalTypeField, enumClassTester.getEnumValue("AVES", 0.8));
    }
}
