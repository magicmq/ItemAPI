package dev.magicmq.itemapi.utils;

import java.io.Serializable;
import java.util.List;

/**
 * A class representing an individual firework effect that can be applied to a firework rocket or a firework star.
 */
public class FireworkEffect implements Serializable {

    private static final long serialVersionUID = -6270695902643928531L;

    private final String type;
    private final List<String> colors;
    private final List<String> fadeColors;
    private boolean flicker;
    private boolean trail;

    /**
     * Create a new FireworkEffect with the given type, colors, fade colors, flicker, and trail.
     * @param type The effect type that the effect should be, an enum of {@link org.bukkit.FireworkEffect.Type}
     * @param colors A list of colors that the effect should have, enums of {@link Colors}
     * @param fadeColors A list of fade colors that the effect should have, enums of {@link Colors}
     * @param flicker True if the effect should flicker, false if otherwise
     * @param trail True if the effect should have a trail, false if otherwise
     */
    public FireworkEffect(String type, List<String> colors, List<String> fadeColors, boolean flicker, boolean trail) {
        this.type = type;
        this.colors = colors;
        this.fadeColors = fadeColors;
        this.flicker = flicker;
        this.trail = trail;
    }

    /**
     * Get the type of this effect.
     * @return The effect type, an enum of {@link org.bukkit.FireworkEffect.Type}
     */
    public String getType() {
        return type;
    }

    /**
     * Get the colors that this effect has.
     * @return A mutable list containing the colors of this effect
     */
    public List<String> getColors() {
        return colors;
    }

    /**
     * Get the fade colors that this effect has.
     * @return A mutable list containing the fade colors of this effect
     */
    public List<String> getFadeColors() {
        return fadeColors;
    }

    /**
     * Check if this effect has flicker.
     * @return True if the effect has flicker, false if otherwise
     */
    public boolean isFlicker() {
        return flicker;
    }

    /**
     * Check if this effect has a trail.
     * @return True if the effect has a trail, false if otherwise
     */
    public boolean isTrail() {
        return trail;
    }
}
