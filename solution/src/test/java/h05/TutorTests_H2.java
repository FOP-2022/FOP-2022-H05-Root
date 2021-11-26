package h05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.IdentifierMatcher;
import h05.ReflectionUtils.MethodTester;

import static h05.TestUtils.*;

@DisplayName("H2")
public class TutorTests_H2 {

    final String class_name = "Animal";

    @Test
    @DisplayName("1 | Enum AnimalType()")
    public void t01() {
        var classTester = new ClassTester<>("h05", "AnimalType", 0.8, Modifier.PUBLIC | Modifier.FINAL | TestUtils.ENUM,
                null, new ArrayList<>());
        classTester.resolveClass();
        classTester.assertIsEnum();
        classTester.assertAccessModifier();
        classTester.assertImplementsInterfaces();
        classTester.assertEnumConstants(new String[] { "AVES", "MAMMALIA", "CROCODYLIDAE", "CHONDRICHTHYES" });
    }

    @Test
    @DisplayName("2 | Existenz Klasse " + class_name)
    public void t02() {
        var classTester = new ClassTester<>("h05", "Animal", 1.0, Modifier.PUBLIC | Modifier.ABSTRACT);
        classTester.resolveClass();
        classTester.assertIsPlainClass();
        classTester.assertAccessModifier();
        classTester.assertDoesNotImplementAnyInterfaces();
    }

    @Test
    @DisplayName("3 | Attribut animalType + Getter")
    public void t03() {
        var classTester = new ClassTester<>("h05", "Animal", 0.8);
        var enumClassTester = new ClassTester<>("h05", "AnimalType", 0.8);
        classTester.resolveClass();
        enumClassTester.resolveClass();
        Field animalTypeField = classTester.resolveAttribute(
                new AttributeMatcher("animalType", 0.8, Modifier.PROTECTED, enumClassTester.getClass()));

        var methodTester = new MethodTester(classTester, "getAnimalType", 0.8, Modifier.PUBLIC,
                enumClassTester.getTheClass(), new ArrayList<>());
        methodTester.resolveMethod();
        methodTester.assertAccessModifier();
        methodTester.assertParametersMatch();
        methodTester.assertReturnType();

        assertDoesNotThrow(() -> animalTypeField.setAccessible(true));
        var instance = classTester.resolveInstance();
        var expectedReturnValue = enumClassTester.getRandomEnumConstant();
        assertDoesNotThrow(() -> animalTypeField.set(instance, expectedReturnValue));
        var returnValue = methodTester.invoke();
        assertEquals(expectedReturnValue, returnValue, "Falsche Rückgabe der Getter-Metode.");
        // // Existance
        // var clazz = assertDoesNotThrow(() -> Class.forName(String.format("h05.%s",
        // class_name)));
        // var attribute = assertDoesNotThrow(() ->
        // clazz.getDeclaredField("animalClass"));

        // // Access Modifier
        // var mod = attribute.getModifiers();
        // var expected_mod = 4; // public abstract
        // assertEquals(expected_mod, mod, String.format("Falscher Access Modifier.
        // Gefordert: %s, Erhalten:%s",
        // Modifier.toString(expected_mod), Modifier.toString(mod)));

        // // Getter
        // Animal a = new Animal() {
        // public String letMeMove() {
        // return null;
        // };
        // };

        // var test_value = ThreadLocalRandom.current().nextInt(10000);
        // assertHasGetter(attribute, a, test_value);
    }

    @Test
    @DisplayName("4 | Test ToString()")
    public void t4() {
        assertEquals("My species is called Ostrich which is part of animal type Aves.", new Ostrich().toString());
    }
}
