package h05.ReflectionUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import h05.TestUtils;

public class MethodTester {
    IdentifierMatcher methodIdentifier;
    Method theMethod;
    int accessModifier;
    private Class<?> returnType;
    private ArrayList<ParameterMatcher> parameters;
    private ClassTester<?> classTester;

    public MethodTester(ClassTester<?> classTester, String methodName, double similarity, int accessModifier,
            Class<?> returnType, ArrayList<ParameterMatcher> parameters) {
        this.classTester = classTester;
        this.methodIdentifier = new IdentifierMatcher(methodName, similarity);
        this.accessModifier = accessModifier;
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public MethodTester(ClassTester<?> classTester, String methodName, double similarity, int accessModifier,
            Class<?> returnType) {
        this(classTester, methodName, similarity, accessModifier, returnType, null);
    }

    public MethodTester(ClassTester<?> classTester, String methodName, double similarity, int accessModifier) {
        this(classTester, methodName, similarity, accessModifier, null, null);
    }

    public MethodTester(ClassTester<?> classTester, String methodName, double similarity, Class<?> returnType,
            ArrayList<ParameterMatcher> parameters) {
        this(classTester, methodName, similarity, -1, returnType, parameters);
    }

    public MethodTester(ClassTester<?> classTester, String methodName, double similarity) {
        this(classTester, methodName, similarity, -1, null);
    }

    public MethodTester(ClassTester<?> classTester, String methodName) {
        this(classTester, methodName, 1, -1, null);
    }

    public ClassTester<?> getClassTester() {
        return classTester;
    }

    public void setClassTester(ClassTester<?> classTester) {
        this.classTester = classTester;
    }

    public IdentifierMatcher getMethodIdentifier() {
        return methodIdentifier;
    }

    public void setMethodIdentifier(IdentifierMatcher methodIdentifier) {
        this.methodIdentifier = methodIdentifier;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public static String getInvalidReturnTypeMessage(String methodName) {
        return String.format("falscher Rückgabetyp für Methode %s", methodName);
    }

    public void assertReturnType() {
        if (returnType == null) {
            throw new RuntimeErrorException(new Error(), "Faulty Test: Cannot assert return type null");
        }
        assertMethodResolved();
        assertSame(returnType, theMethod.getReturnType(), getInvalidReturnTypeMessage(methodIdentifier.identifierName));
    }

    public ArrayList<ParameterMatcher> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<ParameterMatcher> parameters) {
        this.parameters = parameters;
    }

    public Method getTheMethod() {
        return theMethod;
    }

    public void setTheMethod(Method theMethod) {
        this.theMethod = theMethod;
    }

    public void addParameter(ParameterMatcher... interfaceMatcher) {
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        parameters.addAll(Arrays.asList(interfaceMatcher));
    }

    public void addParameter(Class<?> type, String name, double similarity) {
        addParameter(new ParameterMatcher(name, similarity, type));
    }

    public void addParameter(Class<?> type) {
        addParameter(new ParameterMatcher(null, 1, type));
    }

    public static String getShouldNotHaveParameterMessage(String methodName) {
        return String.format("Methode %s sollte keine Parameter haben.", methodName);
    }

    public static int countMatchingParameters(Method m, String methodName, ArrayList<ParameterMatcher> parameters,
            boolean ignoreNames) {
        assertMethodNotNull(m, methodName);
        if (parameters == null || parameters.isEmpty()) {
            return 0;
        }
        int count = 0;
        var params = new ArrayList<>(List.of(m.getParameters()));
        for (int i = 0; i < parameters.size(); i++) {
            var matcher = parameters.get(i);
            var param = params.get(i);
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

    public static void assertParametersMatch(Method m, String methodName, ArrayList<ParameterMatcher> parameters,
            boolean ignoreNames) {
        assertMethodNotNull(m, methodName);
        var params = new ArrayList<>(List.of(m.getParameters()));
        if (parameters == null || parameters.isEmpty()) {
            assertTrue(params == null || params.isEmpty(), "Es sollen keine Interfaces implementiert werden.");
        } else {
            for (int i = 0; i < parameters.size(); i++) {
                var matcher = parameters.get(i);
                assertTrue(i < params.size(), String.format("Methode %s hat zu wenige Parameter.", methodName));
                var param = params.get(i);
                assertSame(param.getType(), matcher.parameterType, "Falscher Parametertyp an Index " + "i.");
                if (!ignoreNames && param.isNamePresent() && matcher.identifierName != null && matcher.similarity > 0) {
                    assertTrue(TestUtils.similarity(matcher.identifierName, param.getName()) >= matcher.similarity,
                            "Falscher Parametername. Erwartet: " + matcher.identifierName + ", Erhalten: "
                                    + param.getName());
                }
            }
            assertEquals(params.size(), parameters.size(), "Die folgenden Parameter waren nicht gefrdert:"
                    + params.subList(parameters.size(), params.size()).toString());
        }
    }

    public void assertParametersMatch() {
        assertParametersMatch(theMethod, methodIdentifier.identifierName, parameters, false);
    }

    public static String getMethodNotFoundMessage(String methodName) {
        return String.format("Methode %s existiert nicht.", methodName);
    }

    public String getMethodNotFoundMessage() {
        return getMethodNotFoundMessage(methodIdentifier.identifierName);
    }

    public boolean methodResolved() {
        return theMethod != null;
    }

    public static void assertMethodNotNull(Method m, String name) {
        assertNotNull(m, getMethodNotFoundMessage(name));

    }

    public void assertMethodResolved() {
        assertTrue(methodResolved(), getMethodNotFoundMessage());
    }

    public static String getClassTesterNullMessage(String methodName) {
        return String.format("Fehlerhafter Test für Methode %s: Kein Klassentester gegeben.", methodName);
    }

    public void assertClassTesterNotNull() {
        assertNotNull(classTester, getClassTesterNullMessage(methodIdentifier.identifierName));
    }

    public boolean classResolved() {
        return classTester != null && classTester.class_resolved();
    }

    public void assertClassResolved() {
        assertClassTesterNotNull();
        classTester.assertClassResolved();
    }

    public boolean invokeable() {
        return classResolved() && classTester.classInstanceResolved() && methodResolved()
                && classTester.classInstanceResolved();
    }

    public void assertInvokeable() {
        assertClassResolved();
        classTester.assertclassInstanceResolved();
        assertMethodResolved();
    }

    public Object invoke(Object... params) {
        assertInvokeable();
        assertDoesNotThrow(() -> theMethod.setAccessible(true), "Konnte Methode nicht ausführen.");
        Object returnValue = null;
        try {
            returnValue = theMethod.invoke(classTester.getClassInstance(), params);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            fail("Method Could not be invoked.");
        }
        return returnValue;
    }

    public int getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(int accessModifier) {
        this.accessModifier = accessModifier;
    }

    public void assertAccessModifier() {
        if (accessModifier >= 0) {
            TestUtils.assertModifier(accessModifier, theMethod);
        }
    }

    public Method resolveMethod(Class<?> theClass, String methodName, double similarity,
            ArrayList<ParameterMatcher> parameters) {
        similarity = Math.max(0, Math.min(similarity, 1));
        ClassTester.assertClassNotNull(theClass, "zu Methode " + methodName);
        Method[] methods = assertDoesNotThrow(() -> theClass.getDeclaredMethods());
        var bestMatch = Arrays.stream(methods)
                .sorted((x, y) -> Double.valueOf(TestUtils.similarity(methodName, y.getName()))
                        .compareTo(TestUtils.similarity(methodName, x.getName())))
                .findFirst().orElse(null);
        assertMethodNotNull(bestMatch, methodName);
        var sim = TestUtils.similarity(bestMatch.getName(), methodName);
        assertTrue(sim >= similarity, getMethodNotFoundMessage() + "Ähnlichster Methodenname:" + bestMatch.getName()
                + " with " + sim + " similarity.");
        if (parameters != null) {
            // Account for overloads
            var matches = Arrays.stream(methods).filter(x -> TestUtils.similarity(methodName, x.getName()) == sim)
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

    public Method resolveMethod() {
        assertClassTesterNotNull();
        if (!classResolved()) {
            classTester.resolveClass();
        }
        return resolveMethod(classTester.theClass, methodIdentifier.identifierName, methodIdentifier.similarity,
                parameters);
    }

    public Method resolveMethod(double similarity) {
        return resolveMethod(classTester.theClass, methodIdentifier.identifierName, similarity, parameters);
    }
}
