package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

public final class Mountain extends Entity {

    public Mountain(@NotNull Board board, @NotNull Position position) {
        super(board, position);
    }

    @Override
    public @NotNull String serialize() {
        return String.format("M - %d - %d", getPosition().x(), getPosition().y());
    }
}
