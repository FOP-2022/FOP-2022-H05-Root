package h05;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static java.lang.reflect.Modifier.*;

import java.util.ArrayList;
import java.util.List;
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

    // public static Modifier[] toModifierArray(int modifier) {
    // var modifiers = new ArrayList<Modifier>();
    // for(var currMod : new Modifier[]{Modifier.PUBLIC}){
    // if((modifier & currMod) != 0){

    // }
    // }
    // if ((mod & PUBLIC) != 0)
    // sj.add("public");
    // if ((mod & PROTECTED) != 0)
    // sj.add("protected");
    // if ((mod & PRIVATE) != 0)
    // sj.add("private");

    // /* Canonical order */
    // if ((mod & ABSTRACT) != 0)
    // sj.add("abstract");
    // if ((mod & STATIC) != 0)
    // sj.add("static");
    // if ((mod & FINAL) != 0)
    // sj.add("final");
    // if ((mod & TRANSIENT) != 0)
    // sj.add("transient");
    // if ((mod & VOLATILE) != 0)
    // sj.add("volatile");
    // if ((mod & SYNCHRONIZED) != 0)
    // sj.add("synchronized");
    // if ((mod & NATIVE) != 0)
    // sj.add("native");
    // if ((mod & STRICT) != 0)
    // sj.add("strictfp");
    // if ((mod & INTERFACE) != 0)
    // sj.add("interface");
    // }

    /**
     * Calculates the similarity (a number within 0 and 1) between two strings.
     */
    public static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) {
            longer = s2;
            shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
            /* both strings are zero length */ }
        /*
         * // If you have Apache Commons Text, you can use it to calculate the edit
         * distance: LevenshteinDistance levenshteinDistance = new
         * LevenshteinDistance(); return (longerLength -
         * levenshteinDistance.apply(longer, shorter)) / (double) longerLength;
         */
        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }

    // Example implementation of the Levenshtein Edit Distance
    // See http://rosettacode.org/wiki/Levenshtein_distance#Java
    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }

    public static Class<?> findClass(String name, double maxSimilarity, int modifier, Class<?> superClass,
            Class<?>[] implementInterfaces) {
        return null;
    }

    public static Class<?> findClass(String name, int maxSimilarity) {
        return findClass(name, maxSimilarity, -1, null, null);
    }

    /**
     * Scans all classes accessible from the context class loader which belong to
     * the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     */
    public static Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        var resources = classLoader.getResources(path);
        var dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            var resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        var classes = new ArrayList<Class<?>>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base
     *                    directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    public static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
        var classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(
                        Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}
