package h05;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.sourcegrade.jagr.api.testing.TestCycle;
import org.sourcegrade.jagr.api.testing.extension.TestCycleResolver;

/**
 * Alex will mir keinen Statischen Zugriff auf den TestCycleResolver geben, also
 * muss ich diesen Umständlichen Weg gehen^^ #MehrIndekrektionsEbenen
 *
 * @author Ruben Deisenroth
 */
public class Alex_fix_test_initialisation {
    /**
     * The {@link TestCycle} that is **not** statically accessible because of Alex
     */
    private static TestCycle TEST_CYCLE;

    /**
     * Resolves the {@link TestCycle}, because more indirections = more fun
     *
     * @param cycle
     */
    @BeforeAll()
    @ExtendWith(TestCycleResolver.class)
    public static void resolveTestCycle(TestCycle cycle) {
        TEST_CYCLE = cycle;
    }

    /**
     * Gets the current {@link TestCycle}
     *
     * @return the current {@link TestCycle}
     */
    public static TestCycle getTestCycle() {
        return TEST_CYCLE;
    }
}
