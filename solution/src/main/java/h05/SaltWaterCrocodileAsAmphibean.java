package h05;

/**
 * A {@link SaltWaterCrocodile} as {@link Amphibean}
 *
 * @author Ruben Deisenroth
 */
public class SaltWaterCrocodileAsAmphibean implements Amphibean, Cloneable {

    /**
     * The {@link SaltWaterCrocodile}
     */
    private SaltWaterCrocodile salty;

    /**
     * Creates a new {@link SaltWaterCrocodileAsAmphibean}
     *
     * @param salty
     *            the {@link SaltWaterCrocodileAsAmphibean}
     */
    public SaltWaterCrocodileAsAmphibean(Animal a) {
        super();
        if (a instanceof SaltWaterCrocodile) {
            salty = (SaltWaterCrocodile) a;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null && obj instanceof SaltWaterCrocodileAsAmphibean)
            && salty == ((SaltWaterCrocodileAsAmphibean) obj).salty;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new SaltWaterCrocodileAsAmphibean(salty);
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
    public void letMeSwim(char distance, double x, double y) {
        salty.letMeSwim(distance, x, y);
    }

    @Override
    public EnvironmentType getPreferredEnvironmentType() {
        return EnvironmentType.IN_WATER;
    }

}
