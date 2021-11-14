package h05;

/**
 * Represents an Animal Class
 */
public abstract class Animal {
    protected AnimalType animalType;

    /**
     * @return AnimalClass The Animal Class Type
     */
    public AnimalType getAnimalType() {
        return animalType;
    }

    /**
     * Lets the Animal Move
     */
    public abstract String letMeMove();
}
