package fr.gabray;

import fr.gabray.board.Board;
import fr.gabray.engine.GameEngine;
import fr.gabray.exception.MapParsingException;
import fr.gabray.parser.BoardParser;
import fr.gabray.serializer.BoardSerializer;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger("Main");

    public static void main(String[] args) {
        if (args.length != 2) {
            logger.log(Level.SEVERE, "Usage: main {inputFilename} {outputFilename}");
            return;
        }

        try {
            final Board board = parseBoard(args[0]);

            GameEngine engine = new GameEngine(board);
            engine.start();

            serializeBoard(args[1], board);
        } catch (final IOException e) {
            logger.log(Level.SEVERE, "Failed to read/write file", e);
        } catch (final MapParsingException e) {
            logger.log(Level.SEVERE, "Invalid map", e);
        }
    }

    private static void serializeBoard(@NotNull final String path, @NotNull final Board board) throws IOException {
        try (var stream = new FileOutputStream(path)) {
            stream.write(new BoardSerializer().serialize(board).getBytes());
        }
    }

    @NotNull
    private static Board parseBoard(@NotNull final String filename) throws IOException, MapParsingException {
        try (var stream = new FileInputStream(filename)) {
            String input = new String(stream.readAllBytes());
            return new BoardParser().parse(input);
        }
    }
}