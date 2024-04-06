package fr.gabray.parser;

import fr.gabray.board.Board;
import fr.gabray.board.Entity;
import fr.gabray.board.Position;
import org.jetbrains.annotations.NotNull;

public abstract class EntityBuilder<T extends EntityBuilder<T>> {

    protected Position position;

    protected abstract T self();

    public abstract Entity build(@NotNull final Board board);

    public T setPosition(Position position) {
        this.position = position;
        return self();
    }
}
