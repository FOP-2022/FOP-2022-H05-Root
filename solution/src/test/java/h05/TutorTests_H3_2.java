package h05;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.IdentifierMatcher;
import h05.ReflectionUtils.MethodTester;

@DisplayName("H3.2")
public class TutorTests_H3_2 {

    final String class_name = "Shark";

    @Test
    @DisplayName("1 | Existenz Klasse " + class_name)
    public void t01() {
        var animalClassTester = new ClassTester<>("h05", "Animal", 0.8).resolveClass();
        new ClassTester<>("h05", class_name, 1.0, Modifier.PUBLIC,
                animalClassTester.getTheClass(),
                new ArrayList<>(List.of(new IdentifierMatcher("Swimming", "h05", 0.8),
                        new IdentifierMatcher("IntConsumer", "java.util.function.IntConsumer", 1.0)))).verify();
    }

    @Test
    @DisplayName("2 | Attribute specificSpecies, x, y, degreeOfHunger + Getter")
    public void t02() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolve();
        for (var fieldMatcher : new AttributeMatcher[] {
                new AttributeMatcher("specificSpecies", 0.8, Modifier.PRIVATE, short.class),
                new AttributeMatcher("x", 1.0, Modifier.PRIVATE, int.class),
                new AttributeMatcher("y", 1.0, Modifier.PRIVATE, int.class),
                new AttributeMatcher("degreeOfHunger", 0.8, Modifier.PRIVATE, int.class)
        }) {
            Field theField = classTester.resolveAttribute(fieldMatcher);
            classTester.assertHasGetter(theField);
        }
    }

    @Test
    @DisplayName("3 | canLiveInSaltWater")
    public void t03() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolve();
        Field specificSpeciesField = classTester
                .resolveAttribute(new AttributeMatcher("specificSpecies", 0.8, Modifier.PRIVATE, int.class));
        MethodTester mt = new MethodTester(classTester, "canLiveInSaltWater", 0.8, Modifier.PUBLIC, boolean.class);
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
    @DisplayName("4 | canLiveInFreshWater")
    public void t04() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolve();
        Field specificSpeciesField = classTester.resolveAttribute(
                new AttributeMatcher("specificSpecies", 0.8, Modifier.PRIVATE, int.class));
        MethodTester mt = new MethodTester(classTester, "canLiveInFreshWater", 0.8, Modifier.PUBLIC, boolean.class);
        mt.resolveMethod();
        for (int i = -20; i <= 20; i++) {
            var specificSpecies = i;
            classTester.setField(specificSpeciesField, (short) specificSpecies);
            mt.assertReturnValueEquals(
                    Math.abs(specificSpecies % 4) == 2,
                    "bei " + specificSpeciesField.getName() + "==" + i);
            classTester.assertFieldEquals(specificSpeciesField, (short) specificSpecies);
        }
    }

    @Test
    @DisplayName("5 | letMeSwim mit Hungerreduktion")
    public void t05() {
        var classTester = new ClassTester<>("h05", class_name, 0.8).resolve();
        classTester.findClass();
        classTester.resolveInstance();
        Field degreeOfHungerField = classTester
                .resolveAttribute(new AttributeMatcher("degreeOfHunger", 0.8,
                        Modifier.PRIVATE, int.class));
        Field xField = classTester
                .resolveAttribute(new AttributeMatcher("x", 1.0, int.class));
        Field yField = classTester
                .resolveAttribute(new AttributeMatcher("y", 1.0, int.class));
        MethodTester mt = new MethodTester(classTester, "letMeSwim", 0.8,
                Modifier.PUBLIC, boolean.class);
        mt.resolveMethod();
        for (int i = 0; i <= 20; i++) {
            int degreeOfHunger = ThreadLocalRandom.current().nextInt(-2000, 2000);
            int x = ThreadLocalRandom.current().nextInt(-2000, 2000);
            double xParam = ThreadLocalRandom.current().nextDouble(-2000, 2000);
            int y = ThreadLocalRandom.current().nextInt(-2000, 2000);
            double yParam = ThreadLocalRandom.current().nextDouble(-2000, 2000);
            char distanceParam = (char) ThreadLocalRandom.current().nextInt(-2000, 2000);
            classTester.setField(degreeOfHungerField, degreeOfHunger);
            classTester.setField(xField, x);
            classTester.setField(yField, y);
            mt.invoke(distanceParam, xParam, yParam);
            String attributesBefore = String.format(
                    "Attribute von Shark vor Aufruf: degreeOfHunger = %s, x = %s, y = %s", degreeOfHunger, x, y);
            String params = String.format("Parameter: distance = %s, x = %s, y = %s", distanceParam, xParam, yParam);

            classTester.assertFieldEquals(degreeOfHungerField, degreeOfHunger + 1, attributesBefore + "\n" + params);
            classTester.assertFieldEquals(xField, (int) (x + xParam * distanceParam), attributesBefore + "\n" + params);
            classTester.assertFieldEquals(yField, (int) (y + yParam * distanceParam), attributesBefore + "\n" + params);
        }
    }

    @Test
    @DisplayName("6 | letMeMove mit Hungerreduktion")
    public void t06() {

    }

    @Test
    @DisplayName("7 | setSpecificSpecies")
    public void t07() {

    }

    @Test
    @DisplayName("8 | Konstruktor")
    public void t08() {

    }
}
