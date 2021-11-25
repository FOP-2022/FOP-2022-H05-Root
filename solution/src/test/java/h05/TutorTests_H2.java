package h05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.IdentifierMatcher;

import static h05.TestUtils.*;

@DisplayName("H2")
public class TutorTests_H2 {

    final String class_name = "Animal";

    @Test
    @DisplayName("1 | Enum AnimalClass()")
    @SuppressWarnings("unchecked")
    public void t01() {
        // Existance
        var clazz = assertDoesNotThrow(() -> Class.forName("h05.AnimalType"), "Interface AnimalType existiert nicht.");

        // Access Modifier
        var mod = clazz.getModifiers();
        var expected_mod = 16401; // public abstract interface
        assertTrue(clazz.isEnum(), String.format("%s ist kein Interface.", class_name));
        assertEquals(expected_mod, mod, String.format("Falscher Access Modifier. Gefordert: %s, Erhalten:%s",
                Modifier.toString(expected_mod), Modifier.toString(mod)));

        // Values
        var enum_class = (Class<Enum<?>>) clazz;
        var enum_values = enum_class.getEnumConstants();
        var expected_names = new String[] { "AVES", "MAMMALIA", "CROCODYLIDAE", "CHONDRICHTHYES" };
        for (String n : expected_names) {
            assertTrue(Stream.of(enum_values).anyMatch(x -> x.toString().equals(n)),
                    String.format("Enum-Konstante %s fehlt.", n));
        }

        // var classTester = new ClassTester<>("h05", "Animal", 1.0, Modifier.PUBLIC |
        // Modifier.ABSTRACT);
        // classTester.resolveClass();
        // classTester.assertIsPlainClass();
        // classTester.assertAccessModifier();
        // classTester.assertDoesNotImplementAnyInterfaces();
    }

    @Test
    @DisplayName("2 | Existenz Klasse " + class_name)
    public void t02() {
        // // Existance
        // var clazz = assertDoesNotThrow(() -> Class.forName(String.format("h05.%s",
        // class_name)),
        // "Klasse Swimming existiert nicht.");

        // // Access Modifier
        // var mod = clazz.getModifiers();
        // var expected_mod = 1025; // public abstract
        // assertFalse(Modifier.isInterface(mod), String.format("%s ist ein Interface.",
        // class_name));
        // assertFalse(clazz.isEnum(), String.format("%s ist ein Enum.", class_name));
        // assertEquals(expected_mod, mod, String.format("Falscher Access Modifier.
        // Gefordert: %s, Erhalten:%s",
        // Modifier.toString(expected_mod), Modifier.toString(mod)));
        var classTester = new ClassTester<>("h05", "Animal", 1.0, Modifier.PUBLIC | Modifier.ABSTRACT);
        classTester.resolveClass();
        classTester.assertIsPlainClass();
        classTester.assertAccessModifier();
        classTester.assertDoesNotImplementAnyInterfaces();
    }

    @Test
    @DisplayName("3 | Attribut animalClass + Getter")
    public void t03() {
        // Existance
        var clazz = assertDoesNotThrow(() -> Class.forName(String.format("h05.%s", class_name)));
        var attribute = assertDoesNotThrow(() -> clazz.getDeclaredField("animalClass"));

        // Access Modifier
        var mod = attribute.getModifiers();
        var expected_mod = 4; // public abstract
        assertEquals(expected_mod, mod, String.format("Falscher Access Modifier. Gefordert: %s, Erhalten:%s",
                Modifier.toString(expected_mod), Modifier.toString(mod)));

        // Getter
        Animal a = new Animal() {
            public String letMeMove() {
                return null;
            };
        };

        var test_value = ThreadLocalRandom.current().nextInt(10000);
        assertHasGetter(attribute, a, test_value);
    }

    @Test
    @DisplayName("4 | Test ToString()")
    public void t4() {
        assertEquals("My species is called Ostrich which is part of animal type Aves.", new Ostrich().toString());
    }
}
