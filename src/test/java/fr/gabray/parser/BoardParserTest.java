package fr.gabray.parser;

import fr.gabray.board.*;
import fr.gabray.exception.MapParsingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class BoardParserTest {

    @Test
    void parseEmptyBoard() throws MapParsingException {
        final Board board = new BoardParser().parse("C - 3 - 4");

        Assertions.assertEquals(3, board.getWidth());
        Assertions.assertEquals(4, board.getHeight());
    }

    @Test
    void parseEmptyFile() {
        Assertions.assertThrows(MapParsingException.class,
                () -> new BoardParser().parse(""));
    }

    @Test
    void parseInvalidMapDeclaration() {
        Assertions.assertThrows(MapParsingException.class,
                () -> new BoardParser().parse("C - 1 - 1 - 1"));
    }

    @Test
    void parseNotNumbers() {
        Assertions.assertThrows(MapParsingException.class,
                () -> new BoardParser().parse("C - foo - bar"));
    }

    @Test
    void parseEmptyBoardDeclaredTwiceShouldThrow() {
        Assertions.assertThrows(MapParsingException.class,
                () -> new BoardParser().parse("C - 3 - 4\nC - 1 - 1"));
    }

    @Test
    void parseBoardWithOneMountain() throws MapParsingException {
        final Board board = new BoardParser().parse("C - 2 - 2\n" +
                "M - 1 - 1");

        Assertions.assertTrue(board.hasMountain(Position.of(1, 1)));
        Assertions.assertFalse(board.hasMountain(Position.of(1, 2)));
    }

    @Test
    void parseBoardInvalidMountainNegativePosition() {
        Assertions.assertThrows(MapParsingException.class,
                () -> new BoardParser().parse("C - 2 - 2\n" +
                        "M - -1 - -2"));
    }

    @Test
    void parseBoardWithOneTreasure() throws MapParsingException {
        final Board board = new BoardParser().parse("C - 2 - 2\n" +
                "T - 1 - 1 - 1");

        Assertions.assertEquals(1, board.getTreasureCount(Position.of(1, 1)));
        Assertions.assertEquals(0, board.getTreasureCount(Position.of(1, 2)));
    }

    @Test
    void parseBoardWithMultipleTreasures() throws MapParsingException {
        final Board board = new BoardParser().parse("C - 2 - 2\n" +
                "T - 1 - 1 - 3");

        Assertions.assertEquals(3, board.getTreasureCount(Position.of(1, 1)));
    }

    @Test
    void parseBoardTreasureInvalidParts() {
        Assertions.assertThrows(MapParsingException.class, () -> new BoardParser().parse("C - 2 - 2\n" +
                "T - 1 - 1 - 1 - invalid parts"));
    }

    @Test
    void parseBoardMountainInvalidParts() {
        Assertions.assertThrows(MapParsingException.class, () -> new BoardParser().parse("C - 2 - 2\n" +
                "M - 1 - 1 - invalid parts"));
    }

    @Test
    void parseBoardTreasureInvalidPositions() {
        Assertions.assertThrows(MapParsingException.class, () -> new BoardParser().parse("C - 2 - 2\n" +
                "T - a - b - 2"));
    }

    @Test
    void parseBoardAdventurer() throws MapParsingException {
        final Board board = new BoardParser().parse("C - 2 - 2\n" +
                "A - John - 1 - 1 - S - AD");

        final List<Adventurer> adventurers = board.getAdventurers();

        Assertions.assertEquals(1, adventurers.size());
        final Adventurer john = adventurers.get(0);
        Assertions.assertEquals("John", john.getName());
        Assertions.assertEquals(Position.of(1, 1), john.getPosition());
        Assertions.assertEquals(Direction.SOUTH, john.getDirection());
        Assertions.assertEquals(List.of(Move.FORWARD, Move.RIGHT), john.getMoves());
    }

    @Test
    void parseBoardAdventurerInvalidDirection() {
        Assertions.assertThrows(MapParsingException.class, () -> new BoardParser().parse("C - 2 - 2\n" +
                "A - John - 1 - 1 - G - AD"));
    }

    @Test
    void parseBoardAdventurerInvalidMoves() {
        Assertions.assertThrows(MapParsingException.class, () -> new BoardParser().parse("C - 2 - 2\n" +
                "A - John - 1 - 1 - E - WWW"));
    }

    @Test
    void parseBoardAdventurerInvalidParts() {
        Assertions.assertThrows(MapParsingException.class, () -> new BoardParser().parse("C - 2 - 2\n" +
                "A - John - 1 - 1 - A - WWW - invalid part"));
    }

    @Test
    void parseBoardUnknownPrefix() {
        Assertions.assertThrows(MapParsingException.class, () -> new BoardParser().parse("C - 2 - 2\n" +
                "X - 1 - 1"));
    }

    @Test
    void parseBoardNoMapSize() {
        Assertions.assertThrows(MapParsingException.class, () -> new BoardParser().parse("M - 1 - 1"));
    }

    @Test
    void parseBoardTwoMountainsAtSamePosition() {
        Assertions.assertThrows(MapParsingException.class, () -> new BoardParser().parse("C - 2 - 2\n" +
                "M - 1 - 2\n" +
                "M - 1 - 2"));
    }
}
