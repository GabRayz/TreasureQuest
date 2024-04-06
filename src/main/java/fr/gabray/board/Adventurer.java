package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Adventurer extends Entity {

    private final String name;
    private final Direction direction;
    private final List<Move> moves;

    public Adventurer(@NotNull Board board, @NotNull Position position, @NotNull final String name, Direction direction, List<Move> moves) {
        super(board, position);
        this.name = name;
        this.direction = direction;
        this.moves = moves;
    }

    @NotNull
    public String getName() {
        return name;
    }

    @NotNull
    public Direction getDirection() {
        return direction;
    }

    @NotNull
    public List<Move> getMoves() {
        return moves;
    }
}
