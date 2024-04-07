package fr.gabray.board;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdventurerTest {

    @Test
    void singleMoveTest() {
        Board board = new Board(2, 2);

        Adventurer adventurer = new Adventurer(board, Position.of(0, 0), "John", Direction.SOUTH, new ArrayList<>(List.of(Move.FORWARD)));

        assertTrue(adventurer.hasNextMove());
        boolean moved = adventurer.move();
        assertTrue(moved);
        assertFalse(adventurer.hasNextMove());
        assertEquals(Position.of(0, 1), adventurer.getPosition());
        assertEquals(Direction.SOUTH, adventurer.getDirection());
    }

    @Test
    void singleMoveWithNoRemainingMoves() {
        Board board = new Board(2, 2);

        Adventurer adventurer = new Adventurer(board, Position.of(0, 0), "John", Direction.SOUTH, new ArrayList<>());

        assertFalse(adventurer.hasNextMove());
        boolean moved = adventurer.move();
        assertFalse(moved);
        assertFalse(adventurer.hasNextMove());
        assertEquals(Position.of(0, 0), adventurer.getPosition());
        assertEquals(Direction.SOUTH, adventurer.getDirection());
    }

    @Test
    void multipleMoves() {
        Board board = new Board(2, 2);

        Adventurer adventurer = new Adventurer(board, Position.of(0, 0), "John", Direction.SOUTH, new ArrayList<>(List.of(Move.FORWARD, Move.RIGHT)));

        boolean moved = adventurer.move();
        assertTrue(moved);
        assertEquals(Position.of(0, 1), adventurer.getPosition());
        assertEquals(Direction.SOUTH, adventurer.getDirection());

        moved = adventurer.move();
        assertTrue(moved);
        assertFalse(adventurer.hasNextMove());
        assertEquals(Direction.WEST, adventurer.getDirection());
    }

    @Test
    void singleMoveOutOfMap() {
        Board board = new Board(2, 2);

        Adventurer adventurer = new Adventurer(board, Position.of(0, 0), "John", Direction.NORTH, new ArrayList<>(List.of(Move.FORWARD)));

        assertTrue(adventurer.hasNextMove());
        boolean moved = adventurer.move();
        assertFalse(moved);
        assertFalse(adventurer.hasNextMove());
    }

    @Test
    void moveWithObstacle() {
        Board board = new Board(2, 2);
        board.addEntity(new Mountain(board, Position.of(0, 1))); // Should block forward move

        Adventurer adventurer = new Adventurer(board, Position.of(0, 0), "John", Direction.SOUTH, new ArrayList<>(List.of(Move.FORWARD)));

        assertTrue(adventurer.hasNextMove());
        boolean moved = adventurer.move();
        assertFalse(moved);
    }

    @Test
    void moveWithObstacleNotBlocking() {
        Board board = new Board(2, 2);
        board.addEntity(new Mountain(board, Position.of(1, 1))); // Should not block

        Adventurer adventurer = new Adventurer(board, Position.of(0, 0), "John", Direction.SOUTH, new ArrayList<>(List.of(Move.FORWARD)));

        assertTrue(adventurer.hasNextMove());
        boolean moved = adventurer.move();
        assertTrue(moved);
    }
}
