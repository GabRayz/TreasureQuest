package fr.gabray.parser;

import fr.gabray.board.Board;
import fr.gabray.exception.MapParsingException;
import org.jetbrains.annotations.NotNull;

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
            default -> throw new MapParsingException();
        }
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

        public BoardBuilder setWidth(int width) {
            this.width = width;
            return this;
        }

        public BoardBuilder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Board build() throws MapParsingException {
            if (width <= 0 || height <= 0)
                throw new MapParsingException("Invalid map size");
            return new Board(width, height);
        }
    }
}
