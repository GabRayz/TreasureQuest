package fr.gabray.parser;

import fr.gabray.board.Board;
import fr.gabray.board.Entity;
import fr.gabray.board.Mountain;
import org.jetbrains.annotations.NotNull;

public final class MountainBuilder extends EntityBuilder<MountainBuilder> {

    @Override
    protected MountainBuilder self() {
        return this;
    }

    @Override
    public Entity build(@NotNull Board board) {
        return new Mountain(board, position);
    }
}
