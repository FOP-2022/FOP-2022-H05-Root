package h05;

/**
 * Ein Interface, dass die Fähigkeit Laufen beschreibt
 *
 * @author Ruben Deisenroth
 */
public interface Walking {
    /**
     * Gibt die Anzahl der Beine des {@link Animal}s an
     *
     * @return die Anzahl der Beine
     */
    byte getNumberOfLegs();

    /**
     * Gets the average Speed (m/s) archievable ofer a given distance
     *
     * @param distance the distance to walk
     * @return the average Speed over the distance
     */
    public double getAverageSpeed(double distance);
}
