package h05;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(AutograderOnlyTest.AutograderOnlyTestConditionExtension.class)
public @interface AutograderOnlyTest {
    public class AutograderOnlyTestConditionExtension implements ExecutionCondition {
        /**
         * @AutograderOnlyTest - your own annotation
         */
        @Override
        public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
            if (TestUtils.isAutograderRun()) {
                return ConditionEvaluationResult.enabled("@AutograderOnlyTest is not present");
            } else {
                return ConditionEvaluationResult.disabled("@AutograderOnlyTest is present with valid condition");
            }
        }
    }
}
