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
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.IdentifierMatcher;
import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;

@TestForSubmission("h05")
@DisplayName("H3_1")
public class TutorTests_H3_1 {

    final String class_name = "Ostrich";

    @Test
    @DisplayName("1 | Existenz Klasse " + class_name)
    public void t01() {
        var animalClassTester = new ClassTester<>("h05", "Animal", 0.8).resolveClass();
        new ClassTester<>("h05", class_name, 1.0, Modifier.PUBLIC,
                animalClassTester.getTheClass(),
                new ArrayList<>(List.of(new IdentifierMatcher("Walking", "h05", 0.8)))).verify();
    }

    @Test
    @DisplayName("2 | Attribut distanceSoFar + Getter")
    public void t02() {
        var classTester = new ClassTester<>("h05", "Ostrich", 0.8).resolveClass();
        Field distanceSoFarField = classTester
                .resolveAttribute(new AttributeMatcher(
                        "distanceSoFar",
                        0.8,
                        Modifier.PRIVATE,
                        double.class));
        classTester.assertHasGetter(distanceSoFarField);
    }

    @Test
    @DisplayName("3 | Methode getNumberOfLegs()")
    public void t03() {
        var classTester = new ClassTester<>("h05", "Ostrich", 0.8).resolve();
        var methodTester = new MethodTester(
                classTester,
                "getNumberOfLegs",
                0.8,
                Modifier.PUBLIC,
                byte.class,
                new ArrayList<>()).verify();
        var returnValue = methodTester.invoke();
        assertEquals((byte) 2, returnValue, "Falsche Rückgabe der Methode getNumberOfLegs.");
    }

    @Test
    @DisplayName("4 | Methode getAverageSpeed()")
    public void t04() {
        var classTester = new ClassTester<>("h05", "Ostrich", 0.8).resolve();
        var methodTester = new MethodTester(
                classTester,
                "getAverageSpeed",
                0.8,
                Modifier.PUBLIC,
                double.class,
                new ArrayList<>(List.of(new ParameterMatcher(
                        "distance",
                        .8,
                        double.class))))
                                .verify();
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
    @DisplayName("5 | Methode letMeMove()")
    public void t05() {
        var classTester = new ClassTester<>("h05", "Ostrich", 0.8).resolve();
        var methodTester = new MethodTester(classTester, "letMeMove", 0.8, Modifier.PUBLIC, String.class)
                .verify();

        Field distanceSoFarField = classTester
                .resolveAttribute(new AttributeMatcher("distanceSoFar", 0.8, Modifier.PRIVATE, double.class));

        double initialDistance = (double) ClassTester.getRandomValue(double.class);
        classTester.setField(distanceSoFarField, initialDistance);

        methodTester.assertReturnValueEquals(String.format("Distance so far: %s", initialDistance + 1d));
        classTester.assertFieldEquals(distanceSoFarField, initialDistance + 1d);
    }

    @Test
    @DisplayName("6 | Konstruktor")
    public void t06() {
        var classTester = new ClassTester<>("h05", "Ostrich", 0.8).resolveClass();
        var constructor = classTester.resolveConstructor();
        classTester.assertConstructorValid(constructor, Modifier.PUBLIC);

        var enumClassTester = new ClassTester<>("h05", "AnimalType", 0.8).resolveClass();
        Field animalTypeField = classTester.resolveAttribute(
                new AttributeMatcher("animalType", 0.8, Modifier.PROTECTED, enumClassTester.getClass(), true));
        classTester.setClassInstance(assertDoesNotThrow(() -> constructor.newInstance()));
        classTester.assertFieldEquals(animalTypeField, enumClassTester.getEnumValue("AVES", 0.8));
    }
}
