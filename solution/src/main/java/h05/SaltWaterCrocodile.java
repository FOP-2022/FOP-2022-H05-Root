package h05;

/**
 * A Salt Water Crocodile(Salzwasserkrokodil)
 *
 * @author Ruben Deisenroth
 */
public class SaltWaterCrocodile extends Animal implements Walking, Swimming {
    /**
     * Creates a new {@link SaltWaterCrocodile}
     */
    public SaltWaterCrocodile() {
        super();
        animalType = AnimalType.CROCODYLIDAE;
    }

    /**
     * The Average Speed
     */
    static double averageSpeed;

    @Override
    public boolean canLiveInSaltWater() {
        return true;
    }

    @Override
    public boolean canLiveInFreshWater() {
        return true;
    }

    @Override
    public void letMeSwim(char distance, double x, double y) {
        // Nothing to see here
    }

    @Override
    public byte getNumberOfLegs() {
        return 4;
    }

    @Override
    public double getAverageSpeed(double distance) {
        return averageSpeed;
    }

    /**
     * Overwrites {@link #averageSpeed} with the given value
     *
     * @param averageSpeed the new average Speed
     */
    public void setAverageSpeed(double averageSpeed) {
        SaltWaterCrocodile.averageSpeed = averageSpeed;
    }

    @Override
    public String letMeMove() {
        return "";
    }
}
