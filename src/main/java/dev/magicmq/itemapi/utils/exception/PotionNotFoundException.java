package dev.magicmq.itemapi.utils.exception;

/**
 * Thrown when a potion name is not recognized.
 */
public class PotionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1627646516632691916L;

    public PotionNotFoundException(String message) {
        super(message);
    }
}
