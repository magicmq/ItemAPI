package dev.magicmq.itemapi.utils;

import java.util.Arrays;

/**
 * A wrapper class representing all available enchantments up to the current Minecraft version as well as common names for more easy parsing from config files.
 * <b>Note:</b> For forwards/backwards compatibility, Bukkit Enchantment is not referenced at all in this class, only the enum names.
 */
public enum Enchantment {

    //1.13 and earlier
    POWER ("ARROW_DAMAGE", "POWER", new String[]{"arrowdamage", "arrow_damage"}),
    FLAME ("ARROW_FIRE", "FLAME", new String[]{"arrowfire", "firearrow", "arrow_fire"}),
    INFINITY ("ARROW_INFINITE", "INFINITY", new String[]{"arrowinfinite", "infinitearrow", "arrow_infinite"}),
    PUNCH ("ARROW_KNOCKBACK", "PUNCH", new String[]{"arrowknockback, knockbackarrow", "arrow_knockback"}),
    SHARPNESS ("DAMAGE_ALL", "SHARPNESS", new String[]{"damageall", "alldamage", "dmg", "damage_all"}),
    BANE_OF_ARTHROPODS ("DAMAGE_ARTHROPODS", "BANE_OF_ARTHROPODS", new String[]{"baneofarthropods", "damagearthropods", "arthropodsdamage", "damage_arthropods"}),
    SMITE ("DAMAGE_UNDEAD", "SMITE", new String[]{"damageundead", "undeaddamage", "damage_undead"}),
    EFFICIENCY ("DIG_SPEED", "EFFICIENCY", new String[]{"digspeed", "dig_speed"}),
    UNBREAKING ("DURABILITY", "UNBREAKING", new String[]{"durability"}),
    FIRE_ASPECT ("FIRE_ASPECT", "FIRE_ASPECT", new String[]{"fireaspect, fire", "fire_aspect"}),
    KNOCKBACK ("KNOCKBACK", "KNOCKBACK", new String[]{"knock", "knockback"}),
    FORTUNE ("LOOT_BONUS_BLOCKS", "FORTUNE", new String[]{"lootbonusblocks", "blockslootbonus", "lootbonusblock", "loot_bonus_blocks"}),
    LOOTING ("LOOT_BONUS_MOBS", "LOOTING", new String[]{"lootbonusmobs", "mobslootbonus", "lootbonusmob"}),
    RESPIRATION ("OXYGEN", "RESPIRATION", new String[]{"waterbreathing", "breathing", "oxygen"}),
    PROTECTION ("PROTECTION_ENVIRONMENTAL", "PROTECTION", new String[]{"protectionenvironmental", "protect", "prot", "protection_environmental"}),
    BLAST_PROTECTION ("PROTECTION_EXPLOSIONS", "BLAST_PROTECTION", new String[]{"blastprotection", "protectionexplosions", "explosionsprotection", "expprotect", "expprot", "protection_explosions"}),
    FEATHER_FALLING ("PROTECTION_FALL", "FEATHER_FALLING", new String[]{"featherfalling", "protectionfall", "fallprotection", "fallprotect", "fallprot", "protection_fall"}),
    FIRE_PROTECTION ("PROTECTION_FIRE", "FIRE_PROTECTION", new String[]{"fireprotection", "protectionfire", "fireprotect", "fireprot", "protection_fire"}),
    PROJECTILE_PROTECTION ("PROTECTION_PROJECTILE", "PROJECTILE_PROTECTION", new String[]{"projectileprotection", "protectionprojectile", "projectileprotect", "projectileprot", "protection_projectile"}),
    SILK_TOUCH ("SILK_TOUCH", "SILK_TOUCH", new String[]{"silktouch", "silk", "silk_touch"}),
    THORNS ("THORNS", "THORNS", new String[]{"thorn", "thorns"}),
    AQUA_AFFINITY ("WATER_WORKER", "AQUA_AFFINITY", new String[]{"aquaaffinity", "waterworker", "water_worker"}),
    LUCK_OF_THE_SEA ("LUCK", "LUCK_OF_THE_SEA", new String[]{"luckofthesea", "luckofsea", "luck"}),
    LURE ("LURE", "LURE", new String[]{"lure", "luring"}),
    DEPTH_STRIDER ("DEPTH_STRIDER", "DEPTH_STRIDER", new String[]{"depthstrider", "striderdepth", "depth_strider"}),
    FROST_WALKER ("FROST_WALKER", "FROST_WALKER", new String[]{"frostwalker", "frost", "frost_walker"}),
    MENDING ("MENDING", "MENDING", new String[]{"mending", "mend"}),
    CURSE_OF_BINDING ("BINDING_CURSE", "CURSE_OF_BINDING", new String[]{"binding", "curseofbinding", "binding_curse"}),
    CURSE_OF_VANISHING ("VANISHING_CURSE", "CURSE_OF_VANISHING", new String[]{"vanishing", "curseofvanishing", "vanishing_curse"}),
    SWEEPING_EDGE ("SWEEPING_EDGE", "SWEEPING_EDGE", new String[]{"sweeping", "sweeping_edge"}),
    LOYALTY ("LOYALTY", "LOYALTY", new String[]{"loyalty"}),
    IMPALING ("IMPALING", "IMPALING", new String[]{"impaling"}),
    RIPTIDE ("RIPTIDE", "RIPTIDE", new String[]{"riptide"}),
    CHANELLING ("CHANNELING", "CHANNELING", new String[]{"channeling"}),

    //1.14
    MULTISHOT("MULTISHOT", "MULTISHOT", new String[]{"multishot"}),
    QUICK_CHARGE("QUICK_CHARGE", "QUICK_CHARGE", new String[]{"quickcharge", "quick_charge"}),
    PIERCING("PIERCING", "PIERCING", new String[]{"piercing"}),

    //1.16
    SOUL_SPEED("SOUL_SPEED", "SOUL_SPEED", new String[]{"soulspeed", "soul_speed"});

    private final String bukkitEnchantment;
    private final String name;
    private final String[] aliases;

    Enchantment(String bukkitEnchantment, String name, String[] aliases) {
        this.bukkitEnchantment = bukkitEnchantment;
        this.name = name;
        this.aliases = aliases;
    }

    public String getBukkitEnchantment() {
        return bukkitEnchantment;
    }

    /**
     * Get an enchantment by its name.
     * @param name The name of the enchantment to get, can be an official name or common name
     * @return The enum representing the enchantment if one was found, null if nothing was found that matched the specified name.
     */
    public static Enchantment getByName(String name) {
        for (Enchantment enchantment : values()) {
            if (enchantment.name.equalsIgnoreCase(name))
                return enchantment;

            if ( Arrays.asList(enchantment.aliases).contains(name.toLowerCase()))
                return enchantment;
        }
        return null;
    }

}
