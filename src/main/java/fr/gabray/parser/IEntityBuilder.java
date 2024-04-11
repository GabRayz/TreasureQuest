package fr.gabray.parser;

import fr.gabray.board.Board;
import fr.gabray.board.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * Functional builder class used to delay entity construction. Allows to have the map size declaration not as the first
 * line, and to wait to have a valid constructed board before actually constructing entities.
 */
@FunctionalInterface
public interface IEntityBuilder {

    @NotNull
    Entity build(@NotNull final Board board);
}
