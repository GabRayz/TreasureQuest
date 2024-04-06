package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

public record Position(int x, int y) {

    @NotNull
    public static Position of(int i, int i1) {
        return new Position(i, i1);
    }
}
