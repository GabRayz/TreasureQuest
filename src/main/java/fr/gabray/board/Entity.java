package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

public abstract class Entity {

    private final Board board;
    private final Position position;

    protected Entity(@NotNull final Board board, @NotNull final Position position) {
        this.board = board;
        this.position = position;
    }

    @NotNull
    public Board getBoard() {
        return board;
    }

    @NotNull
    public Position getPosition() {
        return position;
    }
}
