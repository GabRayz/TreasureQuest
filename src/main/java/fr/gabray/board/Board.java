package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of the Treasure map
 */
public class Board {

    private final int width;
    private final int height;
    /**
     * Entities placed on the map, sorted by placement order
     */
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

    /**
     * @return True if a mountain is placed at the given position
     */
    public boolean hasMountain(@NotNull final Position position) {
        return entities.stream()
                .anyMatch(entity -> entity instanceof Mountain && entity.getPosition().equals(position));
    }

    /**
     * Add a new entity to the map
     *
     * @param entity Entity to add
     */
    public void addEntity(@NotNull final Entity entity) {
        entities.add(entity);
    }

    /**
     * Count the number of treasures present at the given position
     */
    public int getTreasureCount(@NotNull final Position position) {
        return (int) entities.stream()
                .filter(Treasure.class::isInstance)
                .filter(entity -> entity.getPosition().equals(position))
                .count();
    }

    /**
     * @return All placed adventurers
     */
    public List<Adventurer> getAdventurers() {
        return entities.stream()
                .filter(Adventurer.class::isInstance)
                .map(Adventurer.class::cast)
                .toList();
    }

    public boolean isPositionValid(@NotNull final Position position) {
        return position.x() < width && position.y() < height;
    }
}
