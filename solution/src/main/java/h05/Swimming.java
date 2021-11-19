package h05;

/**
 * Represents the Ability to swim
 *
 * @author Ruben Deisenroth
 */
public interface Swimming {
    /**
     * Returns {@code true} if the {@link Animal} can live in Salt Water
     *
     * @return {@code true} if the {@link Animal} can live in Salt Water
     */
    boolean canLiveInSaltWater();

    /**
     * Returns {@code true} if the {@link Animal} can live in fresh Water
     *
     * @return {@code true} if the {@link Animal} can live in fresh Water
     */
    boolean canLiveInFreshWater();

    /**
     * Lets the {@link Swimming} instance swim
     *
     * @param distance The distance to swim
     * @param x        the X-Coordinate of the new Position
     * @param y        the Y-Coordinate of the new Position
     */
    void letMeSwim(int distance, double x, double y);
}
