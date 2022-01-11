package h05;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static h05.H05_Class_Testers.animalCT;
import static h05.H05_Class_Testers.animalTypeCT;
import static h05.H05_Class_Testers.ostrichCT;
import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h05")
@DisplayName("H3_1")
public class TutorTests_H3_1 {

    final String class_name = "Ostrich";

    @Test
    @DisplayName("1 | Existenz Klasse " + class_name)
    @SuppressWarnings("unchecked")
    public void t01() {
        ostrichCT.setSuperClass((Class<Object>) animalCT.assureClassResolved().getTheClass());
        ostrichCT.verify(1.0d);
    }

    @Test
    @DisplayName("2 | Attribut distanceSoFar + Getter")
    public void t02() {
        Field distanceSoFarField = ostrichCT.resolve()
            .resolveAttribute(new AttributeMatcher(
                "distanceSoFar",
                0.8,
                Modifier.PRIVATE,
                int.class));
        ostrichCT.assertHasGetter(distanceSoFarField);
    }

    @Test
    @DisplayName("3 | Methode getNumberOfLegs()")
    public void t03() {
        var methodTester = new MethodTester(
            ostrichCT.resolve(),
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
        var methodTester = new MethodTester(
            ostrichCT.resolve(),
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
        var methodTester = new MethodTester(ostrichCT.resolve(), "letMeMove", 0.8, -1, String.class)
            .verify();

        Field distanceSoFarField = ostrichCT
            .resolveAttribute(new AttributeMatcher("distanceSoFar", 0.8, Modifier.PRIVATE, int.class));

        var initialDistance = ClassTester.getRandomValue(distanceSoFarField.getType());
        ostrichCT.setField(distanceSoFarField, initialDistance);

        methodTester.assertReturnValueEquals(
            String.format("Distance so far: %s", ((Number) initialDistance).intValue() + 1));
        ostrichCT.assertFieldEquals(distanceSoFarField, ((Number) initialDistance).intValue() + 1);
    }

    @Test
    @DisplayName("6 | Konstruktor")
    @SuppressWarnings("unchecked")
    public void t06() {
        Constructor<Object> constructor = (Constructor<Object>) ostrichCT.assureClassResolved().resolveConstructor();
        ((ClassTester<Object>) ostrichCT).assertConstructorValid(constructor, Modifier.PUBLIC);

        animalTypeCT.assureClassResolved();
        Field animalTypeField = ostrichCT.resolveAttribute(
            new AttributeMatcher("animalType", 0.8, -1, animalTypeCT.getClass(), true));
        ((ClassTester<Object>) ostrichCT).setClassInstance(assertDoesNotThrow(() -> constructor.newInstance()));
        ostrichCT.assertFieldEquals(animalTypeField, animalTypeCT.getEnumValue("AVES", 0.8));
    }
}
