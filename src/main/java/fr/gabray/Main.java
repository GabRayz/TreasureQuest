package fr.gabray;

import fr.gabray.board.Board;
import fr.gabray.engine.GameEngine;
import fr.gabray.exception.MapParsingException;
import fr.gabray.parser.BoardParser;
import fr.gabray.serializer.BoardSerializer;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger("Main");

    public static void main(String[] args) {
        if (args.length == 0) {
            logger.log(Level.SEVERE, "Usage: main {filename}");
            return;
        }

        final String filePath = args[0];
        try {
            final String input = readInputFromFile(filePath);

            final Board board = new BoardParser().parse(input);

            GameEngine engine = new GameEngine(board);
            engine.start();

            String output = new BoardSerializer().serialize(board);
            logger.log(Level.INFO, () -> "Result:\n" + output);
        } catch (final IOException e) {
            logger.log(Level.SEVERE, "Failed to read file", e);
        } catch (final MapParsingException e) {
            logger.log(Level.SEVERE, "Invalid map", e);
        }
    }

    @NotNull
    private static String readInputFromFile(@NotNull final String filename) throws IOException {
        try (var stream = new FileInputStream(filename)) {
            return new String(stream.readAllBytes());
        }
    }
}