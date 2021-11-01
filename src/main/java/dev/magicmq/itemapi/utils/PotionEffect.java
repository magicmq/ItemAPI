package dev.magicmq.itemapi.utils;

import java.io.Serializable;

/**
 * A class representing a potion effect that can be applied to a suspicious stew.
 */
public class PotionEffect implements Serializable {

    private static final long serialVersionUID = -1769423775962675617L;

    private final String potion;
    private final String duration;
    private final int amplifier;
    private final boolean ambient;
    private final boolean particles;
    private final boolean icon;

    /**
     * Create a new potion effect with the given potion type, duration, amplifier, and ambient, particle, and icon status.
     * @param potion The potion type, an enum of {@link org.bukkit.potion.PotionEffectType}
     * @param duration The duration of the potion, in a parseable duration String such as "5m30s"
     * @param amplifier The amplifier of the potion effect, which can either extend the duration or increase the strength, depending on the potion type
     * @param ambient Whether or not the potion effect should be ambient (ambient potions have more particles that are more translucent)
     * @param particles Whether or not the potion effect should show particles
     * @param icon Whether or not the potion effect should have an icon
     */
    public PotionEffect(String potion, String duration, int amplifier, boolean ambient, boolean particles, boolean icon) {
        this.potion = potion;
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.particles = particles;
        this.icon = icon;
    }

    /**
     * Get the potion type of this potion effect.
     * @return The potion type, an enum of {@link org.bukkit.potion.PotionEffectType}
     */
    public String getPotion() {
        return potion;
    }

    /**
     * Get the duration of this potion effect.
     * @return The duration of the potion, in a parseable duration String such as "5m30s"
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Get the amplifier of this potion, which represents extended status (for some potions), or upgraded status (for other potions).
     * @return The amplifier of the potion effect
     */
    public int getAmplifier() {
        return amplifier;
    }

    /**
     * Check if this potion is ambient.
     * @return True if it is ambient, false if otherwise
     */
    public boolean isAmbient() {
        return ambient;
    }

    /**
     * Check if the potion should show particles.
     * @return True if the potion effect should show particles, false if otherwise
     */
    public boolean hasParticles() {
        return particles;
    }

    /**
     * Check if the potion effect has an icon
     * @return True if the potion effect has an icon, false if otherwise
     */
    public boolean hasIcon() {
        return icon;
    }
}
