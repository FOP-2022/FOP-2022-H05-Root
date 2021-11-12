package h05;

/**
 * Represents an Animal Class
 */
public abstract class Animal {
    protected AnimalClass animalClass;

    /**
     * @return AnimalClass The Animal Class Type
     */
    public AnimalClass getAnimalClass() {
        return animalClass;
    }

    /**
     * Lets the Animal Move
     */
    abstract String letMeMove();
}
