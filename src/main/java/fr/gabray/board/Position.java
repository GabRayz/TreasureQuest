package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

public record Position(int x, int y) {

    @NotNull
    public static Position of(int x, int y) throws IllegalArgumentException {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Position should not be negative");
        return new Position(x, y);
    }
}
