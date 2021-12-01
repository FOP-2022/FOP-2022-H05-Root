package h05.ReflectionUtils;

/**
 * A Parameter Matcher based on {@link IdentifierMatcher}
 *
 * @see IdentifierMatcher
 * @author Ruben Deisenroth
 */
public class ParameterMatcher extends IdentifierMatcher {
    /**
     * The expected parameter type
     */
    public Class<?> parameterType;

    /**
     * Generates a new {@link ParameterMatcher}
     *
     * @param identifierName The Name to match
     * @param similarity     The Minimum similarity required
     * @param parameterType  The expected parameter type
     */
    public ParameterMatcher(String identifierName, double similarity, Class<?> parameterType) {
        super(identifierName, similarity);
        this.parameterType = parameterType;
    }

    /**
     * Generates a new {@link ParameterMatcher}
     *
     * @param parameterType The expected parameter type
     */
    public ParameterMatcher(Class<?> parameterType) {
        this(null, 0, parameterType);
    }
}
