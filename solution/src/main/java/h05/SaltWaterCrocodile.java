package h05;

public class SaltWaterCrocodile extends Animal implements Walking, Swimming {
    public SaltWaterCrocodile() {
        super();
        animalClass = AnimalClass.CROCODYLIDAE;
    }

    double averageSpeed;

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

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    @Override
    String letMeMove() {
        return "";
    }

}
