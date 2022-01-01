package h05;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.MethodTester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import static h05.H05_Class_Testers.animalCT;
import static h05.H05_Class_Testers.animalTypeCT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h05")
@DisplayName("H2")
public class TutorTests_H2 {

    final String class_name = "Animal";

    @Test
    @DisplayName("1 | Enum AnimalType()")
    public void t01() {
        animalTypeCT
            .verify(1.0d)
            .assertEnumConstants(new String[]{"AVES", "MAMMALIA", "CROCODYLIDAE", "CHONDRICHTHYES"});
    }

    @Test
    @DisplayName("2 | Existenz Klasse " + class_name)
    public void t02() {
        animalCT.verify(1.0d);
    }

    @Test
    @DisplayName("3 | Attribut animalType + Getter")
    public void t03() {
        Field animalTypeField = animalCT.resolve().resolveAttribute(
            new AttributeMatcher("animalType", 1.0d, Modifier.PROTECTED,
                animalTypeCT.assureClassResolved().getClass()));

        animalCT.assertHasGetter(animalTypeField);
    }

    @Test
    @DisplayName("3-Alt | Attribut animalType + Getter")
    public void t03_alt() {
        Field animalTypeField = animalCT.resolve().resolveAttribute(
            new AttributeMatcher("animalType", 1.0d, Modifier.STATIC | Modifier.PROTECTED,
                animalTypeCT.assureClassResolved().getClass()));

        animalCT.assertHasGetter(animalTypeField);
    }

    @Test
    @DisplayName("4 | Test ToString()")
    public void t04() {
        Field animalTypeField = animalCT.resolve().resolveAttribute(
            new AttributeMatcher("animalType", 0.8, -1,
                animalTypeCT.assureClassResolved().getClass()));

        var methodTester = new MethodTester(animalCT.resolve(), "toString", 0.8, Modifier.PUBLIC, String.class,
            new ArrayList<>()).verify();

        assertDoesNotThrow(() -> animalTypeField.setAccessible(true));

        Object animalInstance = animalCT.getClassInstance();

        // Normal
        var expectedAnimalType = animalTypeCT.getRandomEnumConstant();
        var expectedAnswer = String.format("My species is called %s which is part of animal type %s.",
            animalInstance.getClass().getSimpleName(),
            expectedAnimalType.name().charAt(0) + expectedAnimalType.name().substring(1).toLowerCase());
        assertDoesNotThrow(() -> animalTypeField.set(animalInstance, expectedAnimalType));
        var returnValue = methodTester.invoke();
        assertEquals(expectedAnswer, returnValue, "Falsche Rückgabe der toString-Metode.");
        // Null
        expectedAnswer = String.format("My species is called %s which is part of animal type Null.",
            animalInstance.getClass().getSimpleName());
        animalCT.setField(animalTypeField, null);
        assertEquals(expectedAnswer, methodTester.invoke(), "Falsche Rückgabe der toString-Metode.");
    }

    @Test
    @DisplayName("5 | Methode letMeMove")
    public void t05() {
        // Auch wenn diese Lösung technisch inkorrekt ist, werden wir nachträglich keine Punkte mehr abziehen
        new MethodTester(animalCT.assureClassResolved(), "letMeMove", 0.8, Modifier.ABSTRACT | Modifier.PUBLIC,
            String.class,
            new ArrayList<>()).verify();
    }

    @Test
    @DisplayName("5-Alt | Methode letMeMove")
    public void t05_alt() {
        new MethodTester(animalCT.assureClassResolved(), "letMeMove", 0.8, Modifier.ABSTRACT,
            String.class,
            new ArrayList<>()).verify();
    }
}
