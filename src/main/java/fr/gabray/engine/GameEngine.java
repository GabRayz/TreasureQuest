package fr.gabray.engine;

import fr.gabray.board.Adventurer;
import fr.gabray.board.Board;
import org.jetbrains.annotations.NotNull;

/**
 * Class that orchestrates the treasure hunt
 */
public class GameEngine {

    private final Board board;

    public GameEngine(@NotNull final Board board) {
        this.board = board;
    }

    /**
     * Launch the treasure hunt. Will make every adventurer perform their moves until no move is remaining.
     */
    public void start() {
        boolean movesRemaining = true;

        while (movesRemaining) {
            board.getAdventurers().forEach(Adventurer::move);
            movesRemaining = board.getAdventurers().stream().anyMatch(Adventurer::hasNextMove);
        }
    }

    public Board getBoard() {
        return board;
    }
}
