package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Adventurer extends Entity {

    private final String name;
    private Direction direction;
    private final List<Move> moves;
    private int collectedTreasureCount = 0;

    public Adventurer(@NotNull Board board, @NotNull Position position, @NotNull final String name, Direction direction, List<Move> moves) {
        super(board, position);
        this.name = name;
        this.direction = direction;
        this.moves = new ArrayList<>(moves);
    }

    public Adventurer(@NotNull Board board, @NotNull Position position, @NotNull final String name, Direction direction, List<Move> moves, int collectedTreasureCount) {
        super(board, position);
        this.name = name;
        this.direction = direction;
        this.moves = new ArrayList<>(moves);
        this.collectedTreasureCount = collectedTreasureCount;
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
     * obstacle or map limits, or if there is no remaining move. Remove the move from the move list.<br/>
     * Collect Treasures found on the way.
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
            if (board.hasMountain(newPosition) || board.hasEntity(Adventurer.class, newPosition)) {
                return false;
            }
            // Move
            this.position = newPosition;
            if (board.getTreasureCount(position) > 0) {
                collectedTreasureCount++;
                board.collectTreasure(position);
            }
        } else {
            this.direction = direction.rotate(move);
        }
        return true;
    }

    public int getCollectedTreasureCount() {
        return collectedTreasureCount;
    }

    @Override
    public @NotNull String serialize() {
        return String.format("A - %s - %d - %d - %s - %d",
                getName(),
                getPosition().x(),
                getPosition().y(),
                getDirection().getLabel(),
                getCollectedTreasureCount());
    }
}
