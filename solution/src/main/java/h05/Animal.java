package h05;

/**
 * Represents an Animal Class
 *
 * @author Ruben Deisenroth
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
     * @return The Animal Movement
     */
    abstract String letMeMove();

    @Override
    public String toString() {
        return String.format("My species is called %s which is part of animal type %s.",
        getClass().getSimpleName(),
                animalType == null ? "Null" : animalType.name().substring(0, 1) + animalType.name().substring(1).toLowerCase());
    }
}
