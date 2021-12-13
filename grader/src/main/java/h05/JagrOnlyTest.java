package h05;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * A Test that only works with Jagr and is skipped when using the regular Junit
 * Runner.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(JagrOnlyTest.JagrOnlyTestConditionExtension.class)
@Test
public @interface JagrOnlyTest {
    public class JagrOnlyTestConditionExtension implements ExecutionCondition {
        @Override
        public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
            if (TestUtils.isAutograderRun()) {
                return ConditionEvaluationResult.enabled("Autograder present");
            } else {
                return ConditionEvaluationResult.disabled("No Test Cycle Present");
            }
        }
    }
}
