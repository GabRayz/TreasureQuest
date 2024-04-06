package fr.gabray.parser;

import fr.gabray.board.Board;
import fr.gabray.board.Entity;
import fr.gabray.board.Treasure;
import org.jetbrains.annotations.NotNull;

public class TreasureBuilder extends EntityBuilder<TreasureBuilder> {
    @Override
    protected TreasureBuilder self() {
        return this;
    }

    @Override
    public Entity build(@NotNull Board board) {
        return new Treasure(board, position);
    }
}
