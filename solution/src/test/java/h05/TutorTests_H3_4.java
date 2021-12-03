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
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

@DisplayName("H3.4")
public class TutorTests_H3_4 {

    final String class_name = "SaltWaterCrocodileAsAmphibean";

    @Test
    @DisplayName("2 | Existenz Klasse " + class_name)
    public void t02() {
        var classTester = new ClassTester<>("h05", class_name, 1.0, Modifier.PUBLIC, null,
                new ArrayList<>(List.of(new IdentifierMatcher("Amphibean", "h05", 0.8))));
        classTester.findClass();
        classTester.assertIsPlainClass();
        classTester.assertAccessModifier();
        var animalClassTester = new ClassTester<>("h05", "Animal", 0.8);
        animalClassTester.findClass();
        classTester.setSuperClass(animalClassTester.getTheClass());
        classTester.assertImplementsInterfaces();
    }

    @Test
    @DisplayName("3 | Attribut salty + Getter")
    public void t03() {
        var classTester = new ClassTester<>("h05", class_name, 0.8);
        classTester.findClass();
        classTester.resolveInstance();
        var mt = new MethodTester(classTester, "clone", 0.8);
        mt.resolveMethod();
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
        var classTester = new ClassTester<>("h05", class_name, 0.8);
        classTester.findClass();
        classTester.resolveInstance();
        Field specificSpeciesField = classTester
                .resolveAttribute(new AttributeMatcher("specificSpecies", 0.8, Modifier.PRIVATE, int.class));
        MethodTester mt = new MethodTester(classTester, "canLiveInSaltWater", 0.8, Modifier.PUBLIC, boolean.class,
                new ArrayList<>(List.of(
                // new ParameterMatcher("reductionOfHunger",0.8,int.class)
                )));
        mt.resolveMethod();
        for (int i = -20; i <= 20; i++) {
            var specificSpecies = i;
            classTester.setField(specificSpeciesField, (short) specificSpecies);
            mt.assertReturnValueEquals(IntStream.of(2, 5, 9).anyMatch(x -> x == specificSpecies),
                    "bei " + specificSpeciesField.getName() + "==" + i);
            classTester.assertFieldEquals(specificSpeciesField, (short) specificSpecies);
        }
    }

    @Test
    @DisplayName("5 | canLiveInFreshWater")
    public void t05() {
        var classTester = new ClassTester<>("h05", class_name, 0.8);
        classTester.findClass();
        classTester.resolveInstance();
        Field specificSpeciesField = classTester
                .resolveAttribute(new AttributeMatcher("specificSpecies", 0.8, Modifier.PRIVATE, int.class));
        MethodTester mt = new MethodTester(classTester, "canLiveInFreshWater", 0.8, Modifier.PUBLIC, boolean.class,
                new ArrayList<>(List.of(
                // new ParameterMatcher("reductionOfHunger",0.8,int.class)
                )));
        mt.resolveMethod();
        for (int i = -20; i <= 20; i++) {
            var specificSpecies = i;
            classTester.setField(specificSpeciesField, (short) specificSpecies);
            mt.assertReturnValueEquals(Math.abs(specificSpecies % 4) == 2,
                    "bei " + specificSpeciesField.getName() + "==" + i);
            classTester.assertFieldEquals(specificSpeciesField, (short) specificSpecies);
        }
    }

    @Test
    @DisplayName("6 | letMeSwim mit Hungerreduktion")
    public void t06() {
        // var classTester = new ClassTester<>("h05", class_name, 0.8);
        // classTester.resolveClass();
        // classTester.resolveInstance();
        // Field degreeOfHungerField = classTester
        // .resolveAttribute(new AttributeMatcher("degreeOfHunger", 0.8,
        // Modifier.PRIVATE, int.class));
        // Field xField = classTester
        // .resolveAttribute(new AttributeMatcher("x", 1.0, int.class));
        // Field yField = classTester
        // .resolveAttribute(new AttributeMatcher("y", 1.0, int.class));
        // MethodTester mt = new MethodTester(classTester, "letMeSwim", 0.8,
        // Modifier.PUBLIC, boolean.class,
        // new ArrayList<>(List.of(
        // // new ParameterMatcher("reductionOfHunger",0.8,int.class)
        // )));
        // mt.resolveMethod();
        // for (int i = 0; i <= 20; i++) {
        // var degreeOfHunger = ThreadLocalRandom.current().nextInt(-2000, 2000);
        // var x = ThreadLocalRandom.current().nextInt(-2000, 2000);
        // var y = ThreadLocalRandom.current().nextInt(-2000, 2000);
        // classTester.setField(degreeOfHungerField, degreeOfHunger);
        // classTester.setField(xField, x);
        // classTester.setField(yField, y);
        // mt.invoke(char distance, double x, double y);
        // classTester.assertFieldEquals(degreeOfHungerField, degreeOfHunger);
        // classTester.assertFieldEquals(xField, x);
        // classTester.assertFieldEquals(yField, x);
        // }
    }

    @Test
    public void testTest() {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();

        assertEquals(assertDoesNotThrow(() -> dynamicType.getConstructor().newInstance().toString()), "Hello World!");
    }
}
