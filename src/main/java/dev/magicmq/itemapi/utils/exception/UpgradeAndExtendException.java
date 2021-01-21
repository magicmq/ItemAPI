package dev.magicmq.itemapi.utils.exception;

/**
 * Thrown if a potion is both upgraded and extended simultaneously.
 */
public class UpgradeAndExtendException extends RuntimeException {

    private static final long serialVersionUID = 5704817773153801697L;

    public UpgradeAndExtendException(String message) {
        super(message);
    }
}
