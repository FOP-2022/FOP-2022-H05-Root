package h05;

/**
 * A Zoo in which only the fittest Animals can survive.
 *
 * @author Ruben Deisenroth
 */
public class SurvivalOfTheFittestZoo implements Zoo {

    @Override
    public boolean canLiveTogether(Animal a1, Animal a2) {
        return true;
    }

    @Override
    public boolean isAllowed(Animal a) {
        // Der Zoobesitzer mag keine Vögel
        return a.getAnimalType() != AnimalType.AVES;
    }

}
