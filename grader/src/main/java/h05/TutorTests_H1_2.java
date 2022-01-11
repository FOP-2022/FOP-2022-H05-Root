package h05;

import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static h05.H05_Class_Testers.swimmingCT;

@TestForSubmission("h05")
@DisplayName("H1_2")
public class TutorTests_H1_2 {

    final String interface_name = "Swimming";

    @Test
    @DisplayName("1 | Existenz Interface " + interface_name)
    public void t01() {
        swimmingCT.verify(1.0d);
    }

    @Test
    @DisplayName("2 | Methode canLiveInSaltWater()")
    public void t02() {
        new MethodTester(swimmingCT.assureClassResolved(), "canLiveInSaltWater", 0.8,
            Modifier.PUBLIC | Modifier.ABSTRACT,
            boolean.class, new ArrayList<>()).verify();
    }

    @Test
    @DisplayName("3 | Methode canLiveInFreshWater()")
    public void t03() {
        new MethodTester(swimmingCT
            .assureClassResolved(), "canLiveInFreshWater", 0.8,
            Modifier.PUBLIC | Modifier.ABSTRACT, boolean.class, new ArrayList<>()).verify();
    }

    @Test
    @DisplayName("4 | Methode letMeSwim()")
    public void t04() {
        new MethodTester(swimmingCT
            .assureClassResolved(), "letMeSwim", 0.8, Modifier.PUBLIC | Modifier.ABSTRACT,
            void.class, new ArrayList<>(List.of(new ParameterMatcher("distance", 0.8, char.class),
            new ParameterMatcher("x", 1, double.class), new ParameterMatcher("y", 1, double.class))))
            .verify();
    }
}
