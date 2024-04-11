package fr.gabray.parser;

import fr.gabray.board.Board;
import fr.gabray.exception.MapParsingException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

final class BoardBuilder {
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
        try {
            entityBuilders.stream()
                    .map(builder -> builder.build(board))
                    .forEach(board::addEntity);
        } catch (IllegalArgumentException e) {
            throw new MapParsingException("Failed to parse board entities", e);
        }
        return board;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
