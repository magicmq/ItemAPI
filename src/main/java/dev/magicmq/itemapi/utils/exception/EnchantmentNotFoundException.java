package dev.magicmq.itemapi.utils.exception;

/**
 * Thrown when an enchantment name is not recognized.
 */
public class EnchantmentNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -9026446556933606577L;

    public EnchantmentNotFoundException(String message) {
        super(message);
    }
}
