package h05;

import h05.ReflectionUtils.ClassTester;
import h05.ReflectionUtils.IdentifierMatcher;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * A Class with all the Class Testers needed for grading
 *
 * @author Ruben Deisenroth
 */
public class H05_Class_Testers {
    public final static double minSim = 0.8d;
    public final static ClassTester<?> walkingCT = new ClassTester<>("h05", "Walking", minSim,
        Modifier.PUBLIC | Modifier.ABSTRACT | Modifier.INTERFACE);
    public final static ClassTester<?> swimmingCT = new ClassTester<>("h05", "Swimming", minSim,
        Modifier.PUBLIC | Modifier.ABSTRACT | Modifier.INTERFACE);
    public final static ClassTester<?> amphibeanCT = new ClassTester<>("h05", "Amphibean", minSim,
        -1, null, new ArrayList<>(List
        .of(new IdentifierMatcher("Walking", "h05", minSim),
            new IdentifierMatcher("Swimming", "h05", minSim))));
    public final static ClassTester<?> environmentTypeCT = new ClassTester<>("h05", "EnvironmentType", minSim,
        Modifier.PUBLIC | Modifier.FINAL | TestUtils.ENUM);
    public final static ClassTester<?> animalTypeCT = new ClassTester<>("h05", "AnimalType", minSim,
        Modifier.PUBLIC | Modifier.FINAL | TestUtils.ENUM,
        null, new ArrayList<>());
    public final static ClassTester<?> animalCT = new ClassTester<>("h05", "Animal", minSim,
        Modifier.PUBLIC | Modifier.ABSTRACT);
    public final static ClassTester<?> ostrichCT = new ClassTester<>("h05", "Ostrich", minSim, Modifier.PUBLIC,
        animalCT.getTheClass(), new ArrayList<>(List.of(new IdentifierMatcher("Walking", "h05", minSim))));
    public final static ClassTester<?> sharkCT = new ClassTester<>("h05", "Shark",
        minSim, -1,
        animalCT.getTheClass(),
        new ArrayList<>(List.of(new IdentifierMatcher("Swimming", "h05", minSim),
            new IdentifierMatcher("IntConsumer", "java.util.function.IntConsumer", minSim))));
    public final static ClassTester<?> saltWaterCrocodileCT = new ClassTester<>("h05", "SaltWaterCrocodile", minSim,
        -1,
        animalCT.getTheClass(),
        new ArrayList<>(List.of(new IdentifierMatcher("Swimming", "h05", minSim),
            new IdentifierMatcher("Walking", "h05", minSim))));
    public final static ClassTester<?> saltWaterCrocodileAsAmphibeanCT = new ClassTester<>("h05",
        "SaltWaterCrocodileAsAmphibean", minSim, -1, null,
        new ArrayList<>(List.of(new IdentifierMatcher("Amphibean", "h05", minSim),
            new IdentifierMatcher("Cloneable", "h05", minSim))));
    public final static ClassTester<?> zooCT = new ClassTester<>("h05", "Zoo", minSim,
        Modifier.PUBLIC | Modifier.ABSTRACT | Modifier.INTERFACE);
    public final static ClassTester<?> survivalOfTheFittestZooCT = new ClassTester<>("h05", "SurvivalOfTheFittestZoo",
        minSim, -1, null, new ArrayList<>(List.of(
        new IdentifierMatcher("Zoo", minSim))));
    public final static ClassTester<?> familyFriendlyZooCT = new ClassTester<>("h05", "FamilyFriendlyZoo", minSim,
        -1, null, new ArrayList<>(List.of(
        new IdentifierMatcher("Zoo", minSim))));
}
