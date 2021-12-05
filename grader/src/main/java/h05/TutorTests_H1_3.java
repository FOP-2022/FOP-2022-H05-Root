package h05;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;

import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.IdentifierMatcher;
import h05.ReflectionUtils.MethodTester;

@TestForSubmission("h05")
@DisplayName("H1_3")
public class TutorTests_H1_3 {

    final String interface_name = "Amphibean";

    @Test
    @ExtendWith(TestCycleResolver.class)
    @DisplayName("1 | Existenz Interface " + interface_name)
    public void t01(TestCycle testCycle) {
        ClassTester.cycle = testCycle;
        new ClassTester<>("h05", interface_name, 1.0,
                Modifier.PUBLIC | Modifier.ABSTRACT | Modifier.INTERFACE, null,
                new ArrayList<>(List.of(new IdentifierMatcher("Walking", "h05", 0.8),
                        new IdentifierMatcher("Swimming", "h05", 0.8)))).verify();
    }

    @Test
    @ExtendWith(TestCycleResolver.class)
    @DisplayName("2 | Enum EnvironmentType()")
    public void t02(TestCycle testCycle) {
        ClassTester.cycle = testCycle;
        new ClassTester<>("h05", "EnvironmentType", 0.8,
                Modifier.PUBLIC | Modifier.FINAL | TestUtils.ENUM)
                        .verify()
                        .assertEnumConstants(new String[] { "ON_SHORE", "IN_WATER" });
    }

    @Test
    @ExtendWith(TestCycleResolver.class)
    @DisplayName("3 | Methode getPreferredEnvironment()")
    public void t03(TestCycle testCycle) {
        ClassTester.cycle = testCycle;
        var classTester = new ClassTester<>("h05", interface_name, 0.8);
        var environmentTypeClass = new ClassTester<>("h05", "EnvironmentType", 0.8).findClass();
        new MethodTester(classTester, "getPreferredEnvironment", 0.8,
                Modifier.PUBLIC | Modifier.ABSTRACT, environmentTypeClass, new ArrayList<>()).verify();
    }
}
