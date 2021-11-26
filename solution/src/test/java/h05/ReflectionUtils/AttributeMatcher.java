package h05.ReflectionUtils;

public class AttributeMatcher extends IdentifierMatcher {
    public int modifier;
    public Class<?> type;

    public AttributeMatcher(String name, double similarity, int modifier, Class<?> type) {
        super(name, similarity);
        this.modifier = modifier;
        this.type = type;
    }

    public AttributeMatcher(String name, double similarity, Class<?> type) {
        this(name, similarity, -1, type);
    }
}
