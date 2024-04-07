package fr.gabray.board;

import org.jetbrains.annotations.NotNull;

public enum Direction {
    NORTH("N") {
        @Override
        public Direction left() {
            return WEST;
        }

        @Override
        public Direction right() {
            return EAST;
        }
    },
    EAST("E") {
        @Override
        public Direction left() {
            return SOUTH;
        }

        @Override
        public Direction right() {
            return NORTH;
        }
    },
    SOUTH("S") {
        @Override
        public Direction left() {
            return EAST;
        }

        @Override
        public Direction right() {
            return WEST;
        }
    },
    WEST("O") {
        @Override
        public Direction left() {
            return NORTH;
        }

        @Override
        public Direction right() {
            return SOUTH;
        }
    },
    ;

    private final String label;

    Direction(String label) {
        this.label = label;
    }

    /**
     * Return the direction resulting of the rotation of the current direction
     *
     * @param move Rotation move
     * @return new rotated direction
     * @throws IllegalArgumentException if the given move is not a rotation move
     */
    public Direction rotate(@NotNull final Move move) throws IllegalArgumentException {
        return switch (move) {
            case RIGHT -> this.right();
            case LEFT -> this.left();
            default -> throw new IllegalArgumentException("Invalid rotation move " + move);
        };
    }

    public abstract Direction left();

    public abstract Direction right();

    @NotNull
    public static Direction fromLabel(@NotNull final String label) throws IllegalArgumentException {
        for (Direction value : values()) {
            if (value.label.equals(label))
                return value;
        }
        throw new IllegalArgumentException("Unknown Direction " + label);
    }
}
