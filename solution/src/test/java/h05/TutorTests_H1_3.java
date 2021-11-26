package h05;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.IdentifierMatcher;
import h05.ReflectionUtils.MethodTester;

@DisplayName("H1_3")
public class TutorTests_H1_3 {

    final String interface_name = "Amphibean";

    @Test
    @DisplayName("1 | Existenz Interface " + interface_name)
    public void t01() {
        var classTester = new ClassTester<>("h05", interface_name, 1.0,
                Modifier.PUBLIC | Modifier.ABSTRACT | Modifier.INTERFACE, null,
                new ArrayList<>(List.of(new IdentifierMatcher("Walking", "h05", 0.8),
                        new IdentifierMatcher("Swimming", "h05", 0.8))));
        classTester.resolveClass();
        classTester.assertIsInterface();
        classTester.assertAccessModifier();
        classTester.assertImplementsInterfaces();
    }

    @Test
    @DisplayName("2 | Enum EnvironmentType()")
    public void t02() {
        var classTester = new ClassTester<>("h05", "EnvironmentType", 0.8,
                Modifier.PUBLIC | Modifier.FINAL | TestUtils.ENUM);
        classTester.resolveClass();
        classTester.assertIsEnum();
        classTester.assertAccessModifier();
        classTester.assertDoesNotImplementAnyInterfaces();
        classTester.assertEnumConstants(new String[] { "ON_SHORE", "IN_WATER" });
    }

    @Test
    @DisplayName("3 | Methode getPreferredEnvironment()")
    public void t03() {
        var classTester = new ClassTester<>("h05", interface_name, 0.8);
        var environmentTypeClass = new ClassTester<>("h05", "EnvironmentType", 0.8).resolveClass();
        var methodTester = new MethodTester(classTester, "getPreferredEnvironment", 0.8,
                Modifier.PUBLIC | Modifier.ABSTRACT, environmentTypeClass, new ArrayList<>());
        var method = methodTester.resolveMethod();
        System.out.println(method);
        methodTester.assertAccessModifier();
        methodTester.assertParametersMatch();
        methodTester.assertReturnType();
    }
}
