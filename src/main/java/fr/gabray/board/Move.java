package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public enum Move {
    FORWARD("A"),
    RIGHT("D"),
    LEFT("G"),
    ;

    private final String label;

    Move(@NotNull final String label) {
        this.label = label;
    }

    @NotNull
    public static Move fromLabel(@NotNull final String label) throws IllegalArgumentException {
        for (Move value : values()) {
            if (value.label.equals(label))
                return value;
        }
        throw new IllegalArgumentException("Unknown Move " + label);
    }

    @NotNull
    public static List<Move> fromLabels(@NotNull final String label) throws IllegalArgumentException {
        final List<Move> moves = new ArrayList<>();
        for (int i = 0; i < label.length(); i++) {
            moves.add(fromLabel(String.valueOf(label.charAt(i))));
        }

        return moves;
    }
}
