package fr.gabray.parser;

import fr.gabray.board.Board;
import fr.gabray.board.Direction;
import fr.gabray.board.Move;
import fr.gabray.board.Position;
import fr.gabray.exception.MapParsingException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BoardParser {

    public Board parse(@NotNull final String input) throws MapParsingException {
        final String[] lines = input.split("\n");
        final BoardBuilder builder = new BoardBuilder();

        for (String line : lines) {
            parseLine(line, builder);
        }

        return builder.build();
    }

    private void parseLine(@NotNull final String line, @NotNull final BoardBuilder builder) throws MapParsingException {
        if (line.isBlank())
            return;
        switch (line.charAt(0)) {
            case 'C' -> parseMapLine(line, builder);
            case 'M' -> parseMountainLine(line, builder);
            case 'T' -> parseTreasure(line, builder);
            case 'A' -> parseAdventurer(line, builder);
            default -> throw new MapParsingException();
        }
    }

    private void parseAdventurer(@NotNull final String line, @NotNull final BoardBuilder builder) throws MapParsingException {
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

        builder.addEntity(new AdventurerBuilder()
                .setPosition(Position.of(x, y))
                .setName(name)
                .setDirection(direction)
                .setMoves(moves)
        );
    }

    private void parseTreasure(@NotNull final String line, @NotNull final BoardBuilder builder) throws MapParsingException {
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
        for (int i = 0; i < count; i++) {
            builder.addEntity(new TreasureBuilder().setPosition(position));
        }
    }

    private void parseMountainLine(@NotNull final String line, @NotNull final BoardBuilder builder) throws MapParsingException {
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
        builder.addEntity(new MountainBuilder().setPosition(position));
    }

    private void parseMapLine(@NotNull final String line, @NotNull final BoardBuilder builder) throws MapParsingException {
        if (builder.height != 0 || builder.width != 0)
            throw new MapParsingException("Map size declared twice");
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
        builder.setWidth(width)
                .setHeight(height);
    }

    private static final class BoardBuilder {
        private int width;
        private int height;
        private final List<EntityBuilder<?>> entityBuilders = new ArrayList<>();

        public BoardBuilder setWidth(int width) {
            this.width = width;
            return this;
        }

        public BoardBuilder setHeight(int height) {
            this.height = height;
            return this;
        }

        public BoardBuilder addEntity(@NotNull final EntityBuilder<?> entityBuilder) {
            this.entityBuilders.add(entityBuilder);
            return this;
        }

        public Board build() throws MapParsingException {
            if (width <= 0 || height <= 0)
                throw new MapParsingException("Invalid map size");
            Board board = new Board(width, height);
            entityBuilders.stream()
                    .map(builder -> builder.build(board))
                    .forEach(board::addEntity);
            return board;
        }
    }
}
