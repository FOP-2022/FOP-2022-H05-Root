package h05;

/**
 * Represents an amphibean
 *
 * @author Ruben Deisenroth
 */
public interface Amphibean extends Walking, Swimming {
    /**
     * gets the preferred {@link Environment}
     *
     * @return the preferred {@link Environment}
     */
    Environment getPreferredEnvironment();
}
