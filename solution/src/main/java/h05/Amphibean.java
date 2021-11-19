package h05;

/**
 * Represents an amphibean
 *
 * @author Ruben Deisenroth
 */
public interface Amphibean extends Walking, Swimming {
    /**
     * gets the preferred {@link EnvironmentType}
     *
     * @return the preferred {@link EnvironmentType}
     */
    EnvironmentType getPreferredEnvironmentType();
}
