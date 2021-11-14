package h05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("H1_3")
public class TutorTests_H1_3 {

    final String interface_name = "Amphibean";

    @Test
    @DisplayName("1 | Existenz Interface " + interface_name)
    public void t01() {
        // Existance
        var clazz = assertDoesNotThrow(() -> Class.forName(String.format("h05.%s", interface_name)),
                String.format("Interface %s existiert nicht.", interface_name));

        // Access Modifier
        var mod = clazz.getModifiers();
        var expected_mod = 1537; // public abstract interface
        assertTrue(Modifier.isInterface(mod), String.format("%s ist kein Interface.", interface_name));
        assertEquals(expected_mod, mod, String.format("Falscher Access Modifier. Gefordert: %s, Erhalten:%s",
                Modifier.toString(expected_mod), Modifier.toString(mod)));

        // Extends
        var interfaces = clazz.getInterfaces();
        assertTrue(Stream.of(interfaces).anyMatch(x -> x.getName().equals("h05.Walking")),
                "Interface Walking wird nicht erweitert.");
        assertTrue(Stream.of(interfaces).anyMatch(x -> x.getName().equals("h05.Swimming")),
                "Interface Swimming wird nicht erweitert.");
        // assertEquals(2, interfaces.length);
    }

    @Test
    @DisplayName("2 | Enum Environment()")
    @SuppressWarnings("unchecked")
    public void t02() {
        // Existance
        var clazz = assertDoesNotThrow(() -> Class.forName("h05.Environment"), "Interface Swimming existiert nicht.");

        // Access Modifier
        var mod = clazz.getModifiers();
        var expected_mod = 16401; // public abstract interface
        assertTrue(clazz.isEnum(), String.format("%s ist kein Interface.", interface_name));
        assertEquals(expected_mod, mod, String.format("Falscher Access Modifier. Gefordert: %s, Erhalten:%s",
                Modifier.toString(expected_mod), Modifier.toString(mod)));

        // Values
        var enum_class = (Class<Enum<?>>) clazz;
        var enum_values = enum_class.getEnumConstants();
        var expected_names = new String[] { "ON_SHORE", "IN_WATER" };
        for (String n : expected_names) {
            assertTrue(Stream.of(enum_values).anyMatch(x -> x.toString().equals(n)),
                    String.format("Enum-Konstante %s fehlt.", n));
        }
    }

    // @Test
    // @DisplayName("3 | Methode getPreferredEnvironment()")
    // public void t03() {
    //     // Existance
    //     var clazz = assertDoesNotThrow(() -> Class.forName(String.format("h05.%s", interface_name)));
    //     var method = assertDoesNotThrow(() -> Stream.of(clazz.getDeclaredMethods())
    //             .filter(x -> x.getName().equals("getPreferredEnvironment")).findFirst().orElseThrow(),
    //             "Methode existiert nicht.");

    //     // Access Modifier
    //     var mod = method.getModifiers();
    //     var expected_mod = 1025; // public abstract
    //     assertEquals(expected_mod, mod, String.format("Falscher Access Modifier. Gefordert: %s, Erhalten:%s",
    //             Modifier.toString(expected_mod), Modifier.toString(mod)));

    //     // Parameter
    //     assertEquals(0, method.getParameterCount(), "Falsche Parameteranzahl.");

    //     // Return Type
    //     var environment_clazz = assertDoesNotThrow(() -> Class.forName("h05.Environment"),
    //             "Interface Swimming existiert nicht.");
    //     assertEquals(environment_clazz, method.getReturnType());
    // }
}
