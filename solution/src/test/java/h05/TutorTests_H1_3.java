package h05;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.IdentifierMatcher;
import h05.ReflectionUtils.MethodTester;
import static h05.H05_Class_Testers.*;

@TestForSubmission("h05")
@DisplayName("H1_3")
public class TutorTests_H1_3 {

    final String interface_name = "Amphibean";

    @Test
    @DisplayName("1 | Existenz Interface " + interface_name)
    public void t01() {
        amphibeanCT.verify(1.0d);
    }

    @Test
    @DisplayName("1 | Existenz Interface " + interface_name)
    public void t01_alt1() {
        new ClassTester<>("h05", "Amphibian", 1.0,
                Modifier.PUBLIC | Modifier.ABSTRACT | Modifier.INTERFACE, null,
                new ArrayList<>(List.of(new IdentifierMatcher("Walking", "h05", minSim),
                        new IdentifierMatcher("Swimming", "h05", minSim)))).verify(1.0d);
    }

    @Test
    @DisplayName("2 | Enum EnvironmentType()")
    public void t02() {
        environmentTypeCT
                .verify(1.0d)
                .assertEnumConstants(new String[] { "ON_SHORE", "IN_WATER" });
    }

    @Test
    @DisplayName("3 | Methode getPreferredEnvironment()")
    public void t03() {
        new MethodTester(amphibeanCT.assureClassResolved(), "getPreferredEnvironment", 0.8,
                Modifier.PUBLIC | Modifier.ABSTRACT, environmentTypeCT.assureClassResolved().getTheClass(),
                new ArrayList<>()).verify();
    }
}
