package dev.magicmq.itemapi.utils.exception;

/**
 * Thrown if a map ID is not found on the server.
 */
public class MapNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 111263384692060984L;

    public MapNotFoundException(String message) {
        super(message);
    }
}
