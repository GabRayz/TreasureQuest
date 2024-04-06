package fr.gabray.parser;

import fr.gabray.board.Board;
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
            default -> throw new MapParsingException();
        }
    }

    private void parseMountainLine(@NotNull final String line, @NotNull final BoardBuilder builder) throws MapParsingException {
        String[] parts = line.split(" - ", 0);
        if (parts.length != 3)
            throw new MapParsingException("Invalid number of parts in mountain declaration, expected 'M - {d} - {d}'");
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        builder.addEntity(new MountainBuilder().setPosition(Position.of(x, y)));
    }

    private void parseMapLine(@NotNull final String line, @NotNull final BoardBuilder builder) throws MapParsingException {
        if (builder.height != 0 || builder.width != 0)
            throw new MapParsingException("Map size declared twice");
        String[] parts = line.split(" - ", 0);
        if (parts.length != 3)
            throw new MapParsingException("Invalid number of parts in map declaration, expected 'C - {d} - {d}'");
        int width = Integer.parseInt(parts[1]);
        int height = Integer.parseInt(parts[2]);
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
