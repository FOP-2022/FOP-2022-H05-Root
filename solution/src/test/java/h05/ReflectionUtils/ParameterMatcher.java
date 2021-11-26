package h05.ReflectionUtils;

public class ParameterMatcher extends IdentifierMatcher {
    public Class<?> parameterType;

    public ParameterMatcher(String identifierName, double similarity, Class<?> parameterType) {
        super(identifierName, similarity);
        this.parameterType = parameterType;
    }

    public ParameterMatcher(Class<?> parameterType) {
        this(null, 0, parameterType);
    }
}
