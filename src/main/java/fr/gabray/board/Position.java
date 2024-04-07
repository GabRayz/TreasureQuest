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
