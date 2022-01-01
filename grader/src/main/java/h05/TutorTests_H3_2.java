package h05;

import h05.ReflectionUtils.AttributeMatcher;
import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtSwitch;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static h05.H05_Class_Testers.animalCT;
import static h05.H05_Class_Testers.sharkCT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h05")
@DisplayName("H3.2")
public class TutorTests_H3_2 {

    final String class_name = "Shark";

    @Test
    @DisplayName("1 | Existenz Klasse " + class_name)
    @SuppressWarnings("unchecked")
    public void t01() {
        sharkCT.setSuperClass((Class<Object>) animalCT.assureClassResolved().getTheClass());
        sharkCT.verify(1.0d);
    }

    @Test
    @DisplayName("2 | Attribute specificSpecies, x, y, degreeOfHunger + Getter")
    public void t02() {
        sharkCT.resolve();
        for (var fieldMatcher : new AttributeMatcher[]{
            new AttributeMatcher("specificSpecies", 0.8, Modifier.PRIVATE, short.class),
            new AttributeMatcher("x", 1.0, Modifier.PRIVATE, int.class),
            new AttributeMatcher("y", 1.0, Modifier.PRIVATE, int.class),
            new AttributeMatcher("degreeOfHunger", 0.8, Modifier.PRIVATE, int.class)
        }) {
            Field theField = sharkCT.resolveAttribute(fieldMatcher);
            sharkCT.assertHasGetter(theField);
        }
    }

    @Test
    @DisplayName("3 | canLiveInSaltWater")
    public void t03() {
        sharkCT.resolve();
        Field specificSpeciesField = sharkCT
            .resolveAttribute(new AttributeMatcher("specificSpecies", 0.8, Modifier.PRIVATE,
                int.class));
        MethodTester mt = new MethodTester(
            sharkCT, "canLiveInSaltWater", 0.8, Modifier.PUBLIC,
            boolean.class);
        mt.resolveMethod();
        for (int i = -20; i <= 20; i++) {
            var specificSpecies = i;
            sharkCT.setField(specificSpeciesField, (short) specificSpecies);
            mt.assertReturnValueEquals(IntStream.of(2, 5, 9).anyMatch(x -> x == specificSpecies),
                "bei " + specificSpeciesField.getName() + "==" + i);
            sharkCT.assertFieldEquals(specificSpeciesField, (short) specificSpecies);
        }
    }

    @Test
    @DisplayName("4 | canLiveInFreshWater")
    public void t04() {
        sharkCT.resolve();
        Field specificSpeciesField = sharkCT.resolveAttribute(
            new AttributeMatcher("specificSpecies", 0.8, Modifier.PRIVATE, int.class));
        MethodTester mt = new MethodTester(
            sharkCT, "canLiveInFreshWater", 0.8, Modifier.PUBLIC,
            boolean.class);
        mt.resolveMethod();
        for (int i = -20; i <= 20; i++) {
            var specificSpecies = i;
            sharkCT.setField(specificSpeciesField, (short) specificSpecies);
            mt.assertReturnValueEquals(
                Math.abs(specificSpecies % 4) == 2,
                "bei " + specificSpeciesField.getName() + "==" + i);
            sharkCT.assertFieldEquals(specificSpeciesField, (short) specificSpecies);
        }
    }

    @Test
    @DisplayName("5 | letMeSwim mit Hungerreduktion")
    public void t05() {
        sharkCT.resolve();
        Field degreeOfHungerField = sharkCT
            .resolveAttribute(new AttributeMatcher("degreeOfHunger", 0.8,
                Modifier.PRIVATE, int.class));
        Field xField = sharkCT
            .resolveAttribute(new AttributeMatcher("x", 1.0, int.class));
        Field yField = sharkCT
            .resolveAttribute(new AttributeMatcher("y", 1.0, int.class));
        MethodTester mt = new MethodTester(
            sharkCT, "letMeSwim", 0.8,
            Modifier.PUBLIC, void.class,
            new ArrayList<>(List.of(
                new ParameterMatcher("distance", 0.8, char.class),
                new ParameterMatcher("x", 1.0, double.class),
                new ParameterMatcher("y", 1.0, double.class)))).verify();
        for (int i = 0; i <= 20; i++) {
            int degreeOfHunger = ThreadLocalRandom.current().nextInt(-2000, 2000);
            int x = ThreadLocalRandom.current().nextInt(-2000, 2000);
            double xParam = ThreadLocalRandom.current().nextDouble(-2000, 2000);
            int y = ThreadLocalRandom.current().nextInt(-2000, 2000);
            double yParam = ThreadLocalRandom.current().nextDouble(-2000, 2000);
            char distanceParam = (char) ThreadLocalRandom.current().nextInt(-2000, 2000);
            sharkCT.setField(degreeOfHungerField, degreeOfHunger);
            sharkCT.setField(xField, x);
            sharkCT.setField(yField, y);
            mt.invoke(distanceParam, xParam, yParam);
            String attributesBefore = String.format(
                "Attribute von Shark vor Aufruf: degreeOfHunger = %s, x = %s, y = %s",
                degreeOfHunger, x, y);
            String params = String.format("Parameter: distance = %s, x = %s, y = %s", distanceParam, xParam,
                yParam);

            sharkCT.assertFieldEquals(degreeOfHungerField, degreeOfHunger + 1,
                attributesBefore + "\n" + params);
            sharkCT.assertFieldEquals(xField, (int) (x + xParam * distanceParam),
                attributesBefore + "\n" + params);
            sharkCT.assertFieldEquals(yField, (int) (y + yParam * distanceParam),
                attributesBefore + "\n" + params);
        }
    }

    @JagrOnlyTest
    @DisplayName("6 | letMeMove mit Hungerreduktion")
    public void t06() {
        // Aufgrund eines Fehlers in der erstveröffentlichung müssen wir alle Modifier
        // akzeptieren.
        MethodTester mt = new MethodTester(
            sharkCT.resolve()
                .assureSpied(),
            "letMeMove", 0.8,
            -1, String.class).verify();
        ThreadLocalRandomTester.initialize();
        mt.invoke();
        assertEquals(1, mt.getInvocationCount());
        var usedRanges = ThreadLocalRandomTester.current().getUsedRanges();
        assertEquals(usedRanges.size(), 3);
        usedRanges.forEach(r -> {
            assertEquals(1, r.lowerEndpoint());
            assertEquals(6, r.upperEndpoint());
        });
        ThreadLocalRandomTester.removeCurrentTester();
    }

    @Test
    @DisplayName("7 | setSpecificSpecies - logik")
    public void t07() {
        sharkCT.assureClassResolved().resolveRealInstance();
        MethodTester mt = new MethodTester(sharkCT, "setSpecificSpecies", 0.8,
            Modifier.PUBLIC, short.class,
            new ArrayList<>(List.of(new ParameterMatcher("specificSpecies", 0.8, short.class))))
            .verify();
        Field specificSpeciesField = sharkCT
            .resolveAttribute(new AttributeMatcher("specificSpecies", 0.8, Modifier.PRIVATE,
                short.class));
        for (int i = -20; i <= 20; i++) {
            var oldSpecSpecies = sharkCT.setFieldRandom(specificSpeciesField);
            var expectedSpecificSpecies = (short) Math.max(0, Math.min(i, 10));
            mt.assertReturnValueEquals(oldSpecSpecies, (short) i);
            sharkCT.assertFieldEquals(specificSpeciesField,
                (short) Math.max(0, Math.min(expectedSpecificSpecies, 10)),
                "Parameter des Aufrufs von setSpecificSpecies: " + i);
        }
    }

    @JagrOnlyTest
    @DisplayName("7 | setSpecificSpecies - verbindliche Anforderung")
    public void t07_syntax() {
        // TODO: Fix Test
        new MethodTester(sharkCT, "setSpecificSpecies", 0.8,
            Modifier.PUBLIC, short.class,
            new ArrayList<>(List.of(new ParameterMatcher("specificSpecies", 0.8, short.class))))
            .assertConstructsNotUsed(List.of(
                CtConditional.class));
    }

    @Test
    @DisplayName("8 | Konstruktor")
    @SuppressWarnings("unchecked")
    public void t08() {
        var constructor = (Constructor<Object>) sharkCT.assureClassResolved()
            .resolveConstructor(new ParameterMatcher("specificSpecies", 0.8, short.class));
        ((ClassTester<Object>) sharkCT).assertConstructorValid(constructor, Modifier.PUBLIC,
            new ParameterMatcher("specificSpecies", 0.8, short.class));

        var enumClassTester = new ClassTester<>("h05", "AnimalType", 0.8).resolveClass();
        Field animalTypeField = sharkCT.resolveAttribute(
            new AttributeMatcher("animalType", 0.8, -1, enumClassTester.getClass(),
                true));
        Field degreeOfHungerField = sharkCT
            .resolveAttribute(new AttributeMatcher("degreeOfHunger", 0.8,
                Modifier.PRIVATE, int.class));
        Field specificSpeciesField = sharkCT
            .resolveAttribute(new AttributeMatcher("specificSpecies", 0.8, Modifier.PRIVATE,
                short.class));
        // Valid Value
        ((ClassTester<Object>) sharkCT).setClassInstance(assertDoesNotThrow(() -> constructor.newInstance((short) 5)));
        sharkCT.assertFieldEquals(animalTypeField, enumClassTester.getEnumValue("CHONDRICHTHYES", 0.8));
        sharkCT.assertFieldEquals(degreeOfHungerField, 10);
        sharkCT.assertFieldEquals(specificSpeciesField, (short) 5);
        // Smaller Value
        ((ClassTester<Object>) sharkCT)
            .setClassInstance(assertDoesNotThrow(() -> constructor.newInstance((short) -42)));
        sharkCT.assertFieldEquals(animalTypeField, enumClassTester.getEnumValue("CHONDRICHTHYES", 0.8));
        sharkCT.assertFieldEquals(degreeOfHungerField, 10);
        sharkCT.assertFieldEquals(specificSpeciesField, (short) 0);
        // Bigger Value
        ((ClassTester<Object>) sharkCT).setClassInstance(assertDoesNotThrow(() -> constructor.newInstance((short) 69)));
        sharkCT.assertFieldEquals(animalTypeField, enumClassTester.getEnumValue("CHONDRICHTHYES", 0.8));
        sharkCT.assertFieldEquals(degreeOfHungerField, 10);
        sharkCT.assertFieldEquals(specificSpeciesField, (short) 10);
    }

    @Test
    @DisplayName("9 | accept - logik")
    public void t09() {
        // degreeOfHunger -= reductionOfHunger > 0 ? reductionOfHunger : 0;
        sharkCT.assureClassResolved().resolveRealInstance();
        MethodTester mt = new MethodTester(sharkCT, "accept", 0.8,
            Modifier.PUBLIC, void.class,
            new ArrayList<>(List.of(new ParameterMatcher("reductionOfHunger", 0.8, int.class)))).verify();
        Field degreeOfHungerField = sharkCT
            .resolveAttribute(new AttributeMatcher("degreeOfHunger", 0.8, -1,
                int.class));
        sharkCT.setField(degreeOfHungerField, 1000);
        var expectedDOH = 1000;
        for (int i = -20; i <= 20; i++) {
            mt.invoke(i);
            expectedDOH -= i > 0 ? i : 0;
            sharkCT.assertFieldEquals(degreeOfHungerField, expectedDOH);
        }
    }

    @JagrOnlyTest
    @DisplayName("9 | accept - Verbindliche Anforderung")
    public void t09_syntax() {
        // TODO: Fix Test
        new MethodTester(sharkCT, "accept", 0.8,
            Modifier.PUBLIC, void.class,
            new ArrayList<>(List.of(new ParameterMatcher("reductionOfHunger", 0.8, int.class))))
            .assertConstructsNotUsed(List.of(
                CtIf.class,
                CtSwitch.class));
    }
}
