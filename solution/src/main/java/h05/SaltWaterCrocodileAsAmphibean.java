package h05;

public class SaltWaterCrocodileAsAmphibean implements Amphibean {

    private SaltWaterCrocodile salty;

    public SaltWaterCrocodileAsAmphibean(SaltWaterCrocodile salty) {
        super();
        this.salty = salty;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == null || obj instanceof SaltWaterCrocodile) && salty == obj;
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
    public Environment getPreferredEnvironment() {
        return Environment.IN_WATER;
    }

}
