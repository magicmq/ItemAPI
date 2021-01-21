package dev.magicmq.itemapi.utils.exception;

/**
 * Thrown if a meta incompatible with a certain item is applied. For example, this would be thrown if FireworkMetadata was applied to a book.
 */
public class IncorrectMetaException extends RuntimeException {

    private static final long serialVersionUID = -9056776550512086547L;

    public IncorrectMetaException(String message, Throwable err) {
        super(message, err);
    }
}
