package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
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
     * @param type     Type of entity to check
     * @param position Position to check
     * @param <T>      Entity class type
     * @return True if an entity of given type is present at the given position
     */
    public <T extends Entity> boolean hasEntity(@NotNull final Class<T> type, @NotNull final Position position) {
        return entities.stream()
                .anyMatch(entity -> type.isInstance(entity) && entity.getPosition().equals(position));
    }

    /**
     * Check if an entity of any type is present at the given position
     */
    public boolean hasEntity(@NotNull final Position position) {
        return entities.stream()
                .anyMatch(entity -> entity.getPosition().equals(position));
    }

    /**
     * Add a new entity to the map
     *
     * @param entity Entity to add
     * @throws IllegalArgumentException If an entity is already present at that position
     */
    public void addEntity(@NotNull final Entity entity) throws IllegalArgumentException {
        if (hasEntity(entity.getPosition()))
            throw new IllegalArgumentException("Entity already present at that position");
        entities.add(entity);
    }

    /**
     * Count the number of treasures present at the given position
     */
    public int getTreasureCount(@NotNull final Position position) {
        return entities.stream()
                .filter(Treasure.class::isInstance)
                .map(Treasure.class::cast)
                .filter(entity -> entity.getPosition().equals(position))
                .findFirst()
                .map(Treasure::getCount)
                .orElse(0);
    }

    /**
     * Remove at most one treasure at the given position.
     */
    public void collectTreasure(@NotNull final Position position) {
        entities.stream()
                .filter(Treasure.class::isInstance)
                .map(Treasure.class::cast)
                .filter(entity -> entity.getPosition().equals(position))
                .findFirst()
                .ifPresent(entity -> {
                    if (entity.collect() == 0) {
                        entities.remove(entity);
                    }
                });
    }

    /**
     * @return Immutable list of all placed adventurers
     */
    @NotNull
    public List<Adventurer> getAdventurers() {
        return entities.stream()
                .filter(Adventurer.class::isInstance)
                .map(Adventurer.class::cast)
                .toList();
    }

    /**
     * Check if the given Position is within the map's boundaries
     */
    public boolean isPositionValid(@NotNull final Position position) {
        return position.x() < width && position.y() < height;
    }

    /**
     * The list of all entities present on the map
     *
     * @return <b>Immutable</b> list of entities
     */
    @NotNull
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }
}
