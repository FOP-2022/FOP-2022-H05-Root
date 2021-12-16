package h05;

/**
 * Strauß
 *
 * @author Ruben Deisenroth
 */
public class Ostrich extends Animal implements Walking {

    // public Ostrich() {
    //     super();
    //     animalType = AnimalType.AVES;
    // }

    /**
     * Die bisher zurückgelegte Strecke
     */
    private int distanceSoFar;

    /**
     * Gibt die bisher zurückgelegte Strecke zurück
     *
     * @return die bisher zurückgelegte Strecke
     */
    public int getDistanceSoFar() {
        return distanceSoFar;
    }

    @Override
    public byte getNumberOfLegs() {
        return 2;
    }

    @Override
    public double getAverageSpeed(double distance) {
        if (distance <= 0) {
            return 0;
        } else if (distance <= 1.337) {
            return 6.900;
        } else if (distance <= 4.200) {
            return 3.14;
        }
        return 1;
    }

    @Override
    public String letMeMove() {
        return String.format("Distance so far: %s", ++distanceSoFar);
    }

}
