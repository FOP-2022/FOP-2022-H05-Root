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
public class TutorTests_H3_2 {

    final String class_name = "Shark";

    @Test
    @DisplayName("2 | Existenz Klasse " + class_name)
    public void t02() {
        var classTester = new ClassTester<>("h05", class_name, 1.0, Modifier.PUBLIC, null,
                new ArrayList<>(List.of(new IdentifierMatcher("Swimming", "h05", 0.8),
                        new IdentifierMatcher("IntConsumer", "java.util.function.IntConsumer", 1.0))));
        classTester.resolveClass();
        classTester.assertIsPlainClass();
        classTester.assertAccessModifier();
        var animalClassTester = new ClassTester<>("h05", "Animal", 0.8);
        animalClassTester.resolveClass();
        classTester.setSuperClass(animalClassTester.getTheClass());
        classTester.assertImplementsInterfaces();
    }

    @Test
    @DisplayName("3 | Attribute specificSpecies, x, y, degreeOfHunger + Getter")
    public void t03() {
        var classTester = new ClassTester<>("h05", class_name, 0.8);
        classTester.resolveClass();
        Field specificSpeciesField = classTester
                .resolveAttribute(new AttributeMatcher("specificSpecies", 0.8, Modifier.PRIVATE, short.class));
        classTester.assertHasGetter(specificSpeciesField);
        Field xField = classTester.resolveAttribute(new AttributeMatcher("x", 1.0, Modifier.PRIVATE, int.class));
        classTester.assertHasGetter(xField);
        Field yField = classTester.resolveAttribute(new AttributeMatcher("y", 1.0, Modifier.PRIVATE, int.class));
        classTester.assertHasGetter(yField);
        Field degreeOfHungerField = classTester
                .resolveAttribute(new AttributeMatcher("degreeOfHunger", 1.0, Modifier.PRIVATE, int.class));
        classTester.assertHasGetter(degreeOfHungerField);
    }
}
