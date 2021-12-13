package h05;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;

import h05.ReflectionUtils.MethodTester;
import h05.ReflectionUtils.ParameterMatcher;
import static h05.H05_Class_Testers.*;

@TestForSubmission("h05")
@DisplayName("H1_1")
public class TutorTests_H1_1 {

    final String interface_name = "Walking";

    @Test
    @DisplayName("1 | Existenz Interface " + interface_name)
    public void t01() {
        walkingCT.verify(1.0d);
    }

    @Test
    @DisplayName("2 | Methode getNumberOfLegs()")
    public void t02() {
        new MethodTester(walkingCT.assureClassResolved(), "getNumberOfLegs", 0.8, Modifier.PUBLIC | Modifier.ABSTRACT,
                byte.class, new ArrayList<>()).verify();
    }

    @Test
    @DisplayName("3 | Methode getAverageSpeed()")
    public void t03() {
        new MethodTester(walkingCT
                .assureClassResolved(), "getAverageSpeed", 0.8, Modifier.PUBLIC | Modifier.ABSTRACT,
                double.class, new ArrayList<>(List.of(new ParameterMatcher("distance", 0.8, double.class))))
                        .verify();
    }
}
