package h05.ReflectionUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import h05.TestUtils;

/**
 * A Class Tester
 *
 * @author Ruben Deisenroth
 */
public class ClassTester<T> {
    IdentifierMatcher classIdentifier;
    Class<T> theClass;
    int accessModifier;
    private Class<? super T> superClass;
    private ArrayList<IdentifierMatcher> implementsInterfaces;

    T classInstance;

    public ClassTester(String packageName, String className, double similarity, int accessModifier,
            Class<? super T> superClass, ArrayList<IdentifierMatcher> implementsInterfaces, T classInstance) {
        this.classIdentifier = new IdentifierMatcher(className, packageName, similarity);
        this.accessModifier = accessModifier;
        this.superClass = superClass;
        this.implementsInterfaces = implementsInterfaces;
        this.classInstance = classInstance;
    }

    public ClassTester(String packageName, String className, double similarity, int accessModifier,
            Class<? super T> superClass, ArrayList<IdentifierMatcher> implementsInterfaces) {
        this(packageName, className, similarity, accessModifier, superClass, implementsInterfaces, null);
    }

    public ClassTester(String packageName, String className, double similarity, int accessModifier) {
        this(packageName, className, similarity, accessModifier, null, new ArrayList<>(), null);
    }

    public ClassTester(String packageName, String className, double similarity) {
        this(packageName, className, similarity, -1, null, new ArrayList<>(), null);
    }

    public ClassTester(String packageName, String className) {
        this(packageName, className, 1, -1, null, new ArrayList<>(), null);
    }

    /**
     * Gets the Implemented Interfaces
     *
     * @return the Implemented Interfaces
     */
    public ArrayList<IdentifierMatcher> getImplementsInterfaces() {
        return implementsInterfaces;
    }

    /**
     * Gets the Super Class
     *
     * @return the super class
     */
    public Class<? super T> getSuperClass() {
        return superClass;
    }

    /**
     * Sets the Implemented Interfaces
     *
     * @param implementsInterfaces the new Implemented Interfaces
     */
    public void setImplementsInterfaces(ArrayList<IdentifierMatcher> implementsInterfaces) {
        this.implementsInterfaces = implementsInterfaces;
    }

    public void addImplementsInterface(IdentifierMatcher interfaceMatcher) {
        if (implementsInterfaces == null) {
            implementsInterfaces = new ArrayList<>();
        }
        implementsInterfaces.add(interfaceMatcher);
    }

    public void addImplementsInterface(String interfaceName, Double similarity) {
        addImplementsInterface(new IdentifierMatcher(interfaceName, similarity));
    }

    public void addImplementsInterface(String interfaceName) {
        addImplementsInterface(interfaceName, null);
    }

    public Field resolveAttribute(AttributeMatcher matcher) {
        assertClassResolved();
        var fields = theClass.getDeclaredFields();
        Field bestMatch = Arrays.stream(fields)
                .sorted((x, y) -> Double.valueOf(TestUtils.similarity(y.getName(), matcher.identifierName))
                        .compareTo(TestUtils.similarity(x.getName(), matcher.identifierName)))
                .findFirst().orElse(null);
        assertNotNull(bestMatch, String.format("Attribut %s existiert nicht.", matcher.identifierName));
        var sim = TestUtils.similarity(bestMatch.getName(), matcher.identifierName);
        assertTrue(sim >= matcher.similarity,
                String.format("Attribut %s existiert nicht. Ähnlichstes Attribut: %s mit Ähnlichkeit: %s",
                        matcher.identifierName, bestMatch, sim));
        if (matcher.modifier >= 0) {
            TestUtils.assertModifier(matcher.modifier, bestMatch);
        }
        return bestMatch;
    }

    public void assertImplementsInterfaces(ArrayList<IdentifierMatcher> implementsInterfaces) {
        assertClassResolved();
        var interfaces = new ArrayList<>(List.of(theClass.getInterfaces()));
        if (implementsInterfaces == null || implementsInterfaces.isEmpty()) {
            assertTrue(interfaces == null || interfaces.isEmpty(), "Es sollen keine Interfaces implementiert werden.");
        } else {
            for (int i = 0; i < implementsInterfaces.size(); i++) {
                var matcher = implementsInterfaces.get(i);
                var bestMatch = interfaces.stream()
                        .sorted((x, y) -> Double
                                .valueOf(TestUtils.similarity(matcher.identifierName, y.getSimpleName()))
                                .compareTo(TestUtils.similarity(matcher.identifierName, x.getSimpleName())))
                        .findFirst().orElseGet(null);
                assertNotNull(bestMatch, getInterfaceNotImplementedMessage(matcher.identifierName));
                var sim = TestUtils.similarity(bestMatch.getSimpleName(), matcher.identifierName);
                assertTrue(sim >= matcher.similarity, getInterfaceNotImplementedMessage(matcher.identifierName)
                        + "Ähnlichstes Interface:" + bestMatch.getSimpleName() + " with " + sim + " similarity.");
                interfaces.remove(bestMatch);
            }
            assertTrue(interfaces.isEmpty(),
                    "Die folgenden Interfaces sollten nicht implementiert werden:" + interfaces.toString());
        }
    }

    public void assertImplementsInterfaces() {
        assertImplementsInterfaces(implementsInterfaces);
    }

    public void assertDoesNotImplementAnyInterfaces() {
        assertImplementsInterfaces(null);
    }

    public boolean class_resolved() {
        return theClass != null;
    }

    public static String getClassNotFoundMessage(String className) {
        return String.format("Klasse %s existiert nicht.", className);
    }

    public String getClassNotFoundMessage() {
        return getClassNotFoundMessage(classIdentifier.identifierName);
    }

    public static String getInterfaceNotImplementedMessage(String interfaceName) {
        return String.format("Interface %s wird nicht erweitert.", interfaceName);
    }

    public static void assertClassNotNull(Class<?> theClass, String className) {
        assertNotNull(theClass, getClassNotFoundMessage(className));
    }

    public void assertClassResolved() {
        assertClassNotNull(theClass, classIdentifier.identifierName);
    }

    /**
     * Sets the Super Class
     *
     * @param superClass the Super Class
     */
    public void setSuperClass(Class<? super T> superClass) {
        this.superClass = superClass;
    }

    /**
     * Gets the Class if Already resolved
     *
     * @return the Class if Already resolved
     */
    public Class<T> getTheClass() {
        return theClass;
    }

    /**
     * Sets the Class
     *
     * @param theClass the new Class
     */
    public void setTheClass(Class<T> theClass) {
        this.theClass = theClass;
    }

    public int getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(int accessModifier) {
        this.accessModifier = accessModifier;
    }

    public void assertAccessModifier() {
        if (accessModifier >= 0) {
            TestUtils.assertModifier(accessModifier, theClass);
        }
    }

    public T getClassInstance() {
        return classInstance;
    }

    public void setClassInstance(T classInstance) {
        this.classInstance = classInstance;
    }

    public boolean classInstanceResolved() {
        return classInstance != null;
    }

    public void assertclassInstanceResolved() {
        assertNotNull(classInstance, "Es wurde keine Klassen-Instanz gefunden.");
    }

    public static String getEnumConstantMissingMessage(String constantName) {
        return String.format("Enum-Konstante %s fehlt.", constantName);
    }

    public void assertEnumConstants(String[] expectedConstants) {
        assertClassResolved();
        var enum_values = theClass.getEnumConstants();
        for (String n : expectedConstants) {
            assertTrue(Stream.of(enum_values).anyMatch(x -> x.toString().equals(n)),
                    String.format("Enum-Konstante %s fehlt.", n));
        }
    }

    public static Object getRandomEnumConstant(Class<?> enumClass, String enumClassName) {
        assertIsEnum(enumClass, enumClassName);
        var enumConstants = enumClass.getEnumConstants();
        if (enumConstants.length == 0) {
            return null;
        }
        return enumConstants[ThreadLocalRandom.current().nextInt(enumConstants.length)];
    }

    public Object getRandomEnumConstant() {
        return getRandomEnumConstant(theClass, classIdentifier.identifierName);
    }

    /**
     * Resolves a Class With the given name and Similarity
     *
     * @param name       the Intended Class name
     * @param similarity The minimum required similarity
     * @return the resolved Class With the given name and similarity
     */
    @SuppressWarnings("unchecked")
    public Class<T> resolveClass(String packageName, String className, double similarity) {
        if (similarity >= 1) {
            return theClass = (Class<T>) assertDoesNotThrow(
                    () -> Class.forName(String.format("%s.%s", packageName, className)));
        }
        var classes = assertDoesNotThrow(() -> TestUtils.getClasses(packageName));
        var bestMatch = Arrays.stream(classes)
                .sorted((x, y) -> Double.valueOf(TestUtils.similarity(className, y.getSimpleName()))
                        .compareTo(TestUtils.similarity(className, x.getSimpleName())))
                .findFirst().orElseGet(null);
        var sim = TestUtils.similarity(bestMatch.getSimpleName(), className);
        assertNotNull(bestMatch, getClassNotFoundMessage());
        assertTrue(sim >= similarity, getClassNotFoundMessage() + "Ähnlichster Klassenname:" + bestMatch.getSimpleName()
                + " with " + sim + " similarity.");
        return theClass = (Class<T>) bestMatch;
    }

    public Class<T> resolveClass() {
        return resolveClass(classIdentifier.packageName, classIdentifier.identifierName, classIdentifier.similarity);
    }

    public Class<T> resolveClass(double similarity) {
        return resolveClass(classIdentifier.packageName, classIdentifier.identifierName, similarity);
    }

    /**
     * Returns the Default Value for the given Type
     *
     * @param type the Type Class
     * @return the Default Value for the given Type
     */
    public static Object getDefaultValue(Class<?> type) {
        if (List.of(byte.class, short.class, int.class, long.class, float.class, double.class).contains(type)) {
            return 0;
        } else if (type == char.class) {
            return 'a';
        } else if (type == boolean.class) {
            return false;
        } else {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<? extends T> generateDerivedClass(Class<T> clazz, String className, String derivedClassName)
            throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        assertClassNotNull(clazz, className);
        // new sun.tools.javac.Main(baos, source[0]).compile(source);
        // Prepare source somehow.

        System.out.println(clazz.getProtectionDomain().getCodeSource().getLocation().getPath());
        // final File f = new
        // File(clazz.getProtectionDomain().getCodeSource().getLocation().getPath());
        var sourceBuilder = new StringBuilder();
        var lsep = System.getProperty("line.separator");
        if (!clazz.getPackageName().isEmpty()) {
            sourceBuilder.append(String.format("package %s;", clazz.getPackageName()));
            sourceBuilder.append(lsep);
            sourceBuilder.append(String.format("import %s;", clazz.getName())); // Might be unnecessary
            sourceBuilder.append(lsep);
        }
        sourceBuilder.append(String.format("public class %s extends %s {", derivedClassName, clazz.getName()));
        sourceBuilder.append(lsep);
        // Constructors
        for (var c : clazz.getDeclaredConstructors()) {
            if (Modifier.isPrivate(c.getModifiers())) {
                continue;
            }
            sourceBuilder.append(String.format("\t%s %s(%s){" + lsep + "\t\tsuper(%s);" + lsep + "\t}" + lsep,
                    Modifier.toString(c.getModifiers()), derivedClassName,
                    Arrays.stream(c.getParameters())
                            .map(x -> String.format("%s %s", x.getType().getName(), x.getName()))
                            .collect(Collectors.joining(", ")),
                    Arrays.stream(c.getParameters()).map(x -> x.getName()).collect(Collectors.joining(", "))));
        }
        // Abstract Methods
        for (var m : clazz.getDeclaredMethods()) {
            if (!Modifier.isAbstract(m.getModifiers())) {
                continue;
            }
            sourceBuilder.append(String.format("\t%s %s %s(%s){" + lsep + "\t\treturn %s;" + lsep + "\t}" + lsep,
                    Modifier.toString(m.getModifiers() & ~Modifier.ABSTRACT), m.getReturnType().getName(), m.getName(),
                    Arrays.stream(m.getParameters())
                            .map(x -> String.format("%s %s", x.getType().getName(), x.getName()))
                            .collect(Collectors.joining(", ")),
                    getDefaultValue(m.getReturnType())));
        }
        sourceBuilder.append("}");
        String source = sourceBuilder.toString();
        System.out.println(source);

        // Save source in .java file.
        File root = new File(clazz.getProtectionDomain().getCodeSource().getLocation().getPath());
        System.out.println(clazz.getProtectionDomain().getCodeSource().getLocation().getPath());
        File sourceFile = new File(root, clazz.getPackageName().replace(".", "/")
                + String.format("%s%s.java", clazz.getPackageName().isEmpty() ? "" : "/", derivedClassName));
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());

        // Load compiled class.;
        Class<?> cls = Class
                .forName((clazz.getPackageName().isEmpty() ? "" : (clazz.getPackageName() + ".") + derivedClassName));

        return (Class<? extends T>) cls;
    }

    @SuppressWarnings("unchecked")
    public static <T> T resolveInstance(Class<? super T> clazz, String className) {
        assertClassNotNull(clazz, className);
        if (Modifier.isAbstract(clazz.getModifiers())) {
            try {
                clazz = (Class<T>) generateDerivedClass(clazz, className,
                        className + ThreadLocalRandom.current().nextInt(1000, 10000));
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException | IOException e) {
                fail("Could not Create Derivative Class", e);
                // e.printStackTrace();
            }
        }
        assertFalse(Modifier.isAbstract(clazz.getModifiers()), "Kann keine Abstrakten Klasssen instanzieren.");
        var constructors = clazz.getDeclaredConstructors();
        T instance = null;
        for (var c : constructors) {
            try {
                c.setAccessible(true);
                var params = c.getParameters();

                var constructorArgs = Arrays.stream(params).map(x -> {
                    return getDefaultValue(x.getType());
                }).toArray();
                instance = (T) c.newInstance(constructorArgs);
                break;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        assertNotNull(instance, "Could not create Instance.");
        return instance;
    }

    public T resolveInstance() {
        return resolveInstance(theClass, classIdentifier.identifierName);
    }

    public static void assertIsInterface(Class<?> theClass, String className) {
        assertClassNotNull(theClass, className);
        assertTrue(theClass.isInterface(), String.format("%s ist kein Interface.", className));
    }

    public void assertIsInterface() {
        assertIsInterface(theClass, classIdentifier.identifierName);
    }

    public static void assertIsEnum(Class<?> theClass, String className) {
        assertClassNotNull(theClass, className);
        assertTrue(theClass.isEnum(), String.format("%s ist kein Enum.", className));
    }

    public void assertIsEnum() {
        assertIsEnum(theClass, classIdentifier.identifierName);
    }

    public static void assertIsPlainClass(Class<?> theClass, String className) {
        assertClassNotNull(theClass, className);
        assertFalse(theClass.isInterface(), String.format("%s sollte kein Interface sein.", className));
        assertFalse(theClass.isEnum(), String.format("%s sollte kein Enum sein.", className));
    }

    public void assertIsPlainClass() {
        assertIsPlainClass(theClass, classIdentifier.identifierName);
    }

}
