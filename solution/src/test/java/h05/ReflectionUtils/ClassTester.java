package h05.ReflectionUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.stream.Stream;

import h05.TestUtils;

/**
 * A Class Tester
 *
 * @author Ruben Deisenroth
 */
public class ClassTester<T> {
    String packageName, className;
    double similarity;
    Class<T> theClass;
    int accessModifier;
    private Class<? super T> superClass;
    private ArrayList<Class<?>> implementsInterfaces;
    T classInstance;

    public ClassTester(String packageName, String className, double similarity, int accessModifier,
            Class<? super T> superClass, ArrayList<Class<?>> implementsInterfaces, T classInstance) {
        this.packageName = packageName;
        this.className = className;
        this.similarity = similarity;
        this.accessModifier = accessModifier;
        this.superClass = superClass;
        this.implementsInterfaces = implementsInterfaces;
        this.classInstance = classInstance;
    }

    public ClassTester(String packageName, String className, double similarity, int accessModifier,
            Class<? super T> superClass, ArrayList<Class<?>> implementsInterfaces) {
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
    public ArrayList<Class<?>> getImplementsInterfaces() {
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
    public void setImplementsInterfaces(ArrayList<Class<?>> implementsInterfaces) {
        this.implementsInterfaces = implementsInterfaces;
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
    public Class<T> resolveClass(String packageName, String className, double similarity) {
        final String classNotFoundMessage = String.format("Klasse %s existiert nicht.", className);
        if (similarity >= 1) {
            return theClass = (Class<T>) assertDoesNotThrow(
                    () -> Class.forName(String.format("%s.%s", packageName, className)));
        }
        var classes = assertDoesNotThrow(() -> TestUtils.getClasses(packageName));
        var bestMatch = Stream.of(classes)
                .sorted((x, y) -> Double.valueOf(TestUtils.similarity(className, x.getSimpleName()))
                        .compareTo(TestUtils.similarity(className, y.getSimpleName())))
                .findFirst().orElseGet(null);
        assertNotNull(bestMatch, classNotFoundMessage);
        assertTrue(TestUtils.similarity(bestMatch.getSimpleName(), className) >= similarity, classNotFoundMessage);
        return theClass = (Class<T>) bestMatch;
    }

    public Class<T> resolveClass() {
        return resolveClass(packageName, className, similarity);
    }

    public Class<T> resolveClass(double similarity) {
        return resolveClass(packageName, className, similarity);
    }
}
