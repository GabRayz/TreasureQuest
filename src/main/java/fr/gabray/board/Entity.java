package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

public abstract class Entity {

    protected final Board board;
    protected Position position;

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

    @NotNull
    public abstract String serialize();
}
