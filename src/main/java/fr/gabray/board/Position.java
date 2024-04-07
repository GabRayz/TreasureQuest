package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

/**
 * A pair of positive coordinates
 *
 * @param x
 * @param y
 */
public record Position(int x, int y) {

    /**
     * Return a new Position resulting of a forward move in the given direction
     *
     * @param direction Direction of the move
     * @return New Position, or same if the move would have resulting in a negative coordinate.
     */
    public Position moveInDirection(@NotNull final Direction direction) {
        try {
            return switch (direction) {
                case NORTH -> Position.of(x, y - 1);
                case EAST -> Position.of(x + 1, y);
                case SOUTH -> Position.of(x, y + 1);
                case WEST -> Position.of(x - 1, y);
            };
        } catch (IllegalArgumentException e) {
            return this;
        }
    }

    /**
     * Return a Position from the given coordinates
     *
     * @param x X-axis coordinate
     * @param y Y-axis coordinate
     * @return New Position object
     * @throws IllegalArgumentException If x or y is negative
     */
    @NotNull
    public static Position of(int x, int y) throws IllegalArgumentException {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Position should not be negative");
        return new Position(x, y);
    }
}
