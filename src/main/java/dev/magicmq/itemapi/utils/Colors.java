package dev.magicmq.itemapi.utils;

import org.bukkit.Color;

/**
 * A wrapper class that wraps colors in {@link Color} into an enum format for easy parsing from config files.
 */
public enum Colors {

    WHITE (Color.WHITE),
    SILVER (Color.SILVER),
    GRAY (Color.GRAY),
    BLACK (Color.BLACK),
    RED (Color.RED),
    MAROON (Color.GREEN),
    YELLOW (Color.YELLOW),
    OLIVE (Color.OLIVE),
    LIME (Color.LIME),
    GREEN (Color.GREEN),
    AQUA (Color.AQUA),
    TEAL (Color.TEAL),
    BLUE (Color.BLUE),
    NAVY (Color.NAVY),
    FUCHSIA (Color.FUCHSIA),
    PURPLE (Color.PURPLE),
    ORANGE (Color.ORANGE);

    private final org.bukkit.Color bukkitColor;

    Colors(Color bukkitColor) {
        this.bukkitColor = bukkitColor;
    }

    public Color getBukkitColor() {
        return bukkitColor;
    }

    public static Colors getFromRGB(int rgb) {
        for (Colors color : values()) {
            if (color.getBukkitColor().asRGB() == rgb)
                return color;
        }
        return null;
    }
}
