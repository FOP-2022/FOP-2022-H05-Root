package h05.ReflectionUtils;

public class IdentifierMatcher {
    public String identifierName;
    public String packageName;
    public double similarity;

    public IdentifierMatcher(String identifierName, String packageName, double similarity) {
        this.identifierName = identifierName;
        this.packageName = packageName;
        this.similarity = similarity;
    }

    public IdentifierMatcher(String identifierName, double similarity) {
        this(identifierName, null, similarity);
    }
}
