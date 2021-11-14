package h05;

/**
 * Ein Interface, dass die Fähigkeit Laufen beschreibt
 *
 * @author Ruben Deisenroth
 */
public interface Walking {
    /**
     * Gibt die Anzahl der Beine des {@link Animal}s an
     * @return die Anzahl der Beine
     */
    byte getNumberOfLegs();

    double getAverageSpeed(double distance);
}
