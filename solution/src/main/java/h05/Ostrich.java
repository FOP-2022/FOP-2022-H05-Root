package h05;

public class Ostrich extends Animal implements Walking {

    public Ostrich() {
        super();
        animalType = AnimalType.AVES;
    }

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
        } else if (distance <= 1.19) {
            return 3.14;
        } else if (distance <= 2.38) {
            return 2.71;
        }
        return 1;
    }

    @Override
    public String letMeMove() {
        return String.format("Distance so far: %d", ++distanceSoFar);
    }

}
