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
//
//    public int getTreasureCount(@NotNull final Position position) {
//        return 0;
//    }
//
//    public List<Adventurer> getAdventurers() {
//        return List.of();
//    }
}
