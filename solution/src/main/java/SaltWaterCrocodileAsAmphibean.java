import h05.Amphibien;
import h05.Animal;
import h05.Environment;
import h05.SaltWaterCrocodile;

public class SaltWaterCrocodileAsAmphibean implements Amphibien {

    private SaltWaterCrocodile salty;

    public SaltWaterCrocodileAsAmphibean(Animal animal) {
        super();
        if (animal instanceof SaltWaterCrocodile) {
            salty = (SaltWaterCrocodile) animal;
        }
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
    public Environment wherePrefered() {
        return Environment.IN_WATER;
    }

}
