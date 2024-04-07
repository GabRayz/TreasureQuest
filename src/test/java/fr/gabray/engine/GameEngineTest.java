package fr.gabray.engine;

import fr.gabray.board.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class GameEngineTest {

    @Test
    void singleAdventurerTest() {
        Board board = new Board(5, 5);
        board.addEntity(new Treasure(board, Position.of(1, 1)));
        Adventurer john = new Adventurer(board, Position.of(0, 1), "John", Direction.EAST, List.of(Move.FORWARD));
        board.addEntity(john);
        GameEngine gameEngine = new GameEngine(board);

        gameEngine.start();

        Assertions.assertFalse(john.hasNextMove());
        Assertions.assertEquals(1, john.getCollectedTreasureCount());
    }

    @Test
    void singleAdventurerMultipleMoves() {
        Board board = new Board(5, 5);
        board.addEntity(new Treasure(board, Position.of(1, 1)));
        board.addEntity(new Treasure(board, Position.of(1, 2)));
        Adventurer john = new Adventurer(board, Position.of(0, 1), "John", Direction.EAST, List.of(Move.FORWARD, Move.RIGHT, Move.FORWARD));
        board.addEntity(john);
        GameEngine gameEngine = new GameEngine(board);

        gameEngine.start();

        Assertions.assertFalse(john.hasNextMove());
        Assertions.assertEquals(Position.of(1, 2), john.getPosition());
        Assertions.assertEquals(2, john.getCollectedTreasureCount());
    }

    @Test
    void twoAdventurers() {
        Board board = new Board(5, 5);
        Adventurer john = new Adventurer(board, Position.of(0, 1), "John", Direction.EAST, List.of(Move.FORWARD));
        Adventurer bob = new Adventurer(board, Position.of(0, 0), "Bob", Direction.EAST, List.of(Move.FORWARD));
        board.addEntity(john);
        board.addEntity(bob);
        GameEngine gameEngine = new GameEngine(board);

        gameEngine.start();

        Assertions.assertEquals(Position.of(1, 1), john.getPosition());
        Assertions.assertEquals(Position.of(1, 0), bob.getPosition());
    }

    @Test
    void twoAdventurersWithDifferentMoveCount() {
        Board board = new Board(5, 5);
        Adventurer john = new Adventurer(board, Position.of(0, 1), "John", Direction.EAST, List.of(Move.FORWARD, Move.FORWARD));
        Adventurer bob = new Adventurer(board, Position.of(0, 0), "Bob", Direction.EAST, List.of(Move.FORWARD));
        board.addEntity(john);
        board.addEntity(bob);
        GameEngine gameEngine = new GameEngine(board);

        gameEngine.start();

        Assertions.assertEquals(Position.of(2, 1), john.getPosition());
        Assertions.assertEquals(Position.of(1, 0), bob.getPosition());
    }

    @Test
    void twoAdventurersCollide() {
        Board board = new Board(5, 5);
        Adventurer john = new Adventurer(board, Position.of(0, 1), "John", Direction.EAST, List.of(Move.FORWARD, Move.FORWARD));
        Adventurer bob = new Adventurer(board, Position.of(0, 2), "Bob", Direction.NORTH, List.of(Move.FORWARD, Move.FORWARD));
        board.addEntity(bob); // Bob should be blocked by john
        board.addEntity(john);
        GameEngine gameEngine = new GameEngine(board);

        gameEngine.start();

        Assertions.assertEquals(Position.of(2, 1), john.getPosition());
        Assertions.assertEquals(Position.of(0, 1), bob.getPosition());
    }

    @Test
    void twoAdventurersNoCollide() {
        Board board = new Board(5, 5);
        Adventurer john = new Adventurer(board, Position.of(0, 1), "John", Direction.EAST, List.of(Move.FORWARD, Move.FORWARD));
        Adventurer bob = new Adventurer(board, Position.of(0, 2), "Bob", Direction.NORTH, List.of(Move.FORWARD, Move.FORWARD));
        board.addEntity(john); // John moves before bob, bob does not get blocked
        board.addEntity(bob);
        GameEngine gameEngine = new GameEngine(board);

        gameEngine.start();

        Assertions.assertEquals(Position.of(2, 1), john.getPosition());
        Assertions.assertEquals(Position.of(0, 0), bob.getPosition());
    }
}
