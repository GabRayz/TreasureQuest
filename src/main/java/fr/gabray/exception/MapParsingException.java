package fr.gabray.exception;

public class MapParsingException extends Exception {
    public MapParsingException() {
    }

    public MapParsingException(String message) {
        super(message);
    }

    public MapParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MapParsingException(Throwable cause) {
        super(cause);
    }

    public MapParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
