package h05;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Ein Zoo der versucht, Konflikte zu vermeiden aber eine möglichst Große
 * Vielfalt zu bieten.
 *
 * @author Ruben Deisenroth
 */
public class FamilyFriendlyZoo implements Zoo {

    @Override
    public boolean canLiveTogether(Animal a1, Animal a2) {
        // Haie sind nicht mit anderen Knorpelfischen kompatibel
        if (a1 instanceof Shark && a2.getAnimalType() == AnimalType.CHONDRICHTHYES && !(a2 instanceof Shark)) {
            return false;
        }

        // Krokodile dürfen nicht mit Säugetieren zusammenleben
        if (a1.getAnimalType() == AnimalType.CROCODYLIDAE && a2.getAnimalType() == AnimalType.MAMMALIA) {
            return false;
        }

        // Vögel dürfen nur mit Tieren zusammenleben, die (falls Sie laufen können) bei
        // einer Strecke von 10 Metern langsamer als 3m/s sind.
        if (a1.getAnimalType() == AnimalType.AVES && (a2 instanceof Walking)
                && ((Walking) a2).getAverageSpeed(10) >= 3) {
            // Haiart 4 darf nicht mit anderen Haiarten zusammenleben
            // Krokodile Sind nur mit
            return false;
        }
        return true;
    }

    @Override
    public boolean isAllowed(Animal a) {
        // Bis auf die besonders gefährliche Heiart 4, sind alle Tiere erlaubt
        return !(a instanceof Shark && ((Shark) a).getSpecificSpecies() == 4);
    }

    /**
     * Gibt Einen Zufälligen Buchstaben zurück
     */
    public char letterOfTheDay() {
        return (char) ThreadLocalRandom.current().nextInt('a', 'z' + 1);
    }

}
