package dev.magicmq.itemapi.utils.exception;

/**
 * Thrown for various reasons related to parsing and applying NBT data, especially if required fields such as the key and value are null.
 */
public class NBTException extends RuntimeException {

    private static final long serialVersionUID = 6760627395867297410L;

    public NBTException(String message) {
        super(message);
    }
}
