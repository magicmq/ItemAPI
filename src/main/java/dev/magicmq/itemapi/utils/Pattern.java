package dev.magicmq.itemapi.utils;

import java.io.Serializable;

/**
 * A class representing a pattern that can be applied to a banner or a shield.
 */
public class Pattern implements Serializable {

    private static final long serialVersionUID = 8303166385745223879L;

    private final String color;
    private final String patternType;

    /**
     * Create a new pattern with the given color and pattern type.
     * @param color The color that the pattern should be, an enum of {@link org.bukkit.DyeColor}
     * @param patternType The type of pattern that this pattern should be, an enum of {@link org.bukkit.block.banner.PatternType}
     */
    public Pattern(String color, String patternType) {
        this.color = color;
        this.patternType = patternType;
    }

    /**
     * Get the color of this pattern.
     * @return The color of this pattern, an enum of {@link org.bukkit.DyeColor}
     */
    public String getColor() {
        return color;
    }

    /**
     * Get the pattern type of this pattern.
     * @return The pattern type of this pattern, an enum of {@link org.bukkit.block.banner.PatternType}
     */
    public String getPatternType() {
        return patternType;
    }
}
