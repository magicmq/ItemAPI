package dev.magicmq.itemapi.utils;

import java.util.Arrays;

/**
 * A wrapper class representing all available potions up to the current Minecraft version as well as common names for more easy parsing from config files.
 * <b>Note:</b> For forwards/backwards compatibility, Bukkit PotionType is not referenced at all in this class, only the enum names.
 */
public enum Potion {

    NIGHT_VISION ("NIGHT_VISION", "NIGHT_VISION", new String[]{"nightvision", "nightvis", "night_vision"}),
    INVISIBILITY ("INVISIBILITY", "INVISIBILITY", new String[]{"invisibility"}),
    JUMP ("JUMP", "JUMP", new String[]{"jumping", "leaping", "leap", "jump"}),
    FIRE_RESISTANCE ("FIRE_RESISTANCE", "FIRE_RESISTANCE", new String[]{"fireresistance", "fireresist", "resistfire", "fire_resistance"}),
    SPEED ("SPEED", "SPEED", new String[]{"speed", "quick", "swiftness", "swift"}),
    SLOWNESS ("SLOWNESS", "SLOWNESS", new String[]{"slow", "slowness"}),
    WATER_BREATHING ("WATER_BREATHING", "WATER_BREATHING", new String[]{"waterbreathing", "waterbreathe", "breathewater", "water_breathing"}),
    INSTANT_HEAL ("INSTANT_HEAL", "INSTANT_HEAL", new String[]{"healing", "heal", "instantheal", "instanthealing", "healinstant", "healinginstant", "instant_heal"}),
    INSTANT_DAMAGE ("INSTANT_DAMAGE", "INSTANT_DAMAGE", new String[]{"instantdamage", "instantdmg", "dmginstant", "damageinstant", "harming", "harm", "instant_damage"}),
    POISON ("POISON", "POISON", new String[]{"poison"}),
    REGEN ("REGEN", "REGEN", new String[]{"regeneration", "regen"}),
    STRENGTH ("STRENGTH", "STRENGTH", new String[]{"strength", "strong"}),
    WEAKNESS ("WEAKNESS", "WEAKNESS", new String[]{"weak", "weakness"}),
    LUCK ("LUCK", "LUCK", new String[]{"luckiness", "lucky", "luck"}),
    WATER ("WATER", "WATER", new String[]{"water", "aqua"}),
    TURTLE_MASTER ("TURTLE_MASTER", "TURTLE_MASTER", new String[]{"turtlemaster", "masterturtle", "turtle_master"}),
    SLOW_FALLING("SLOW_FALLING", "SLOW_FALLING", new String[]{"slowfalling", "fallingslow", "slowfall", "fallslow", "slow_falling"});

    private final String bukkitPotionType;
    private final String name;
    private final String[] aliases;

    Potion(String bukkitPotionType, String name, String[] aliases) {
        this.bukkitPotionType = bukkitPotionType;
        this.name = name;
        this.aliases = aliases;
    }

    public String getBukkitPotionType() {
        return bukkitPotionType;
    }

    /**
     * Get a potion by its name.
     * @param name The name of the potion to get, can be an official name or common name
     * @return The enum representing the potion if one was found, null if nothing was found that matched the specified name.
     */
    public static Potion getByName(String name) {
        for (Potion potion : values()) {
            if (potion.name.equalsIgnoreCase(name))
                return potion;

            if (Arrays.asList(potion.aliases).contains(name.toLowerCase()))
                return potion;
        }
        return null;
    }
}
