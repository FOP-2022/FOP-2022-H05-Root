package h05;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import static java.lang.reflect.Modifier.*;
import java.util.stream.Stream;

public class TestUtils {
    /**
     * Asserts that a given field has a getter method
     *
     * @param field the Field to check
     */
    public static void assertHasGetter(Field field, Object clazz_instance, Object testValue) {
        // Existance
        var clazz = clazz_instance.getClass();
        var fieldName = field.getName();
        System.out.println(fieldName);
        var getter_name = String.format("get%s%s", fieldName.substring(0, 1).toUpperCase(), fieldName.substring(1));
        var method = assertDoesNotThrow(() -> Stream.of(clazz.getDeclaredMethods())
                .filter(x -> x.getName().equals(getter_name)).findFirst().orElseThrow(),
                String.format("Getter existiert nicht: %s.", getter_name));

        // Access Modifier
        var mod = method.getModifiers();
        var expected_mod = 1; // public
        assertEquals(expected_mod, mod, String.format("Falscher Access Modifier. Gefordert: %s, Erhalten:%s",
                Modifier.toString(expected_mod), Modifier.toString(mod)));

        // Parameter
        assertEquals(0, method.getParameterCount(), "Falsche Parameteranzahl.");

        // Return Type
        assertEquals(field.getType(), method.getReturnType());

        // Return Value
        field.setAccessible(true);
        assertDoesNotThrow(() -> field.set(clazz_instance, testValue));
        assertEquals(testValue, assertDoesNotThrow(() -> method.invoke(clazz_instance)));
    }

    /**
     * Asserts matching Modifiers
     *
     * @param expected Erwarteter Wert
     * @param actual   Eigentlicher Wert
     * @param name     Feld Name
     */
    public static void assertModifier(int expected, int actual, String name) {
        assertEquals(expected, actual,
                String.format("Falsche Modifiers für %s! Gefordert: %s Erhalten: %s", name, expected, actual));
    }

    public static void assertModifier(int expected, Class<?> clazz) {
        assertEquals(expected, clazz.getModifiers(), "Klasse " + clazz.getName());
    }

    public static void assertModifier(int expected, Method method) {
        assertEquals(expected, method.getModifiers(), "Methode " + method.getDeclaringClass() + "." + method.getName());
    }
}
