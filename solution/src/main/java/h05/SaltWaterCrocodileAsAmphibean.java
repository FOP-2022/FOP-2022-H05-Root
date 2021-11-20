package h05;

/**
 * A {@link SaltWaterCrocodile} as {@link Amphibean}
 *
 * @author Ruben Deisenroth
 */
public class SaltWaterCrocodileAsAmphibean implements Amphibean {

    /**
     * The {@link SaltWaterCrocodile}
     */
    private SaltWaterCrocodile salty;

    /**
     * Creates a new {@link SaltWaterCrocodileAsAmphibean}
     *
     * @param salty the {@link SaltWaterCrocodileAsAmphibean}
     */
    public SaltWaterCrocodileAsAmphibean(Animal a) {
        super();
        if (a instanceof SaltWaterCrocodile) {
            salty = (SaltWaterCrocodile) a;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == null || obj instanceof SaltWaterCrocodile) && salty == obj;
    }

    @Override
    protected Object clone() {
        return this;
    }

    @Override
    public byte getNumberOfLegs() {
        return salty.getNumberOfLegs();
    }

    @Override
    public double getAverageSpeed(double distance) {
        return salty.getAverageSpeed(distance);
    }

    @Override
    public boolean canLiveInSaltWater() {
        return salty.canLiveInSaltWater();
    }

    @Override
    public boolean canLiveInFreshWater() {
        return salty.canLiveInFreshWater();
    }

    @Override
    public void letMeSwim(int distance, double x, double y) {
        salty.letMeSwim(distance, x, y);
    }

    @Override
    public EnvironmentType getPreferredEnvironmentType() {
        return EnvironmentType.IN_WATER;
    }

}
