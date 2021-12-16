package h05.ReflectionUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.mockito.invocation.Invocation;
import org.sourcegrade.docwatcher.MethodDocumentation;
import org.sourcegrade.docwatcher.SourceDocumentation;

import h05.TestUtils;

/**
 * A Method Tester
 *
 * @author Ruben Deisenroth
 */
public class MethodTester {
    /**
     * The Method-Identifier
     */
    IdentifierMatcher methodIdentifier;
    /**
     * The resolved Method that will be tested
     */
    Method theMethod;
    /**
     * The Expected Access Modifier
     */
    int accessModifier;
    /**
     * The expected return Type
     */
    private Class<?> returnType;
    /**
     * The expected parameters
     */
    private ArrayList<ParameterMatcher> parameters;
    /**
     * A Class Tester (used for invoking)
     */
    private ClassTester<?> classTester;
    /**
     * whether to also match super implementations
     */
    public boolean allowSuperClass;

    /**
     * Generates a new {@link MethodTester}
     *
     * @param classTester
     *            A Class Tester (used for invoking)
     * @param methodName
     *            the expected method name
     * @param similarity
     *            the minimum matching similarity
     * @param accessModifier
     *            The Expected Access Modifier
     * @param returnType
     *            The expected return Type
     * @param parameters
     *            The expected parameters
     */
    public MethodTester(ClassTester<?> classTester, String methodName, double similarity, int accessModifier,
        Class<?> returnType, ArrayList<ParameterMatcher> parameters, boolean allowSuperClass) {
        this.classTester = classTester;
        this.methodIdentifier = new IdentifierMatcher(methodName, similarity);
        this.accessModifier = accessModifier;
        this.returnType = returnType;
        this.parameters = parameters;
        this.allowSuperClass = allowSuperClass;
    }

    /**
     * Generates a new {@link MethodTester}
     *
     * @param classTester
     *            A Class Tester (used for invoking)
     * @param methodName
     *            the expected method name
     * @param similarity
     *            the minimum matching similarity
     * @param accessModifier
     *            The Expected Access Modifier
     * @param returnType
     *            The expected return Type
     * @param parameters
     *            The expected parameters
     */
    public MethodTester(ClassTester<?> classTester, String methodName, double similarity, int accessModifier,
        Class<?> returnType, ArrayList<ParameterMatcher> parameters) {
        this(classTester, methodName, similarity, accessModifier, returnType, parameters, false);

    }

    /**
     * Generates a new {@link MethodTester}
     *
     * @param classTester
     *            A Class Tester (used for invoking)
     * @param methodName
     *            the expected method name
     * @param similarity
     *            the minimum matching similarity
     * @param accessModifier
     *            The Expected Access Modifier
     * @param returnType
     *            The expected return Type
     */
    public MethodTester(ClassTester<?> classTester, String methodName, double similarity, int accessModifier,
        Class<?> returnType) {
        this(classTester, methodName, similarity, accessModifier, returnType, null);
    }

    /**
     * Generates a new {@link MethodTester}
     *
     * @param classTester
     *            A Class Tester (used for invoking)
     * @param methodName
     *            the expected method name
     * @param similarity
     *            the minimum matching similarity
     * @param accessModifier
     *            The Expected Access Modifier
     */
    public MethodTester(ClassTester<?> classTester, String methodName, double similarity, int accessModifier) {
        this(classTester, methodName, similarity, accessModifier, null, null);
    }

    /**
     * Generates a new {@link MethodTester}
     *
     * @param classTester
     *            A Class Tester (used for invoking)
     * @param methodName
     *            the expected method name
     * @param similarity
     *            the minimum matching similarity
     * @param returnType
     *            The expected return Type
     * @param parameters
     *            The expected parameters
     */
    public MethodTester(ClassTester<?> classTester, String methodName, double similarity, Class<?> returnType,
        ArrayList<ParameterMatcher> parameters) {
        this(classTester, methodName, similarity, -1, returnType, parameters);
    }

    /**
     * Generates a new {@link MethodTester}
     *
     * @param classTester
     *            A Class Tester (used for invoking)
     * @param methodName
     *            the expected method name
     * @param similarity
     *            the minimum matching similarity
     */
    public MethodTester(ClassTester<?> classTester, String methodName, double similarity) {
        this(classTester, methodName, similarity, -1, null);
    }

    /**
     * Generates a new {@link MethodTester}
     *
     * @param classTester
     *            A Class Tester (used for invoking)
     * @param methodName
     *            the expected method name
     */
    public MethodTester(ClassTester<?> classTester, String methodName) {
        this(classTester, methodName, 1, -1, null);
    }

    /**
     * returns the Value of {@link #classTester}
     *
     * @return the Value of {@link #classTester}
     */
    public ClassTester<?> getClassTester() {
        return classTester;
    }

    /**
     * Set {@link #classTester} to the given value
     *
     * @param classTester
     *            the new Class Tester
     */
    public void setClassTester(ClassTester<?> classTester) {
        this.classTester = classTester;
    }

    /**
     * returns the Value of {@link #methodIdentifier}
     *
     * @return the Value of {@link #methodIdentifier}
     */
    public IdentifierMatcher getMethodIdentifier() {
        return methodIdentifier;
    }

    /**
     * Set {@link #methodIdentifier} to the given value
     *
     * @param classTester
     *            the new method Identifier
     */
    public void setMethodIdentifier(IdentifierMatcher methodIdentifier) {
        this.methodIdentifier = methodIdentifier;
    }

    /**
     * returns the Value of {@link #returnType}
     *
     * @return the Value of {@link #returnType}
     */
    public Class<?> getReturnType() {
        return returnType;
    }

    /**
     * Set {@link #returnType} to the given value
     *
     * @param classTester
     *            the new return Type
     */
    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    /**
     * Generates a Message for an invalid return type
     *
     * @param methodName
     *            the Method name
     * @return the generated Message
     */
    public static String getInvalidReturnTypeMessage(String methodName) {
        return String.format("falscher Rückgabetyp für Methode %s", methodName);
    }

    /**
     * asserts that the Return type matches the expected return Type
     */
    public void assertReturnType() {
        if (returnType == null) {
            throw new RuntimeErrorException(new Error(), "Faulty Test: Cannot assert return type null");
        }
        assertMethodResolved();
        assertSame(returnType, theMethod.getReturnType(), getInvalidReturnTypeMessage(methodIdentifier.identifierName));
    }

    /**
     * Verifies that the Method was declared correctly
     *
     * @returns this
     */
    public MethodTester verify() {
        if (!methodResolved()) {
            resolveMethod();
        }
        if (accessModifier >= 0) {
            assertAccessModifier();
        }
        assertParametersMatch();
        assertReturnType();
        return this;
    }

    /**
     * returns the Value of {@link #parameters}
     *
     * @return the Value of {@link #parameters}
     */
    public ArrayList<ParameterMatcher> getParameters() {
        return parameters;
    }

    /**
     * Set {@link #parameters} to the given value
     *
     * @param classTester
     *            the new parameters
     */
    public void setParameters(ArrayList<ParameterMatcher> parameters) {
        this.parameters = parameters;
    }

    /**
     * returns the Value of {@link #theMethod}
     *
     * @return the Value of {@link #theMethod}
     */
    public Method getTheMethod() {
        return theMethod;
    }

    /**
     * Set {@link #theMethod} to the given value
     *
     * @param classTester
     *            the new Method
     */
    public void setTheMethod(Method theMethod) {
        this.theMethod = theMethod;
    }

    /**
     * Adds expected Parameter Matchers to {@link #parameters}
     *
     * @param interfaceMatcher
     *            the Interface Metchers to add
     */
    public void addParameter(ParameterMatcher... interfaceMatcher) {
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        parameters.addAll(Arrays.asList(interfaceMatcher));
    }

    /**
     * Adds expected Parameter Matchers to {@link #parameters}
     *
     * @param type
     *            The expected parameter type
     * @param name
     *            The Name to match
     * @param similarity
     *            The Minimum similarity required
     */
    public void addParameter(Class<?> type, String name, double similarity) {
        addParameter(new ParameterMatcher(name, similarity, type));
    }

    /**
     * Adds expected Parameter Matchers to {@link #parameters}
     *
     * @param type
     *            The expected parameter type
     */
    public void addParameter(Class<?> type) {
        addParameter(new ParameterMatcher(null, 1, type));
    }

    /**
     * Generates a Should Not Have Parameter Message
     *
     * @param methodName
     *            the Method name
     * @return the generated Message
     */
    public static String getShouldNotHaveParameterMessage(String methodName) {
        return String.format("Methode %s sollte keine Parameter haben.", methodName);
    }

    /**
     * Counts the matching Parameters
     *
     * @param expectedParametes
     *            the Expected Parameter List
     * @param actualParameters
     *            the Actual Parameter List
     * @param ignoreNames
     *            whether to ignore Parameter Names
     * @return the Amount of matching Parameters
     */
    public static int countMatchingParameters(
        ArrayList<ParameterMatcher> expectedParametes,
        ArrayList<Parameter> actualParameters,
        boolean ignoreNames) {
        int count = 0;
        for (int i = 0; i < expectedParametes.size(); i++) {
            var matcher = expectedParametes.get(i);
            var param = actualParameters.get(i);
            if (param.getType() != matcher.parameterType) {
                continue;
            }
            if (!ignoreNames && matcher.identifierName != null && matcher.similarity > 0) {
                if (TestUtils.similarity(matcher.identifierName, param.getName()) < matcher.similarity) {
                    continue;
                }
            }
            count++;
        }
        return count;
    }

    /**
     * Counts the matching Parameters
     *
     * @param m
     *            The Method to verify
     * @param methodName
     *            The expected Method name
     * @param parameters
     *            the Expected Parameter List
     * @param ignoreNames
     *            whether to ignore Parameter Names
     * @return the Amount of matching Parameters
     */
    public static int countMatchingParameters(Method m, String methodName, ArrayList<ParameterMatcher> parameters,
        boolean ignoreNames) {
        assertMethodNotNull(m, methodName);
        if (parameters == null || parameters.isEmpty()) {
            return 0;
        }
        return countMatchingParameters(parameters, new ArrayList<>(List.of(m.getParameters())), ignoreNames);
    }

    /**
     * assert that the Method Parameters match
     *
     * @param expectedParameters
     *            the expected Parameter List
     * @param actualParamters
     *            the actual Parameter List
     * @param ignoreNames
     *            whether to ignore Parameter Names
     */
    public static void assertParametersMatch(ArrayList<ParameterMatcher> expectedParameters,
        ArrayList<Parameter> actualParamters,
        boolean ignoreNames) {

        if (expectedParameters == null || expectedParameters.isEmpty()) {
            assertTrue(actualParamters == null || actualParamters.isEmpty(),
                "Es sollen keine Parameter vorhanden sein.");
        } else {
            for (int i = 0; i < expectedParameters.size(); i++) {
                var matcher = expectedParameters.get(i);
                assertTrue(i < actualParamters.size(), "Zu wenige Parameter.");
                var param = actualParamters.get(i);
                assertSame(param.getType(), matcher.parameterType, "Falscher Parametertyp an Index " + "i.");
                if (!ignoreNames && param.isNamePresent() && matcher.identifierName != null && matcher.similarity > 0) {
                    assertTrue(TestUtils.similarity(matcher.identifierName, param.getName()) >= matcher.similarity,
                        "Falscher Parametername. Erwartet: " + matcher.identifierName + ", Erhalten: "
                            + param.getName());
                }
            }
            assertEquals(actualParamters.size(), expectedParameters.size(),
                "Die folgenden Parameter waren nicht gefrdert:"
                    + actualParamters.subList(expectedParameters.size(), actualParamters.size()).toString());
        }
    }

    /**
     * assert that the Method Parameters match
     *
     * @param m
     *            The Method to verify
     * @param methodName
     *            The expected Method name
     * @param parameters
     *            the Expected Parameter List
     * @param ignoreNames
     *            whether to ignore Parameter Names
     */
    public static void assertParametersMatch(Method m, String methodName, ArrayList<ParameterMatcher> parameters,
        boolean ignoreNames) {
        assertMethodNotNull(m, methodName);
        assertParametersMatch(parameters, new ArrayList<>(List.of(m.getParameters())), ignoreNames);
    }

    /**
     * assert that the Method Parameters match with {@link #parameters}
     */
    public void assertParametersMatch() {
        assertParametersMatch(theMethod, methodIdentifier.identifierName, parameters, false);
    }

    /**
     * Generates a Method not found Message
     *
     * @param methodName
     *            the expecteed Method name
     * @return the generated Message
     */
    public static String getMethodNotFoundMessage(String methodName) {
        return String.format("Methode %s existiert nicht.", methodName);
    }

    /**
     * Generates a Method not found Message
     *
     * @return the generated Message
     */
    public String getMethodNotFoundMessage() {
        return getMethodNotFoundMessage(methodIdentifier.identifierName);
    }

    /**
     * returns {@code true} if {@link #theMethod} is not {@code null}
     *
     * @return {@code true} if {@link #theMethod} is not {@code null}
     */
    public boolean methodResolved() {
        return theMethod != null;
    }

    /**
     * Assert that a given method is not {@code null}
     *
     * @param m
     *            the Method
     * @param name
     *            the expected Method name
     */
    public static void assertMethodNotNull(Method m, String name) {
        assertNotNull(m, getMethodNotFoundMessage(name));

    }

    /**
     * Assert that the method is resolved
     */
    public void assertMethodResolved() {
        assertTrue(methodResolved(), getMethodNotFoundMessage());
    }

    /**
     * Generates a Class tester null message
     *
     * @param methodName
     *            the expected Method name
     * @return the generated message
     */
    public static String getClassTesterNullMessage(String methodName) {
        return String.format("Fehlerhafter Test für Methode %s: Kein Klassentester gegeben.", methodName);
    }

    /**
     * Assert that {@link #classTester} is not {@code null}
     */
    public void assertClassTesterNotNull() {
        assertNotNull(classTester, getClassTesterNullMessage(methodIdentifier.identifierName));
    }

    /**
     * returns {@code true} if {@link #classTester} is not
     * {@code null} and {@link ClassTester#class_resolved} returns true
     *
     * @return {@code true} if {@link #classTester} is not {@code null}
     */
    public boolean classResolved() {
        return classTester != null && classTester.class_resolved();
    }

    /**
     * Asserts that {@link ClassTester#classInstance} is not {@code null}
     */
    public void assertClassResolved() {
        assertClassTesterNotNull();
        classTester.assertClassResolved();
    }

    /**
     * returns {@code true}, if the Method is invokable.
     *
     * <br>
     * </br>
     * To be exact: returns {@code true} if
     * {@link #classTester} {@link #theMethod} and
     * {@link ClassTester#classInstance} are
     * resolved
     *
     * @return returns {@code true}, if the Method is invokable.
     */
    public boolean invokeable() {
        return classResolved() && classTester.classInstanceResolved() && methodResolved()
            && classTester.classInstanceResolved();
    }

    /**
     * Asserts that the Method is invokable.
     *
     * To be exact: asserts that {@link #classTester} {@link #theMethod} and
     * {@link ClassTester#classInstance} are resolved
     */
    public void assertInvokeable() {
        assertClassResolved();
        classTester.assertclassInstanceResolved();
        assertMethodResolved();
    }

    /**
     * Invokes {@link #theMethod} using {@link #classTester}
     *
     * @param params
     *            the Parameters used for invoking
     * @return the Returned Value of the Method
     */
    public Object invoke(Object... params) {
        assertInvokeable();
        assertDoesNotThrow(() -> theMethod.setAccessible(true), "Konnte Methode nicht ausführen.");
        Object returnValue = null;
        try {
            returnValue = theMethod.invoke(classTester.getClassInstance(), params);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            fail("Method Could not be invoked.", e);
        }
        return returnValue;
    }

    /**
     * Gets the Invocations of the Method
     *
     * @return the Invocations of the Method
     */
    public List<Invocation> getInvocations() {
        assertMethodResolved();
        classTester.assertSpied();
        return classTester.getMockingDetails().getInvocations().stream()
            .filter(x -> x.getMethod().getName().equals(getTheMethod().getName())).collect(Collectors.toList());
    }

    /**
     * Gets the Invocation Count (How often Has the Method been invoked?)
     *
     * @return the Invocation Count
     */
    public int getInvocationCount() {
        return getInvocations().size();
    }

    /**
     * Gets random Valid Parameter Values
     *
     * @return the Random Parameters
     */
    public Object[] getRandomParams() {
        return Arrays.stream(getTheMethod().getParameters()).map(x -> ClassTester.getRandomValue(x.getType()))
            .toArray();
    }

    /**
     * {@link #theMethod} using {@link #classTester} with Random parameters
     *
     * @return the Returned Value of the Method
     */
    public Object invokeWithRandomParams() {
        assertMethodResolved();
        return invoke(getRandomParams());
    }

    // public boolean needsJavadoc() {
    // assertMethodResolved();
    // // theMethod.
    // }

    public static String safeArrayToString(Object... array) {
        var paramsString = "[]";
        if (array != null) {
            try {
                paramsString = Arrays.toString(array);
            } catch (Exception e) {
                Arrays.stream(
                    array).map(x -> x.getClass().getName() + "@" + Integer.toHexString(x.hashCode()))
                    .collect(Collectors.joining(", ", "[", "]"));
            }
        }
        return paramsString;
    }

    /**
     * Asserts the Return Value of an invokation with the given parameters
     *
     * @param expected
     *            the expected Return value
     * @param additionalMessage
     *            an Additional Message Text
     * @param params
     *            the Parameters used for invokation
     */
    public void assertReturnValueEquals(Object expected, String additionalMessage, Object... params) {

        assertEquals(expected, invoke(params), "Falsche Rückgabe bei Methode" + getMethodIdentifier().identifierName
            + (params.length > 0 ? "mit Parameter(n):" + safeArrayToString(params) : "") + additionalMessage);
    }

    /**
     * Asserts the Return Value of an invokation with the given parameters
     *
     * @param expected
     *            the expected Return value
     * @param params
     *            the Parameters used for invokation
     */
    public void assertReturnValueEquals(Object expected, Object... params) {
        assertReturnValueEquals(expected, "", params);
    }

    /**
     * Returns the Value of {@link #accessModifier}
     *
     * @return the Value of {@link #accessModifier}
     */
    public int getAccessModifier() {
        return accessModifier;
    }

    /**
     * Sets {@link #accessModifier} to the given Value
     *
     * @param accessModifier
     *            the new Access Modifier
     */
    public void setAccessModifier(int accessModifier) {
        this.accessModifier = accessModifier;
    }

    /**
     * Asserts the actual access Modifier matches {@link #accessModifier}
     */
    public void assertAccessModifier() {
        if (accessModifier >= 0) {
            TestUtils.assertModifier(accessModifier, theMethod);
        }
    }

    /**
     * Gets all Fields from a given Class and its superclasses recursively
     *
     * @param methods
     *            the fields so far (initially give it new ArrayList<>())
     * @param clazz
     *            the Class to search
     * @return all Fields from a given Class and its superclasses recursively
     */
    private static ArrayList<Method> getAllMethods(ArrayList<Method> methods, Class<?> clazz) {
        methods.addAll(Arrays.asList(clazz.getDeclaredMethods()));

        if (clazz.getSuperclass() != null) {
            getAllMethods(methods, clazz.getSuperclass());
        }

        return methods;
    }

    /**
     * Gets all Fields from a given Class and its superclasses recursively
     *
     * @param clazz
     *            the Class to search
     * @return all Fields from a given Class and its superclasses recursively
     */
    public static ArrayList<Method> getAllMethods(Class<?> clazz) {
        return getAllMethods(new ArrayList<>(), clazz);
    }

    /**
     * Resolve the Method with tolerances
     *
     * <br>
     * </br>
     * The Method is first searched by name using using
     * {@link TestUtils#similarity(String, String)}. If Multiple overloads are found
     * then the function with the most matching parameters according to
     * {@link #countMatchingParameters(Method, String, ArrayList, boolean)} is
     * chosen.
     *
     * @param theClass
     *            The Class to search in
     * @param methodName
     *            The expected Method name
     * @param similarity
     *            The minimum required similarity
     * @param parameters
     *            The expected Parameters
     * @param allowSuperClass
     *            whether to search in Super classes as well
     * @return the resolved Method
     * @see TestUtils#similarity(String, String)
     * @see #countMatchingParameters(Method, String, ArrayList, boolean)
     */
    public Method resolveMethod(Class<?> theClass, String methodName, double similarity,
        ArrayList<ParameterMatcher> parameters, boolean allowSuperClass) {
        similarity = Math.max(0, Math.min(similarity, 1));
        ClassTester.assertClassNotNull(theClass, "zu Methode " + methodName);
        ArrayList<Method> methods = allowSuperClass ? getAllMethods(theClass)
            : new ArrayList<>(Arrays.asList(theClass.getDeclaredMethods()));
        var bestMatch = methods.stream()
            .sorted((x, y) -> Double.valueOf(TestUtils.similarity(methodName, y.getName()))
                .compareTo(TestUtils.similarity(methodName, x.getName())))
            .findFirst().orElse(null);
        assertMethodNotNull(bestMatch, methodName);
        var sim = TestUtils.similarity(bestMatch.getName(), methodName);
        assertTrue(sim >= similarity, getMethodNotFoundMessage() + "Ähnlichster Methodenname:" + bestMatch.getName()
            + " with " + sim + " similarity.");
        if (parameters != null) {
            // Account for overloads
            var matches = methods.stream().filter(x -> TestUtils.similarity(methodName, x.getName()) == sim)
                .collect(Collectors.toCollection(ArrayList::new));
            if (matches.size() > 1) {
                // Find Best match according to parameter options
                bestMatch = matches.stream()
                    .sorted((x, y) -> Integer.valueOf(countMatchingParameters(y, methodName, parameters, true))
                        .compareTo(countMatchingParameters(x, methodName, parameters, true)))
                    .findFirst().orElse(null);
            }
        }

        return theMethod = bestMatch;
    }

    /**
     * Resolve the Method with tolerances
     *
     * <br>
     * </br>
     * The Method is first searched by name using using
     * {@link TestUtils#similarity(String, String)}. If Multiple overloads are found
     * then the function with the most matching parameters according to
     * {@link #countMatchingParameters(Method, String, ArrayList, boolean)} is
     * chosen.
     *
     * @param theClass
     *            The Class to search in
     * @param methodName
     *            The expected Method name
     * @param similarity
     *            The minimum required similarity
     * @param parameters
     *            The expected Parameters
     * @return the resolved Method
     * @see TestUtils#similarity(String, String)
     * @see #countMatchingParameters(Method, String, ArrayList, boolean)
     */
    public Method resolveMethod(Class<?> theClass, String methodName, double similarity,
        ArrayList<ParameterMatcher> parameters) {
        return resolveMethod(theClass, methodName, similarity, parameters, false);
    }

    /**
     * Resolve the Method with tolerances
     *
     * <br>
     * </br>
     * The Method is first searched by name using using
     * {@link TestUtils#similarity(String, String)}. If Multiple overloads are found
     * then the function with the most matching parameters according to
     * {@link #countMatchingParameters(Method, String, ArrayList, boolean)} is
     * chosen.
     *
     * @return the resolved Method
     * @see TestUtils#similarity(String, String)
     * @see #countMatchingParameters(Method, String, ArrayList, boolean)
     */
    public Method resolveMethod() {
        assertClassTesterNotNull();
        if (!classResolved()) {
            classTester.resolveClass();
        }
        return resolveMethod(classTester.theClass, methodIdentifier.identifierName, methodIdentifier.similarity,
            parameters, allowSuperClass);
    }

    /**
     * Assures that the Method has been resolved
     *
     * @return the Method
     */
    public MethodTester assureMethodResolved() {
        if (!methodResolved()) {
            resolveMethod();
        }
        return this;
    }

    /**
     * Gets Method Documentation for JavaDoc
     *
     * @param d
     *            the Source Documentation
     * @return the Method Documentation
     */
    public MethodDocumentation getMethodDocumentation(SourceDocumentation d) {
        try {
            classTester.assureClassResolved();
            var resolvedMethod = assureMethodResolved().getTheMethod();
            return d.forTopLevelType(classTester.getTheClass().getName()).forMethod(
                resolvedMethod.getName(), resolvedMethod.getParameterTypes());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Resolve the Method with tolerances
     *
     * <br>
     * </br>
     * The Method is first searched by name using using
     * {@link TestUtils#similarity(String, String)}. If Multiple overloads are found
     * then the function with the most matching parameters according to
     * {@link #countMatchingParameters(Method, String, ArrayList, boolean)} is
     * chosen.
     *
     * @param similarity
     *            The minimum required similarity
     * @return the resolved Method
     * @see TestUtils#similarity(String, String)
     * @see #countMatchingParameters(Method, String, ArrayList, boolean)
     */
    public Method resolveMethod(double similarity) {
        return resolveMethod(classTester.theClass, methodIdentifier.identifierName, similarity, parameters);
    }
}
