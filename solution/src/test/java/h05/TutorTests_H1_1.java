package h05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("H1_1")
public class TutorTests_H1_1 {

    final String interface_name = "Walking";

    // TODO: Finding Class Names based on similarity matching, resulting only in
    // small point loss
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
    }

    @Test
    @DisplayName("2 | Methode getNumberOfLegs()")
    public void t02() {
        // Existance
        var clazz = assertDoesNotThrow(() -> Class.forName(String.format("h05.%s", interface_name)));
        var method = assertDoesNotThrow(() -> Stream.of(clazz.getDeclaredMethods())
                .filter(x -> x.getName().equals("getNumberOfLegs")).findFirst().orElseThrow(),
                "Methode existiert nicht.");

        // Access Modifier
        var mod = method.getModifiers();
        var expected_mod = 1025; // public abstract
        assertEquals(expected_mod, mod, String.format("Falscher Access Modifier. Gefordert: %s, Erhalten:%s",
                Modifier.toString(expected_mod), Modifier.toString(mod)));

        // Parameter
        assertEquals(0, method.getParameterCount(), "Falsche Parameteranzahl.");

        // Return Type
        assertEquals(byte.class, method.getReturnType());
    }

    @Test
    @DisplayName("3 | Methode getAverageSpeed()")
    public void t03() {
        // Existance
        var clazz = assertDoesNotThrow(() -> Class.forName(String.format("h05.%s", interface_name)));
        var method = assertDoesNotThrow(() -> Stream.of(clazz.getDeclaredMethods())
                .filter(x -> x.getName().equals("getAverageSpeed")).findFirst().orElseThrow(),
                "Methode existiert nicht.");

        // Access Modifier
        var mod = method.getModifiers();
        var expected_mod = 1025; // public abstract
        assertEquals(expected_mod, mod, String.format("Falscher Access Modifier. Gefordert: %s, Erhalten:%s",
                Modifier.toString(expected_mod), Modifier.toString(mod)));

        // Parameter
        var params = method.getParameters();
        assertEquals(1, params.length, "Falsche Parameteranzahl.");

        var param0 = params[0]; // double distance
        assertEquals(double.class, param0.getType(), "Falscher Parametertyp.");
        if (param0.isNamePresent() && !param0.getName().equals("distance")) {
            System.out.format("falscher Parametername: Gefordert: %s, Erhalten: %s", "distance", param0.getName());
        }

        // Return Type
        assertEquals(double.class, method.getReturnType());
    }
}
