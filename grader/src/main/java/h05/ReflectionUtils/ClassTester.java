package h05.ReflectionUtils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.spy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.primitives.Primitives;

import org.mockito.MockingDetails;

import h05.TestUtils;
import net.bytebuddy.ByteBuddy;

/**
 * A Class Tester
 *
 * @author Ruben Deisenroth
 */
public class ClassTester<T> {
    /**
     * The Class Identifier (Containing Name, Similarity)
     */
    IdentifierMatcher classIdentifier;
    /**
     * The resolved Class that will be tested
     */
    Class<T> theClass;
    /**
     * The Expected Access Modifier
     */
    int accessModifier;
    /**
     * The Expected Super class
     */
    private Class<? super T> superClass;
    /**
     * Matchers for the Interfaces that are expected to be implemented
     */
    private ArrayList<IdentifierMatcher> implementsInterfaces;

    /**
     * The Class Instance of the Class Being Tested
     */
    T classInstance;

    /**
     * Creates a new {@link ClassTester}
     *
     * @param packageName          the Package Name of the Class
     * @param className            The Class Name
     * @param similarity           The Maximum Name Similarity for matching
     * @param accessModifier       The Expected Access Modifier
     * @param superClass           The Expected Super Class
     * @param implementsInterfaces Matchers for the Interfaces that are expected to
     *                             be implemented
     * @param classInstance        The Class Instance of the Class Being Tested
     */
    public ClassTester(String packageName, String className, double similarity, int accessModifier,
            Class<? super T> superClass, ArrayList<IdentifierMatcher> implementsInterfaces, T classInstance) {
        this.classIdentifier = new IdentifierMatcher(className, packageName, similarity);
        this.accessModifier = accessModifier;
        this.superClass = superClass;
        this.implementsInterfaces = implementsInterfaces;
        this.classInstance = classInstance;
    }

    /**
     * Creates a new {@link ClassTester}
     *
     * @param packageName          the Package Name of the Class
     * @param className            The Class Name
     * @param similarity           The Maximum Name Similarity for matching
     * @param accessModifier       The Expected Access Modifier
     * @param superClass           The Expected Super Class
     * @param implementsInterfaces Matchers for the Interfaces that are expected to
     *                             be implemented
     */
    public ClassTester(String packageName, String className, double similarity, int accessModifier,
            Class<? super T> superClass, ArrayList<IdentifierMatcher> implementsInterfaces) {
        this(packageName, className, similarity, accessModifier, superClass, implementsInterfaces, null);
    }

    /**
     * Creates a new {@link ClassTester}
     *
     * @param packageName    the Package Name of the Class
     * @param className      The Class Name
     * @param similarity     The Maximum Name Similarity for matching
     * @param accessModifier The Expected Access Modifier
     */
    public ClassTester(String packageName, String className, double similarity, int accessModifier) {
        this(packageName, className, similarity, accessModifier, null, new ArrayList<>(), null);
    }

    /**
     * Creates a new {@link ClassTester}
     *
     * @param packageName the Package Name of the Class
     * @param className   The Class Name
     * @param similarity  The Maximum Name Similarity for matching
     */
    public ClassTester(String packageName, String className, double similarity) {
        this(packageName, className, similarity, -1, null, new ArrayList<>(), null);
    }

    /**
     * Creates a new {@link ClassTester}
     *
     * @param packageName the Package Name of the Class
     * @param className   The Class Name
     */
    public ClassTester(String packageName, String className) {
        this(packageName, className, 1, -1, null, new ArrayList<>(), null);
    }

    public ClassTester(Class<T> clazz) {
        this(
                clazz.getPackageName(),
                clazz.getSimpleName(),
                0.8,
                clazz.getModifiers(),
                clazz.getSuperclass(),
                Arrays.stream(clazz.getInterfaces())
                        .map(x -> new IdentifierMatcher(x.getSimpleName(), x.getPackageName(), 0.8))
                        .collect(Collectors.toCollection(ArrayList::new)),
                null);
        setTheClass(clazz);
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

    /**
     * Adds an Interface Matcher to the {@link #implementsInterfaces} List
     *
     * @param interfaceMatcher the InterfaceMatcher
     */
    public void addImplementsInterface(IdentifierMatcher interfaceMatcher) {
        if (implementsInterfaces == null) {
            implementsInterfaces = new ArrayList<>();
        }
        implementsInterfaces.add(interfaceMatcher);
    }

    /**
     * Adds an Interface Matcher to the {@link #implementsInterfaces} List
     *
     * @param interfaceName the InterfaceMatcher
     * @param similarity    the Maximum similarity allowed
     */
    public void addImplementsInterface(String interfaceName, Double similarity) {
        addImplementsInterface(new IdentifierMatcher(interfaceName, similarity));
    }

    /**
     * Adds an Interface Matcher to the {@link #implementsInterfaces} List
     *
     * @param interfaceName the InterfaceMatcher
     */
    public void addImplementsInterface(String interfaceName) {
        addImplementsInterface(interfaceName, null);
    }

    /**
     * Gets all Fields from a given Class and its superclasses recursively
     *
     * @param fields the fields so far (initially give it new ArrayList<>())
     * @param clazz  the Class to search
     * @return all Fields from a given Class and its superclasses recursively
     */
    private static ArrayList<Field> getAllFields(ArrayList<Field> fields, Class<?> clazz) {
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));

        if (clazz.getSuperclass() != null) {
            getAllFields(fields, clazz.getSuperclass());
        }

        return fields;
    }

    /**
     * Gets all Fields from a given Class and its superclasses recursively
     *
     * @param clazz the Class to search
     * @return all Fields from a given Class and its superclasses recursively
     */
    public static ArrayList<Field> getAllFields(Class<?> clazz) {
        return getAllFields(new ArrayList<>(), clazz);
    }

    /**
     * Gets all Fields from {@link #theClass} and its superclasses recursively
     *
     * @return all Fields from from {@link #theClass} and its superclasses
     *         recursively
     */
    public ArrayList<Field> getAllFields() {
        return getAllFields(new ArrayList<>(), getTheClass());
    }

    /**
     * Resolves An Attribute with a given {@link AttributeMatcher}.
     *
     * @param matcher the {@link AttributeMatcher}
     * @return the Attribute-{@link Field}
     */
    public Field resolveAttribute(AttributeMatcher matcher) {
        assertClassResolved();
        ArrayList<Field> fields = matcher.allowSuperClass ? getAllFields(theClass)
                : new ArrayList<>(Arrays.asList(theClass.getDeclaredFields()));
        Field bestMatch = fields.stream()
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

    /**
     * Asserts that a given attribute has a getter
     *
     * @param attribute the Attribute-{@link Field}
     */
    public void assertHasGetter(Field attribute, ParameterMatcher... parameters) {
        assertNotNull(attribute);

        // Method Declaration
        var methodTester = new MethodTester(this, String.format("get%s%s",
                attribute.getName().substring(0, 1).toUpperCase(), attribute.getName().substring(1)), 0.8,
                Modifier.PUBLIC, attribute.getType(), new ArrayList<>(Arrays.asList(parameters)));
        methodTester.resolveMethod();
        methodTester.assertAccessModifier();
        methodTester.assertParametersMatch();
        methodTester.assertReturnType();

        // test with Value

        assertDoesNotThrow(() -> attribute.setAccessible(true),
                "Konnte nicht auf Attribut zugreifen:" + attribute.getName());

        resolveInstance();

        var expectedReturnValue = getRandomValue(attribute.getType());
        assertDoesNotThrow(() -> attribute.set(getClassInstance(), expectedReturnValue));
        var returnValue = methodTester
                .invoke(Arrays.stream(parameters).map(x -> getRandomValue(x.parameterType)).toArray());
        assertEquals(expectedReturnValue, returnValue, "Falsche Rückgabe der Getter-Metode.");
    }

    /**
     * Asserts that a given attribute has a getter
     *
     * @param attribute the Attribute-{@link Field}
     * @param testValue the TestValue
     */
    public void assertHasSetter(Field attribute, Object testValue) {
        assertNotNull(attribute);

        // Method Declaration
        var methodTester = new MethodTester(this, String.format("set%s%s",
                attribute.getName().substring(0, 1).toUpperCase(), attribute.getName().substring(1)), 0.8,
                Modifier.PUBLIC, void.class, new ArrayList<>(List.of(
                        new ParameterMatcher(attribute.getName(), 0.8, attribute.getType())))).verify();

        // test with Value
        methodTester.invoke(testValue);
        assertFieldEquals(attribute, testValue, "Falscher Wert durch Setter-Methode.");
    }

    /**
     * Asserts that a given attribute has a getter
     *
     * @param attribute the Attribute-{@link Field}
     */
    public void assertHasSetter(Field attribute) {
        assertNotNull(attribute);
        assertHasSetter(attribute, getRandomValue(attribute.getType()));
    }

    /**
     * asserts that all the interfaces described by the given matchers are being
     * extended
     *
     * @param implementsInterfaces the Interface-Matchers
     */
    public void assertImplementsInterfaces(ArrayList<IdentifierMatcher> implementsInterfaces) {
        assertClassResolved();
        var interfaces = new ArrayList<>(List.of(theClass.getInterfaces()));
        if (implementsInterfaces == null || implementsInterfaces.isEmpty()) {
            assertTrue(interfaces == null || interfaces.isEmpty(), "Es sollen keine Interfaces implementiert werden.");
        } else {
            for (int i = 0; i < implementsInterfaces.size(); i++) {
                var matcher = implementsInterfaces.get(i);
                assertFalse(interfaces.isEmpty(), getInterfaceNotImplementedMessage(matcher.identifierName));
                var bestMatch = interfaces.stream()
                        .sorted((x, y) -> Double
                                .valueOf(TestUtils.similarity(matcher.identifierName, y.getSimpleName()))
                                .compareTo(TestUtils.similarity(matcher.identifierName, x.getSimpleName())))
                        .findFirst().orElse(null);
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

    /**
     * asserts that all the interfaces described by {@link #implementsInterfaces}
     * are being extended
     */
    public void assertImplementsInterfaces() {
        assertImplementsInterfaces(implementsInterfaces);
    }

    /**
     * asserts that {@link #theClass} does not extend any interfaces
     */
    public void assertDoesNotImplementAnyInterfaces() {
        assertImplementsInterfaces(null);
    }

    /**
     * Returns true if {@link #theClass} is not null
     *
     * @return true if {@link #theClass} is not null
     */
    public boolean class_resolved() {
        return theClass != null;
    }

    /**
     * Gets the {@link MockingDetails} of {@link #theClass}
     *
     * @return the {@link MockingDetails} of {@link #theClass}
     */
    public MockingDetails getMockingDetails() {
        return mockingDetails(getClassInstance());
    }

    /**
     * Returns true if {@link #theClass} is Mocked
     *
     * @return true if {@link #theClass} is Mocked
     * @see MockingDetails#isMock()
     */
    public boolean is_mock() {
        return classInstanceResolved() && mockingDetails(getClassInstance()).isMock();
    }

    /**
     * Returns true if {@link #theClass} is a Spy
     *
     * @return true if {@link #theClass} is a Spy
     * @see MockingDetails#isSpy()
     */
    public boolean is_spy() {
        return classInstanceResolved() && mockingDetails(getClassInstance()).isSpy();
    }

    /**
     * Generates a class not found Message
     *
     * @param className the Class Name
     * @return a class not found Message
     */
    public static String getClassNotFoundMessage(String className) {
        return String.format("Klasse %s existiert nicht.", className);
    }

    /**
     * Makes the class a Spy if not done already
     *
     * @return this
     */
    public ClassTester<T> assureSpied() {
        assertclassInstanceResolved();
        if (!is_spy()) {
            setClassInstance(spy(getClassInstance()));
        }
        return this;
    }

    /**
     * Makes the class a Spy if not done already
     *
     * @return this
     */
    public ClassTester<T> assertSpied() {
        assertclassInstanceResolved();
        assertTrue(is_spy(), "Faulty Test: Class was not spied on");
        return this;
    }

    /**
     * Generates a class not found Message
     *
     * @return a class not found Message
     */
    public String getClassNotFoundMessage() {
        return getClassNotFoundMessage(classIdentifier.identifierName);
    }

    /**
     * Generates a Interface not found Message
     *
     * @param interfaceName the Interface Name
     * @return a Interface not found Message
     */
    public static String getInterfaceNotImplementedMessage(String interfaceName) {
        return String.format("Interface %s wird nicht erweitert.", interfaceName);
    }

    /**
     * Asserts that a given Class is not {@code null} and fails with the propper
     * message if not
     *
     * @param theClass  the {@link Class}
     * @param className the Class Name for the error Message
     */
    public static void assertClassNotNull(Class<?> theClass, String className) {
        assertNotNull(theClass, getClassNotFoundMessage(className));
    }

    /**
     * Asserts that {@link #theClass} is not {@code null} and fails with the propper
     * message if not resolved
     */
    public void assertClassResolved() {
        assertClassNotNull(theClass, classIdentifier.identifierName);
    }

    /**
     * Asserts that the Class is declared correctly.
     *
     * @return {@link ClassTester} this
     */
    public ClassTester<T> verify() {
        if (!class_resolved()) {
            resolveClass();
        }
        if (accessModifier >= 0) {
            // Class Type
            if (Modifier.isInterface(getAccessModifier())) {
                assertIsInterface();
            } else if ((getAccessModifier() & TestUtils.ENUM) != 0) {
                assertIsEnum();
            } else {
                assertIsPlainClass();
            }
            assertAccessModifier();
        }
        assertSuperclass();
        assertImplementsInterfaces();
        return this;
    }

    /**
     * Asserts that the Class is declared correctly.
     *
     * @param minSimilarity the Minimum required Similarity
     * @return {@link ClassTester} this
     */
    public ClassTester<T> verify(double minSimilarity) {
        final var currSim = getClassIdentifier().similarity;
        getClassIdentifier().similarity = minSimilarity;
        verify();
        getClassIdentifier().similarity = currSim;
        return this;
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
     * Assert tthat the Superclass of {@link #theClass} matches {@link #superClass}
     */
    public void assertSuperclass() {
        assertClassResolved();

        if (superClass == null) {
            if (getAccessModifier() >= 0) {
                if ((getAccessModifier() & TestUtils.ENUM) != 0) {
                    assertSame(Enum.class, theClass.getSuperclass());
                } else if (Modifier.isInterface(getAccessModifier())) {
                    assertSame(null, theClass.getSuperclass());
                } else {
                    assertSame(Object.class, theClass.getSuperclass());

                }
            }
        } else {
            assertSame(superClass, theClass.getSuperclass());
        }
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

    /**
     * Gets the Value of {@link #accessModifier}
     *
     * @return the Value of {@link #accessModifier}
     */
    public int getAccessModifier() {
        return accessModifier;
    }

    /**
     * Sets {@link #accessModifier} to the given Modifier
     *
     * @param accessModifier the new Modifier
     */
    public void setAccessModifier(int accessModifier) {
        this.accessModifier = accessModifier;
    }

    /**
     * Asserts that the Access Modifier is correct, with propper Fail Message
     */
    public void assertAccessModifier() {
        if (accessModifier >= 0) {
            TestUtils.assertModifier(accessModifier, theClass);
        }
    }

    /**
     * Gets the Value of {@link #classInstance}
     *
     * @return the Value of {@link #classInstance}
     */
    public T getClassInstance() {
        return classInstance;
    }

    /**
     * Sets {@link #classInstance} to the given Value
     *
     * @param classInstance the new Class Instance
     */
    public void setClassInstance(T classInstance) {
        this.classInstance = classInstance;
    }

    /**
     * Returns true if {@link #classInstance} is not {@code null}
     *
     * @return true if {@link #classInstance} is not {@code null}
     */
    public boolean classInstanceResolved() {
        return classInstance != null;
    }

    /**
     * Asserts that {@link #classInstance} is not {@code null}
     */
    public void assertclassInstanceResolved() {
        assertNotNull(classInstance, "Es wurde keine Klassen-Instanz gefunden.");
    }

    /**
     * Generates a Message for a Missing enum constant
     *
     * @param constantName the Constant name
     * @return the generated Message
     */
    public static String getEnumConstantMissingMessage(String constantName) {
        return String.format("Enum-Konstante %s fehlt.", constantName);
    }

    /**
     * Assert that enum Constants with the given names exist
     *
     * @param expectedConstants the enum Constants
     */
    public void assertEnumConstants(String[] expectedConstants) {
        assertClassResolved();
        var enum_values = theClass.getEnumConstants();
        for (String n : expectedConstants) {
            assertTrue(Stream.of(enum_values).anyMatch(x -> x.toString().equals(n)),
                    String.format("Enum-Konstante %s fehlt.", n));
        }
    }

    /**
     * Gets a random Enum Constant
     *
     * @param enumClass     the {@link Enum}-{@link Class}
     * @param enumClassName the expected {@link Enum}-{@link Class}-Name
     * @return the random Enum Constant
     */
    public static Enum<?> getRandomEnumConstant(Class<Enum<?>> enumClass, String enumClassName) {
        assertIsEnum(enumClass, enumClassName);
        var enumConstants = enumClass.getEnumConstants();
        if (enumConstants.length == 0) {
            return null;
        }
        return enumConstants[ThreadLocalRandom.current().nextInt(enumConstants.length)];
    }

    /**
     * Gets a random Enum Constant
     *
     * @return the random Enum Constant
     */
    @SuppressWarnings("unchecked")
    public Enum<?> getRandomEnumConstant() {
        assertIsEnum();
        return getRandomEnumConstant((Class<Enum<?>>) theClass, classIdentifier.identifierName);
    }

    /**
     * returns the Value of {@link #classIdentifier}
     *
     * @return the Value of {@link #classIdentifier}
     */
    public IdentifierMatcher getClassIdentifier() {
        return classIdentifier;
    }

    /**
     * Sets {@link #classIdentifier} to the given Value
     *
     * @param classIdentifier the new Class Identifier
     */
    public void setClassIdentifier(IdentifierMatcher classIdentifier) {
        this.classIdentifier = classIdentifier;
    }

    /**
     * Resolves a Class With the given name and Similarity
     *
     * @param name       the Intended Class name
     * @param similarity The minimum required similarity
     * @return the resolved Class With the given name and similarity
     */
    @SuppressWarnings("unchecked")
    public Class<T> findClass(String packageName, String className, double similarity) {
        // if (similarity >= 1) {
        // return theClass = (Class<T>) assertDoesNotThrow(
        // () -> Class.forName(String.format("%s.%s", packageName, className)),
        // getClassNotFoundMessage(className));
        // }
        var classes = assertDoesNotThrow(() -> TestUtils.getClasses(packageName));
        var bestMatch = Arrays.stream(classes)
                .sorted((x, y) -> Double.valueOf(TestUtils.similarity(className, y.getSimpleName()))
                        .compareTo(TestUtils.similarity(className, x.getSimpleName())))
                .findFirst().orElse(null);
        var sim = TestUtils.similarity(bestMatch.getSimpleName(), className);
        assertNotNull(bestMatch, getClassNotFoundMessage());
        assertTrue(sim >= similarity, getClassNotFoundMessage() + "Ähnlichster Klassenname:" + bestMatch.getSimpleName()
                + " with " + sim + " similarity.");
        return theClass = (Class<T>) bestMatch;
    }

    /**
     * Resolves a Class With the current Class name and Similarity
     *
     * @return the resolved Class With the given name and similarity
     */
    public Class<T> findClass() {
        return findClass(classIdentifier.packageName, classIdentifier.identifierName, classIdentifier.similarity);
    }

    /**
     * Resolves a Class With the given Similarity
     *
     * @param similarity The minimum required similarity
     * @return the resolved Class With the given name and similarity
     */
    public Class<T> findClass(double similarity) {
        return findClass(classIdentifier.packageName, classIdentifier.identifierName, similarity);
    }

    /**
     * Finds The Class and stores it in {@link #theClass}
     *
     * @return this
     */
    public ClassTester<T> resolveClass() {
        theClass = findClass();
        return this;
    }

    /**
     * Resolves the class if necessary (We do not care about fields fields being
     * made accessible here)
     *
     * @return this
     */
    public ClassTester<T> assureClassResolved() {
        if (!class_resolved()) {
            resolveClass();
        }
        return this;
    }

    /**
     * Resolves the Class and Instance and stores them in {@link #theClass} and
     * {@link #classInstance}
     *
     * @return this
     */
    public ClassTester<T> resolve() {
        assureClassResolved();
        resolveInstance();
        return this;
    }

    /**
     * Returns the Default Value for the given Type
     *
     * @param type the Type Class
     * @return the Default Value for the given Type
     */
    public static Object getDefaultValue(Class<?> type) {
        if (type == null) {
            return null;
        } else if (type == short.class || type == Short.class) {
            return (short) 0;
        } else if (type == int.class || type == Integer.class) {
            return 0;
        } else if (type == long.class || type == Long.class) {
            return (long) 0;
        } else if (type == float.class || type == Float.class) {
            return (float) 0;
        } else if (type == double.class || type == Double.class) {
            return (double) 0;
        } else if (type == char.class || type == Character.class) {
            return (char) 'a';
        } else if (type == boolean.class || type == Boolean.class) {
            return false;
        } else {
            return null;
        }
    }

    /**
     * Returns the Random Value for the given Type
     *
     * @param type the Type Class
     * @return the Random Value for the given Type
     */
    @SuppressWarnings("unchecked")
    public static Object getRandomValue(Class<?> type) {
        if (type == null) {
            return null;
        }
        if (type == byte.class || type == Byte.class) {
            return (byte) ThreadLocalRandom.current().nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
        } else if (type == short.class || type == Short.class) {
            return (short) ThreadLocalRandom.current().nextInt(Short.MIN_VALUE, Short.MAX_VALUE);
        } else if (type == int.class || type == Integer.class) {
            return ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else if (type == long.class || type == Long.class) {
            return ThreadLocalRandom.current().nextLong(Long.MIN_VALUE, Long.MAX_VALUE);
        } else if (type == float.class || type == Float.class) {
            return (float) ThreadLocalRandom.current().nextDouble(Float.MIN_VALUE, Float.MAX_VALUE);
        } else if (type == double.class || type == Double.class) {
            return ThreadLocalRandom.current().nextDouble(Double.MIN_VALUE, Double.MAX_VALUE);
        } else if (type == char.class || type == Character.class) {
            return (char) ThreadLocalRandom.current().nextInt(Character.MIN_VALUE, Character.MAX_VALUE);
        } else if (type == boolean.class) {
            return ThreadLocalRandom.current().nextBoolean();
        } else if (type.isEnum()) {
            return getRandomEnumConstant((Class<Enum<?>>) type, type.getName());
        } else {
            return resolveInstance(type, type.getName() + "Impl" + ThreadLocalRandom.current().nextInt(1000, 10000));
        }
    }

    /**
     * Generates A derived Class from a given Class
     *
     * @param <T>              The Generic Class Type
     * @param clazz            The source class
     * @param className        the source Class Name
     * @param derivedClassName the name for the derived Class
     * @return the derived Class
     */
    public static <T> Class<? extends T> generateDerivedClass(Class<T> clazz, String className,
            String derivedClassName) {
        assertClassNotNull(clazz, className);

        return new ByteBuddy()
                .subclass(clazz)
                .make()
                .load(clazz.getClassLoader())
                .getLoaded();
    }

    /**
     * Resolves an Instance of a given class (even abstract)
     *
     * @param <T>       The Instance type
     * @param clazz     The class to generate the Instance from
     * @param className the Class Name
     * @return the instance
     */
    @SuppressWarnings("unchecked")
    public static <T> T resolveInstance(Class<? super T> clazz, String className) {
        assertClassNotNull(clazz, className);
        if (Modifier.isAbstract(clazz.getModifiers())) {
            clazz = (Class<T>) generateDerivedClass(clazz, className,
                    className + ThreadLocalRandom.current().nextInt(1000, 10000));
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

    /**
     * Resolves an Instance of {@link #theClass} (even abstract)
     *
     * @param <T> The Instance type
     * @return the instance
     */
    public T resolveInstance() {
        return classInstance = resolveInstance(theClass, classIdentifier.identifierName);
    }

    /**
     * Resolves a Constructor with the given parameters
     *
     * @param parameters the Expected Parameters
     * @return the best Match
     */
    @SuppressWarnings("unchecked")
    public Constructor<T> resolveConstructor(ArrayList<ParameterMatcher> parameters) {
        assertClassResolved();
        Constructor<T>[] constructors = (Constructor<T>[]) assertDoesNotThrow(() -> theClass.getDeclaredConstructors());
        assertTrue(constructors.length > 0, "Keine Konstruktoren gefunden.");
        Constructor<T> bestMatch = null;
        if (parameters != null && !parameters.isEmpty()) {
            // Find Best match according to parameter options
            bestMatch = Arrays.stream(constructors)
                    .sorted((x, y) -> Integer
                            .valueOf(MethodTester.countMatchingParameters(parameters,
                                    new ArrayList<>(Arrays.asList(x.getParameters())), true))
                            .compareTo(MethodTester.countMatchingParameters(parameters,
                                    new ArrayList<>(Arrays.asList(x.getParameters())), true)))
                    .findFirst().orElse(null);
        } else {
            bestMatch = Arrays.stream(constructors).filter(x -> x.getParameterCount() == 0).findFirst().orElse(null);
        }
        assertNotNull(bestMatch, "Der Passende Konstruktor wurde nicht gefunden");
        return bestMatch;
    }

    /**
     * Resolves a Constructor with the given parameters
     *
     * @param parameters the Expected Parameters
     * @return the best Match
     */
    public Constructor<T> resolveConstructor(ParameterMatcher... parameters) {
        return resolveConstructor(new ArrayList<>(Arrays.asList(parameters)));
    }

    /**
     * Asserts that a {@link Constructor} was declared correctly
     *
     * @param constructor    the {@link Constructor}
     * @param accessModifier the expected access Modifier
     * @param parameters     the expected Parameters
     */
    public void assertConstructorValid(Constructor<T> constructor, int accessModifier,
            ArrayList<ParameterMatcher> parameters) {
        assertNotNull(constructor, "Der Passende Konstruktor wurde nicht gefunden");
        TestUtils.assertModifier(accessModifier, constructor);
        MethodTester.assertParametersMatch(parameters, new ArrayList<>(Arrays.asList(constructor.getParameters())),
                true);
    }

    /**
     * Asserts that a {@link Constructor} was declared correctly
     *
     * @param constructor    the {@link Constructor}
     * @param accessModifier the expected access Modifier
     * @param parameters     the expected Parameters
     */
    public void assertConstructorValid(Constructor<T> constructor, int accessModifier,
            ParameterMatcher... parameters) {
        assertConstructorValid(constructor, accessModifier, new ArrayList<>(Arrays.asList(parameters)));
    }

    public static void setFieldTyped(Field field, Object obj, Object value)
            throws IllegalArgumentException, IllegalAccessException {
        if (field == null) {
            return;
        }
        var type = field.getType();
        if (type == byte.class || type == Byte.class) {
            field.setByte(obj, (byte) value);
        } else if (type == short.class || type == Short.class) {
            field.setShort(obj, (short) value);
        } else if (type == int.class || type == Integer.class) {
            field.setInt(obj, (int) value);
        } else if (type == long.class || type == Long.class) {
            field.setLong(obj, (long) value);
        } else if (type == float.class || type == Float.class) {
            field.setFloat(obj, (float) value);
        } else if (type == double.class || type == Double.class) {
            field.setDouble(obj, (double) value);
        } else if (type == char.class || type == Character.class) {
            field.setChar(obj, (char) value);
        } else if (type == boolean.class || type == Boolean.class) {
            field.setBoolean(obj, (boolean) value);
        } else {
            field.set(obj, value);
        }
    }

    /**
     * Sets a field to a given Class
     *
     * @param instance the Instance to set the field
     * @param field    the Field to modify
     * @param value    the new Value
     */
    public static void setField(Object instance, Field field, Object value) {
        assertNotNull(field, "Das Feld wurde nicht gefunden.");
        assertDoesNotThrow(() -> {
            field.setAccessible(true);
            setFieldTyped(field, instance, value);
        }, "Konnte nicht auf Attribut " + field.getName() + " zugreifen.");
    }

    /**
     * Sets a field to {@link #classInstance}
     *
     * @param field the Field to modify
     * @param value the new Value
     */
    public void setField(Field field, Object value) {
        setField(getClassInstance(), field, value);
    }

    /**
     * Sets a field of {@link #classInstance} to a random Value Supported by its
     * type
     *
     * @param field the Field to set
     * @return the random Value
     */
    public Object setFieldRandom(Field field) {
        assertNotNull(field, "Das Feld wurde nicht gefunden.");
        var value = getRandomValue(field.getType());
        setField(field, value);
        return value;
    }

    /**
     * Gets the Value of a given field of a given Instance
     *
     * @param instance the Class Instance
     * @param field    the Field to get
     * @return the Value
     */
    public static Object getFieldValue(Object instance, Field field) {
        assertNotNull(field, "Das Feld wurde nicht gefunden.");
        assertNotNull(instance, "Es wurde keine Klassen-Instanz gefunden.");
        return assertDoesNotThrow(() -> field.get(instance));
    }

    /**
     * Gets the Value of a given field of {@link #classInstance}
     *
     * @param field the Field to get
     * @return the Value
     */
    public Object getFieldValue(Field field) {
        assertNotNull(field, "Das Feld wurde nicht gefunden.");
        assertclassInstanceResolved();
        if (!field.canAccess(Modifier.isStatic(field.getModifiers()) ? null : getClassInstance())) {
            assertDoesNotThrow(() -> field.setAccessible(true));
        }
        return assertDoesNotThrow(() -> field.get(getClassInstance()));
    }

    /**
     * Asserts that a given field has a certain value
     *
     * @param field             the field
     * @param expected          the expected Value
     * @param additionalMessage an Addition Error Message
     */
    public void assertFieldEquals(Field field, Object expected, String additionalMessage) {
        assertEquals(expected, getFieldValue(field), "Das Attribut " + field.getName() + " hat den Falschen Wert."
                + (additionalMessage == null ? "" : "\n" + additionalMessage));
    }

    /**
     * Asserts that a given field has a certain value
     *
     * @param field    the field
     * @param expected the expected Value
     */
    public void assertFieldEquals(Field field, Object expected) {
        assertFieldEquals(field, expected, "");
    }

    /**
     * Gets a specific Enum-Value
     *
     * @param <T>          The Generic Enum-Type
     * @param enumClass    the Enum Class
     * @param expectedName the expected Enum Class Name
     * @param similarity   the min Similarity
     * @return the specific Enum-Value
     */
    public static <T> Enum<?> getEnumValue(Class<Enum<?>> enumClass, String expectedName, double similarity) {
        var enumConstants = enumClass.getEnumConstants();
        var bestMatch = Arrays.stream(enumConstants).sorted((x, y) -> Double.valueOf(
                TestUtils.similarity(expectedName, y.name())).compareTo(TestUtils.similarity(expectedName, x.name())))
                .findFirst().orElse(null);
        assertNotNull(bestMatch, "Enum-Wert" + expectedName + " existiert nicht.");
        var sim = TestUtils.similarity(expectedName, bestMatch.name());
        assertTrue(sim >= similarity,
                "Enum-Wert" + expectedName + " existiert nicht. Ähnlichte Konstante:" + bestMatch.name());
        return bestMatch;
    }

    /**
     * Gets a specific Enum-Value
     *
     * @param expectedName the expected Enum Class Name
     * @param similarity   the min Similarity
     * @return the specific Enum-Value
     */
    @SuppressWarnings("unchecked")
    public Enum<?> getEnumValue(String expectedName, double similarity) {
        return getEnumValue((Class<Enum<?>>) theClass, expectedName, similarity);
    }

    /**
     * asserts a given Class is an Interface
     *
     * @param theClass  the class to check
     * @param className the expected Class Name
     */
    public static void assertIsInterface(Class<?> theClass, String className) {
        assertClassNotNull(theClass, className);
        assertTrue(theClass.isInterface(), String.format("%s ist kein Interface.", className));
    }

    /**
     * asserts {@link #theClass} is an Interface
     *
     * @param className the expected Class Name
     */
    public void assertIsInterface() {
        assertIsInterface(theClass, classIdentifier.identifierName);
    }

    /**
     * asserts a given Class is an Enum
     *
     * @param theClass  the class to check
     * @param className the expected Class Name
     */
    public static void assertIsEnum(Class<?> theClass, String className) {
        assertClassNotNull(theClass, className);
        assertTrue(theClass.isEnum(), String.format("%s ist kein Enum.", className));
    }

    /**
     * asserts {@link #theClass} is an Enum
     *
     * @param className the expected Class Name
     */
    public void assertIsEnum() {
        assertIsEnum(theClass, classIdentifier.identifierName);
    }

    /**
     * asserts a given Class is a Plain Class
     *
     * @param theClass  the class to check
     * @param className the expected Class Name
     */
    public static void assertIsPlainClass(Class<?> theClass, String className) {
        assertClassNotNull(theClass, className);
        assertFalse(theClass.isInterface(), String.format("%s sollte kein Interface sein.", className));
        assertFalse(theClass.isEnum(), String.format("%s sollte kein Enum sein.", className));
    }

    /**
     * asserts {@link #theClass} is a Plain Class
     *
     * @param className the expected Plain Class
     */
    public void assertIsPlainClass() {
        assertIsPlainClass(theClass, classIdentifier.identifierName);
    }

}
