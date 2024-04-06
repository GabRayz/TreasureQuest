package fr.gabray.parser;

import fr.gabray.board.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdventurerBuilder extends EntityBuilder<AdventurerBuilder> {

    private String name;
    private Direction direction;
    private List<Move> moves;

    @Override
    protected AdventurerBuilder self() {
        return this;
    }

    @Override
    public Entity build(@NotNull Board board) {
        return new Adventurer(board, position, name, direction, moves);
    }

    public AdventurerBuilder setName(String name) {
        this.name = name;
        return self();
    }

    public AdventurerBuilder setDirection(Direction direction) {
        this.direction = direction;
        return self();
    }

    public AdventurerBuilder setMoves(List<Move> moves) {
        this.moves = moves;
        return self();
    }
}
