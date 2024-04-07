package fr.gabray.serializer;

import fr.gabray.board.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class SerializerTest {

    @Test
    void serializeEmptyBoard() {
        Board board = new Board(5, 5);

        final String output = new BoardSerializer().serialize(board);

        Assertions.assertEquals("C - 5 - 5", output.trim());
    }

    @Test
    void serializeMountain() {
        Board board = new Board(5, 5);
        board.addEntity(new Mountain(board, Position.of(1, 1)));

        final String output = new BoardSerializer().serialize(board);

        Assertions.assertEquals("C - 5 - 5\n" +
                "M - 1 - 1", output.trim());
    }

    @Test
    void serializeTreasure() {
        Board board = new Board(5, 5);
        board.addEntity(new Treasure(board, Position.of(1, 1)));

        final String output = new BoardSerializer().serialize(board);

        Assertions.assertEquals("C - 5 - 5\n" +
                "T - 1 - 1 - 1", output.trim());
    }

    @Test
    void serializeMultipleTreasures() {
        Board board = new Board(5, 5);
        board.addEntity(new Treasure(board, Position.of(1, 1)));
        board.addEntity(new Treasure(board, Position.of(1, 1)));
        board.addEntity(new Treasure(board, Position.of(1, 1)));

        final String output = new BoardSerializer().serialize(board);

        Assertions.assertEquals("C - 5 - 5\n" +
                "T - 1 - 1 - 3", output.trim());
    }

    @Test
    void serializeAdventurer() {
        Board board = new Board(5, 5);
        board.addEntity(new Adventurer(board, Position.of(1, 1), "John", Direction.SOUTH, List.of(Move.FORWARD), 3));

        final String output = new BoardSerializer().serialize(board);

        Assertions.assertEquals("C - 5 - 5\n" +
                "A - John - 1 - 1 - S - 3", output.trim());
    }
}
