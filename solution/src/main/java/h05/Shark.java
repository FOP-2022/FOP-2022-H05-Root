package h05;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public class Shark extends Animal implements Swimming, IntConsumer {

    private short specificSpecies;
    private int x, y, degreeOfHunger;

    public Shark(short specificSpecies) {
        super();
        setSpecificSpecies(specificSpecies);
        animalClass = AnimalClass.CHONDRICHTHYES;
        degreeOfHunger = 10;
    }

    public short getSpecificSpecies() {
        return specificSpecies;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDegreeOfHunger() {
        return degreeOfHunger;
    }

    @Override
    public void accept(int reductionOfHunger) {
        degreeOfHunger -= reductionOfHunger > 0 ? reductionOfHunger : 0;
    }

    @Override
    public boolean canLiveInSaltWater() {
        degreeOfHunger++;
        return IntStream.of(2, 5, 9).anyMatch(x -> x == specificSpecies);
    }

    @Override
    public boolean canLiveInFreshWater() {
        degreeOfHunger++;
        return specificSpecies % 4 == 2;
        // return specificSpecies % 2 == 0 && specificSpecies % 4 != 0;
    }

    @Override
    public void letMeSwim(char distance, double x, double y) {
        degreeOfHunger++;
        this.x += x * distance;
        this.y += y * distance;
    }

    @Override
    String letMeMove() {
        degreeOfHunger++;
        int oldX = x, oldY = y;
        letMeSwim((char) (ThreadLocalRandom.current().nextInt('a', 'z' - 'a' + 2)),
                ThreadLocalRandom.current().nextInt(1, 6), ThreadLocalRandom.current().nextInt(1, 6));
        return String.format("%d -> %d and %d -> %d", oldX, oldY, x, y);
    }

    public short setSpecificSpecies(short specificSpecies) {
        return this.specificSpecies = (short) Math.max(0, Math.min(specificSpecies, 10));
    }

}
