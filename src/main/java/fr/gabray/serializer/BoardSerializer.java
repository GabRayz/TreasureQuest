package fr.gabray.serializer;

import fr.gabray.board.Board;
import org.jetbrains.annotations.NotNull;

import java.util.StringJoiner;

public class BoardSerializer {

    public String serialize(@NotNull final Board board) {
        final StringJoiner joiner = new StringJoiner("\n");

        joiner.add(writeMap(board));

        board.getEntities().forEach(entity -> joiner.add(entity.serialize()));

        return joiner.toString();
    }

    private String writeMap(@NotNull final Board board) {
        return String.format("C - %d - %d", board.getWidth(), board.getHeight());
    }
}
