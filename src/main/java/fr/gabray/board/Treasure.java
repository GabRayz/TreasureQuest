package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

public class Treasure extends Entity {

    public Treasure(@NotNull Board board, @NotNull Position position) {
        super(board, position);
    }

    @Override
    public @NotNull String serialize() {
        return String.format("T - %d - %d - 1", getPosition().x(), getPosition().y());
    }
}
