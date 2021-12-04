package h05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.IdentifierMatcher;
import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

@DisplayName("H3.4")
public class TutorTests_H3_4 {

    final String class_name = "SaltWaterCrocodileAsAmphibean";

    @Test
    @DisplayName("2 | Existenz Klasse " + class_name)
    public void t02() {
        new ClassTester<>("h05", class_name, 1.0, Modifier.PUBLIC, null,
                new ArrayList<>(List.of(new IdentifierMatcher("Amphibean", "h05", 0.8),
                        new IdentifierMatcher("Cloneable", "h05", 1.0)))).verify();
    }

    @Test
    @DisplayName("3 | Methode Clone")
    public void t03() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolve();
        var mt = new MethodTester(classTester, "clone", 0.8, Modifier.PUBLIC, Object.class, null, false).verify();
        Field saltyField = classTester
                .resolveAttribute(new AttributeMatcher("salty", 0.8, Modifier.PRIVATE, short.class));

        var saltWaterCrocodileTester = new ClassTester<>("h05", "SaltWaterCrocodile", 0.8);
        saltWaterCrocodileTester.findClass();
        var saltyInstance = saltWaterCrocodileTester.resolveInstance();

        classTester.setField(saltyField, saltyInstance);

        var clonedSWCAA = mt.invoke();
        assertNotSame(saltyInstance, clonedSWCAA);
        assertSame(saltyInstance, ClassTester.getFieldValue(clonedSWCAA, saltyField));
        // classTester.assertHasGetter(saltyField);
    }

    @Test
    @DisplayName("4 | canLiveInSaltWater")
    public void t04() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolve();
        var saltyClassTester = new ClassTester<>("h05", "SaltWaterCrocodile", 0.8).resolve();
        Field saltyField = classTester
                .resolveAttribute(new AttributeMatcher("salty", 0.8, short.class));
        classTester.setFieldRandom(saltyField);
        MethodTester mt = new MethodTester(classTester, "canLiveInSaltWater", 0.8, Modifier.PUBLIC, boolean.class)
                .verify();
        MethodTester saltyMt = new MethodTester(saltyClassTester, "canLiveInSaltWater", 0.8, -1, boolean.class)
                .verify();
        mt.assertReturnValueEquals(saltyMt.invoke());
    }

    @Test
    @DisplayName("5 | canLiveInFreshWater")
    public void t05() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolve();
        var saltyClassTester = new ClassTester<>("h05", "SaltWaterCrocodile", 0.8).resolve();
        Field saltyField = classTester
                .resolveAttribute(new AttributeMatcher("salty", 0.8, short.class));
        classTester.setFieldRandom(saltyField);
        MethodTester mt = new MethodTester(classTester, "canLiveInFreshWater", 0.8, Modifier.PUBLIC, boolean.class)
                .verify();
        MethodTester saltyMt = new MethodTester(saltyClassTester, "canLiveInFreshWater", 0.8, -1, boolean.class)
                .verify();
        mt.assertReturnValueEquals(saltyMt.invoke());
    }

    @Test
    @DisplayName("6 | letMeSwim")
    public void t06() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolve();
        var saltyClassTester = new ClassTester<>("h05", "SaltWaterCrocodile", 0.8).resolve();
        Field saltyField = classTester
                .resolveAttribute(new AttributeMatcher("salty", 0.8, short.class));
        classTester.setFieldRandom(saltyField);
        MethodTester mt = new MethodTester(classTester, "letMeSwim", 0.8,
                Modifier.PUBLIC, void.class,
                new ArrayList<>(List.of(
                        new ParameterMatcher("distance", 0.8, char.class),
                        new ParameterMatcher("x", 1.0, double.class),
                        new ParameterMatcher("y", 1.0, double.class)))).verify();
        MethodTester saltyMt = new MethodTester(saltyClassTester, "letMeSwim", 0.8,
                -1, void.class,
                new ArrayList<>(List.of(
                        new ParameterMatcher("distance", 0.8, char.class),
                        new ParameterMatcher("x", 1.0, double.class),
                        new ParameterMatcher("y", 1.0, double.class)))).verify();
        var params = mt.getRandomParams();
        mt.assertReturnValueEquals(saltyMt.invoke(params), params);
    }

    @Test
    @DisplayName("8 | Konstruktor")
    public void t08() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolveClass();
        var animalClassTester = new ClassTester<>("h05", "Animal", 0.8).resolve();
        var saltyClassTester = new ClassTester<>("h05", "SaltWaterCrocodile", 0.8).resolve();

        var constructor = classTester
                .resolveConstructor(new ParameterMatcher("animal", 0.8, animalClassTester.getTheClass()));
        classTester.assertConstructorValid(constructor, Modifier.PUBLIC,
                new ParameterMatcher("animal", 0.8, animalClassTester.getTheClass()));

        Field saltyField = classTester
                .resolveAttribute(new AttributeMatcher("salty", 0.8, short.class));
        // Valid Value
        classTester.setClassInstance(
                assertDoesNotThrow(() -> constructor.newInstance(saltyClassTester.getClassInstance())));
        classTester.assertFieldEquals(saltyField, saltyClassTester.getClassInstance());
        // Invalid Value
        classTester.setClassInstance(
                assertDoesNotThrow(() -> constructor.newInstance(animalClassTester.getClassInstance())));
        classTester.assertFieldEquals(saltyField, null);
    }
}
