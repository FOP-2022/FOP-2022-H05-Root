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
import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;

@TestForSubmission("h05")
@DisplayName("H1_2")
public class TutorTests_H1_2 {

    final String interface_name = "Swimming";

    @Test
    @ExtendWith(TestCycleResolver.class)
    @DisplayName("1 | Existenz Interface " + interface_name)
    public void t01(TestCycle testCycle) {
        ClassTester.cycle = testCycle;
        new ClassTester<>("h05", interface_name, 1.0,
                Modifier.PUBLIC | Modifier.ABSTRACT | Modifier.INTERFACE).verify();
    }

    @Test
    @ExtendWith(TestCycleResolver.class)
    @DisplayName("2 | Methode canLiveInSaltWater()")
    public void t02(TestCycle testCycle) {
        ClassTester.cycle = testCycle;

        var classTester = new ClassTester<>("h05", interface_name, 0.8);
        new MethodTester(classTester, "canLiveInSaltWater", 0.8, Modifier.PUBLIC | Modifier.ABSTRACT,
                boolean.class, new ArrayList<>()).verify();
    }

    @Test
    @ExtendWith(TestCycleResolver.class)
    @DisplayName("3 | Methode canLiveInFreshWater()")
    public void t03(TestCycle testCycle) {
        ClassTester.cycle = testCycle;
        var classTester = new ClassTester<>("h05", interface_name, 0.8);
        new MethodTester(classTester, "canLiveInFreshWater", 0.8,
                Modifier.PUBLIC | Modifier.ABSTRACT, boolean.class, new ArrayList<>()).verify();
    }

    @Test
    @ExtendWith(TestCycleResolver.class)
    @DisplayName("4 | Methode letMeSwim()")
    public void t04(TestCycle testCycle) {
        ClassTester.cycle = testCycle;
        var classTester = new ClassTester<>("h05", interface_name, 0.8);
        new MethodTester(classTester, "letMeSwim", 0.8, Modifier.PUBLIC | Modifier.ABSTRACT,
                void.class, new ArrayList<>(List.of(new ParameterMatcher("distance", 0.8, char.class),
                        new ParameterMatcher("x", 1, double.class), new ParameterMatcher("y", 1, double.class))))
                                .verify();
    }
}
