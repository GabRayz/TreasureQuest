package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

public enum Direction {
    SOUTH("S"),
    ;

    private final String label;

    Direction(String label) {
        this.label = label;
    }

    @NotNull
    public static Direction fromLabel(@NotNull final String label) throws IllegalArgumentException {
        for (Direction value : values()) {
            if (value.label.equals(label))
                return value;
        }
        throw new IllegalArgumentException("Unknown Direction " + label);
    }
}
