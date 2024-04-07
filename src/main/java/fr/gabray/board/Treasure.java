package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

public class Treasure extends Entity {

    private int count;

    public Treasure(@NotNull Board board, @NotNull Position position, int count) {
        super(board, position);
        this.count = count;
    }

    public Treasure(@NotNull Board board, @NotNull Position position) {
        this(board, position, 1);
    }

    public int getCount() {
        return count;
    }

    public int collect() {
        return --count;
    }

    @Override
    public @NotNull String serialize() {
        return String.format("T - %d - %d - %d", getPosition().x(), getPosition().y(), count);
    }
}
