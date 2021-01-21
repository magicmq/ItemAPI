package dev.magicmq.itemapi.utils.exception;

/**
 * Thrown if a feature is utilized when it is not supported in the running Minecraft version. For example, using "mob-type" to define spawn eggs in 1.13+ will cause this exception to be thrown.
 */
public class VersionNotSupportedException extends RuntimeException {

    private static final long serialVersionUID = 5954957323003383258L;

    public VersionNotSupportedException(String message) {
        super(message);
    }
}
