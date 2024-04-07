package fr.gabray.board;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class AdventurerTest {

    @Test
    void singleMoveTest() {
        Board board = new Board(2, 2);

        Adventurer adventurer = new Adventurer(board, Position.of(0, 0), "John", Direction.SOUTH, new ArrayList<>(List.of(Move.FORWARD)));

        Assertions.assertTrue(adventurer.hasNextMove());
        boolean moved = adventurer.move();
        Assertions.assertTrue(moved);
        Assertions.assertFalse(adventurer.hasNextMove());
        Assertions.assertEquals(Position.of(0, 1), adventurer.getPosition());
        Assertions.assertEquals(Direction.SOUTH, adventurer.getDirection());
    }

    @Test
    void singleMoveWithNoRemainingMoves() {
        Board board = new Board(2, 2);

        Adventurer adventurer = new Adventurer(board, Position.of(0, 0), "John", Direction.SOUTH, new ArrayList<>());

        Assertions.assertFalse(adventurer.hasNextMove());
        boolean moved = adventurer.move();
        Assertions.assertFalse(moved);
        Assertions.assertFalse(adventurer.hasNextMove());
        Assertions.assertEquals(Position.of(0, 0), adventurer.getPosition());
        Assertions.assertEquals(Direction.SOUTH, adventurer.getDirection());
    }

    @Test
    void multipleMoves() {
        Board board = new Board(2, 2);

        Adventurer adventurer = new Adventurer(board, Position.of(0, 0), "John", Direction.SOUTH, new ArrayList<>(List.of(Move.FORWARD, Move.RIGHT)));

        boolean moved = adventurer.move();
        Assertions.assertTrue(moved);
        Assertions.assertEquals(Position.of(0, 1), adventurer.getPosition());
        Assertions.assertEquals(Direction.SOUTH, adventurer.getDirection());

        moved = adventurer.move();
        Assertions.assertTrue(moved);
        Assertions.assertFalse(adventurer.hasNextMove());
        Assertions.assertEquals(Direction.WEST, adventurer.getDirection());
    }

    @Test
    void singleMoveOutOfMap() {
        Board board = new Board(2, 2);

        Adventurer adventurer = new Adventurer(board, Position.of(0, 0), "John", Direction.NORTH, new ArrayList<>(List.of(Move.FORWARD)));

        Assertions.assertTrue(adventurer.hasNextMove());
        boolean moved = adventurer.move();
        Assertions.assertFalse(moved);
        Assertions.assertFalse(adventurer.hasNextMove());
    }
}
