package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int width;
    private final int height;
    private final List<Entity> entities = new ArrayList<>();

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean hasMountain(@NotNull final Position position) {
        return entities.stream()
                .anyMatch(entity -> entity instanceof Mountain && entity.getPosition().equals(position));
    }

    public void addEntity(@NotNull final Entity entity) {
        entities.add(entity);
    }

    public int getTreasureCount(@NotNull final Position position) {
        return (int) entities.stream()
                .filter(Treasure.class::isInstance)
                .filter(entity -> entity.getPosition().equals(position))
                .count();
    }

    public List<Adventurer> getAdventurers() {
        return entities.stream()
                .filter(Adventurer.class::isInstance)
                .map(Adventurer.class::cast)
                .toList();
    }
}
