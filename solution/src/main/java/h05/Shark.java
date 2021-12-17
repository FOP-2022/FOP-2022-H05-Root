package h05;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Represents a Shark (Hai)
 *
 * @author Ruben Deisenroth
 */
public class Shark extends Animal implements Swimming, IntConsumer {

    /**
     * The Specific Shark Species
     */
    private short specificSpecies;
    private int x, y, degreeOfHunger;

    /**
     * Creates a new {@link Shark}
     *
     * @param specificSpecies
     *            the specific Shark Species, stored in
     *            {@link #specificSpecies}
     */
    public Shark(short specificSpecies) {
        super();
        setSpecificSpecies(specificSpecies);
        animalType = AnimalType.CHONDRICHTHYES;
        degreeOfHunger = 10;
    }

    /**
     * Gets the Specific Species
     *
     * @return the Specific Species
     */
    public short getSpecificSpecies() {
        return specificSpecies;
    }

    /**
     * Gets the X-Coordinate
     *
     * @return the X-Coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the YCoordinate
     *
     * @return the Y-Coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the Degree of Hunger
     *
     * @return the Degree of Hunger
     */
    public int getDegreeOfHunger() {
        return degreeOfHunger;
    }

    @Override
    public void accept(int reductionOfHunger) {
        degreeOfHunger -= reductionOfHunger > 0 ? reductionOfHunger : 0;
    }

    @Override
    public boolean canLiveInSaltWater() {
        return IntStream.of(2, 5, 9).anyMatch(x -> x == specificSpecies);
    }

    @Override
    public boolean canLiveInFreshWater() {
        return Math.abs(specificSpecies % 4) == 2;
        // return specificSpecies % 2 == 0 && specificSpecies % 4 != 0;
    }

    @Override
    public void letMeSwim(char distance, double x, double y) {
        degreeOfHunger++;
        this.x += x * distance;
        this.y += y * distance;
    }

    @Override
    public String letMeMove() {
        degreeOfHunger++;
        int oldX = x, oldY = y;
        letMeSwim((char) ThreadLocalRandom.current().nextInt(1, 6), ThreadLocalRandom.current().nextInt(1, 6),
            ThreadLocalRandom.current().nextInt(1, 6));
        return String.format("%d -> %d and %d -> %d", oldX, x, oldY, y);
    }

    /**
     * Sets {@link #specificSpecies} to the given value, if it's between {@code 0}
     * and {@code 10}
     *
     * @param specificSpecies
     *            the Specific Shark Species
     * @return the Specific Shark Species
     */
    public short setSpecificSpecies(short specificSpecies) {
        short oldSpecies = this.specificSpecies;
        this.specificSpecies = (short) Math.max(0, Math.min(specificSpecies, 10));
        return oldSpecies;
    }

}
