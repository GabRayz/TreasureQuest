package fr.gabray.board;

public class Board {

    private final int width;
    private final int height;

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

//    public boolean hasMountain(@NotNull final Position position) {
//        return false;
//    }
//
//    public int getTreasureCount(@NotNull final Position position) {
//        return 0;
//    }
//
//    public List<Adventurer> getAdventurers() {
//        return List.of();
//    }
}
