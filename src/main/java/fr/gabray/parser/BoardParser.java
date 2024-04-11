package fr.gabray.parser;

import fr.gabray.board.*;
import fr.gabray.exception.MapParsingException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BoardParser {

    /**
     * Parse the board represented in input
     *
     * @param input Serialized board
     * @throws MapParsingException If input is not a valid representation of a board
     */
    public Board parse(@NotNull final String input) throws MapParsingException {
        final String[] lines = input.split("\n");

        Board board = null;
        final List<IEntityBuilder> entityBuilders = new ArrayList<>();
        // Parse line by line
        for (String line : lines) {
            if (line.isBlank())
                continue;

            if (line.charAt(0) == 'C') {
                // Construct the board
                if (board != null)
                    throw new MapParsingException("Map size declared twice");
                board = parseMapLine(line);
            } else {
                // Parse entity
                // We may not have a board yet, so store only the builders
                final IEntityBuilder entityBuilder = parseEntityLine(line);
                entityBuilders.add(entityBuilder);
            }
        }

        if (board == null) {
            throw new MapParsingException("No map declaration");
        }
        // Now that we have a board, build all entities
        for (IEntityBuilder entityBuilder : entityBuilders) {
            try {
                board.addEntity(entityBuilder.build(board));
            } catch (IllegalArgumentException e) {
                throw new MapParsingException("Failed to parse board entities", e);
            }
        }

        return board;
    }

    /**
     * @throws IllegalArgumentException if line is blank
     */
    private IEntityBuilder parseEntityLine(@NotNull final String line) throws MapParsingException, IllegalArgumentException {
        if (line.isBlank())
            throw new IllegalArgumentException("Line is blank");
        return switch (line.charAt(0)) {
            case 'M' -> parseMountainLine(line);
            case 'T' -> parseTreasure(line);
            case 'A' -> parseAdventurer(line);
            default -> throw new MapParsingException();
        };
    }

    private IEntityBuilder parseAdventurer(@NotNull final String line) throws MapParsingException {
        String[] parts = line.split(" - ", 0);
        if (parts.length != 6)
            throw new MapParsingException("Invalid number of parts in adventurer declaration");
        final String name = parts[1];
        final int x;
        final int y;
        final Direction direction;
        final List<Move> moves;
        try {
            x = Integer.parseInt(parts[2]);
            y = Integer.parseInt(parts[3]);
            direction = Direction.fromLabel(parts[4]);
            moves = Move.fromLabels(parts[5]);
        } catch (IllegalArgumentException e) {
            throw new MapParsingException("Invalid adventurer declaration", e);
        }

        return board -> new Adventurer(board, Position.of(x, y), name, direction, moves);
    }

    private IEntityBuilder parseTreasure(@NotNull final String line) throws MapParsingException {
        String[] parts = line.split(" - ", 0);
        if (parts.length != 4)
            throw new MapParsingException("Invalid number of parts in treasure declaration, expected 'T - {d} - {d} {d}'");
        final int count;
        final Position position;
        try {
            final int x = Integer.parseInt(parts[1]);
            final int y = Integer.parseInt(parts[2]);
            count = Integer.parseInt(parts[3]);
            position = Position.of(x, y);
        } catch (NumberFormatException e) {
            throw new MapParsingException("Invalid treasure declaration", e);
        }

        return board -> new Treasure(board, position, count);
    }

    private IEntityBuilder parseMountainLine(@NotNull final String line) throws MapParsingException {
        String[] parts = line.split(" - ", 0);
        if (parts.length != 3)
            throw new MapParsingException("Invalid number of parts in mountain declaration, expected 'M - {d} - {d}'");
        final Position position;
        try {
            final int x = Integer.parseInt(parts[1]);
            final int y = Integer.parseInt(parts[2]);
            position = Position.of(x, y);
        } catch (IllegalArgumentException e) {
            throw new MapParsingException("Invalid mountain definition", e);
        }

        return board -> new Mountain(board, position);
    }

    private Board parseMapLine(@NotNull final String line) throws MapParsingException {
        final String[] parts = line.split(" - ", 0);
        if (parts.length != 3)
            throw new MapParsingException("Invalid number of parts in map declaration, expected 'C - {d} - {d}'");
        final int width;
        final int height;
        try {
            width = Integer.parseInt(parts[1]);
            height = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new MapParsingException("Invalid map declaration", e);
        }
        return new Board(width, height);
    }

}
