package h05.ReflectionUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

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
        this(packageName, className, similarity, accessModifier, null, null, null);
    }

    public ClassTester(String packageName, String className, double similarity) {
        this(packageName, className, similarity, -1, null, null, null);
    }

    public ClassTester(String packageName, String className) {
        this(packageName, className, 1, -1, null, null, null);
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
                var sim = TestUtils.similarity(bestMatch.getSimpleName(), matcher.identifierName);
                assertNotNull(bestMatch, getInterfaceNotImplementedMessage(matcher.identifierName));
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

    public String getClassNotFoundMessage() {
        return String.format("Klasse %s existiert nicht.", classIdentifier.identifierName);
    }

    public String getInterfaceNotImplementedMessage(String interfaceName) {
        return String.format("Interface %s wird nicht erweitert.", interfaceName);
    }

    public void assertClassResolved() {
        assertTrue(class_resolved(), getClassNotFoundMessage());
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

    public Object getClassInstance() {
        return classInstance;
    }

    public void setClassInstance(T classInstance) {
        this.classInstance = classInstance;
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

    public void assertIsInterface() {
        assertClassResolved();
        assertTrue(theClass.isInterface(), String.format("%s ist kein Interface.", classIdentifier.identifierName));
    }

    public void assertIsEnum() {
        assertClassResolved();
        assertTrue(theClass.isEnum(), String.format("%s ist kein Enum.", classIdentifier.identifierName));
    }

    public void assertIsPlainClass() {
        assertClassResolved();
        assertFalse(theClass.isInterface(),
                String.format("%s sollte kein Interface sein.", classIdentifier.identifierName));
        assertFalse(theClass.isEnum(), String.format("%s sollte kein Enum sein.", classIdentifier.identifierName));
    }

}
