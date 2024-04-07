package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Adventurer extends Entity {

    private final String name;
    private Direction direction;
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

    /**
     * @return True if the adventurer has moves remaining
     */
    public boolean hasNextMove() {
        return !moves.isEmpty();
    }

    /**
     * Execute next move and update the adventurer's position and direction. Do nothing if the move is blocked by an
     * obstacle or map limits, or if there is no remaining move. Remove the move from the move list.
     *
     * @return True if the adventurer has moved
     */
    public boolean move() {
        if (moves.isEmpty()) {
            return false;
        }
        final Move move = moves.remove(0);
        if (move == Move.FORWARD) {
            final Position newPosition = position.moveInDirection(direction);
            if (newPosition.equals(position) || !board.isPositionValid(newPosition)) {
                return false;
            }
            this.position = newPosition;
        } else {
            this.direction = direction.rotate(move);
        }
        return true;
    }
}
