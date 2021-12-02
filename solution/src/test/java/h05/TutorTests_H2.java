package h05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.MethodTester;

@DisplayName("H2")
public class TutorTests_H2 {

    final String class_name = "Animal";

    @Test
    @DisplayName("1 | Enum AnimalType()")
    public void t01() {
        new ClassTester<>("h05", "AnimalType", 0.8, Modifier.PUBLIC | Modifier.FINAL | TestUtils.ENUM,
                null, new ArrayList<>())
                        .verify()
                        .assertEnumConstants(new String[] { "AVES", "MAMMALIA", "CROCODYLIDAE", "CHONDRICHTHYES" });
    }

    @Test
    @DisplayName("2 | Existenz Klasse " + class_name)
    public void t02() {
        new ClassTester<>("h05", "Animal", 1.0, Modifier.PUBLIC | Modifier.ABSTRACT).verify();
    }

    @Test
    @DisplayName("3 | Attribut animalType + Getter")
    public void t03() {
        var classTester = new ClassTester<>("h05", "Animal", 0.8).resolve();
        var enumClassTester = new ClassTester<>("h05", "AnimalType", 0.8).resolveClass();
        Field animalTypeField = classTester.resolveAttribute(
                new AttributeMatcher("animalType", 0.8, Modifier.PROTECTED, enumClassTester.getClass()));

        classTester.assertHasGetter(animalTypeField);
    }

    @Test
    @DisplayName("4 | Test ToString()")
    public void t4() {
        var classTester = new ClassTester<>("h05", "Animal", 0.8).resolve();
        var enumClassTester = new ClassTester<>("h05", "AnimalType", 0.8).resolveClass();
        Field animalTypeField = classTester.resolveAttribute(
                new AttributeMatcher("animalType", 0.8, Modifier.PROTECTED, enumClassTester.getClass()));

        var methodTester = new MethodTester(classTester, "toString", 0.8, Modifier.PUBLIC, String.class,
                new ArrayList<>()).verify();

        assertDoesNotThrow(() -> animalTypeField.setAccessible(true));

        Object animalInstance = classTester.resolveInstance();
        classTester.setClassInstance(animalInstance);

        // Normal
        var expectedAnimalType = enumClassTester.getRandomEnumConstant();
        var expectedAnswer = String.format("My species is called %s which is part of animal type %s.",
                animalInstance.getClass().getSimpleName(),
                expectedAnimalType.name().substring(0, 1) + expectedAnimalType.name().substring(1).toLowerCase());
        assertDoesNotThrow(() -> animalTypeField.set(animalInstance, expectedAnimalType));
        var returnValue = methodTester.invoke();
        assertEquals(expectedAnswer, returnValue, "Falsche Rückgabe der toString-Metode.");
        // Null
        expectedAnswer = String.format("My species is called %s which is part of animal type Null.",
                animalInstance.getClass().getSimpleName());
        classTester.setField(animalTypeField, null);
        assertEquals(expectedAnswer, methodTester.invoke(), "Falsche Rückgabe der toString-Metode.");
    }
}
