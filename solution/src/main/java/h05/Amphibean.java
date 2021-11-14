package h05;

/**
 * Das Interface Amphibean beschreibt Amphib
 * @author Ruben Deisenroth
 */
public interface Amphibean extends Walking, Swimming {
    Environment getPreferredEnvironment();
}
