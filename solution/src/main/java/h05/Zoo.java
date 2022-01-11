package h05;

/**
 * @author Ruben Deisenroth
 */
public interface Zoo {
    /**
     * Returns true if two {@link Animal}s are Compatible
     *
     * @param a1 the first {@link Animal}
     * @param a2 the second {@link Animal}
     * @return true if two {@link Animal}s are Compatible
     */
    public boolean canLiveTogether(Animal a1, Animal a2);

    /**
     * Returns true if the given {@link Animal} is permitted in the Zoo
     *
     * @param a the Animal to verify
     * @return true if the given {@link Animal} is permitted in the Zoo
     */
    public boolean isAllowed(Animal a);
}
