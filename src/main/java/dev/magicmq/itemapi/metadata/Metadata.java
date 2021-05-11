package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import dev.magicmq.itemapi.utils.MCVersion;
import dev.magicmq.itemapi.utils.exception.EnchantmentNotFoundException;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Wrapper class that contians all basic metadata such as name and lore for most items.
 */
public class Metadata implements Serializable {

    private static final long serialVersionUID = 4593997172606851959L;

    private String name;
    private List<String> lore;
    private boolean unbreakable;
    private List<String> flags;
    private List<Enchantment> enchantments;
    private Integer model;

    /**
     * Create a new Metadata class from scratch with default values.
     */
    public Metadata() {
        this.name = null;
        this.lore = null;
        this.unbreakable = false;
        this.flags = null;
        this.enchantments = null;
        this.model = null;
    }

    /**
     * Create a new Metadata class with values parsed from a configuration section.
     * @param section The section from which the data will be parsed
     */
    public Metadata(WrappedConfigurationSection section) {
        this.name = section.getString("name");
        this.lore = section.getStringList("lore");
        this.unbreakable = section.getBoolean("unbreakable", false);
        this.flags = section.getStringList("item-flags");
        if (section.contains("enchantments")) {
            this.enchantments = new ArrayList<>();
            setEnchantmentsFromConfig(section.getStringList("enchantments"));
        }
        this.model = section.getInt("model");
    }

    /**
     * Create a new Metadata class with values parsed from an existing ItemStack.
     * @param item The item from which metadata will be extracted
     */
    public Metadata(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            this.name = meta.getDisplayName();
            this.lore = meta.getLore();
            this.unbreakable = meta.isUnbreakable();
            this.flags = meta.getItemFlags().stream().map(ItemFlag::name).collect(Collectors.toList());
            if (meta.hasEnchants()) {
                this.enchantments = new ArrayList<>();
                for (Map.Entry<org.bukkit.enchantments.Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                    enchantments.add(new Enchantment(entry.getKey().getKey().getKey(), entry.getValue()));
                }
            } else
                this.enchantments = null;
            if (MCVersion.isCurrentVersionAtLeast(MCVersion.v1_14_R1)) {
                if (meta.hasCustomModelData()) {
                    this.model = meta.getCustomModelData();
                } else
                    this.model = null;
            } else
                this.model = null;
        } else {
            this.name = null;
            this.lore = null;
            this.unbreakable = false;
            this.flags = null;
            this.enchantments = null;
            this.model = null;
        }
    }

    /**
     * Get the display name of the item.
     * @return The display name that is set
     */
    public String getName() {
        return name;
    }

    /**
     * Set the display name of the item.
     * @param name The display name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the lore of the item.
     * @return A mutable list of lore that is set
     */
    public List<String> getLore() {
        return lore;
    }

    /**
     * Set the lore of the item.
     * @param lore A list of lore to set
     */
    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    /**
     * Get if the item is unbreakable.
     * @return True if the item is unbreakable, false if otherwise
     */
    public boolean getUnbreakable() {
        return unbreakable;
    }

    /**
     * Set the item's unbreakable status.
     * @param unbreakable True if the item should be unbreakable, false otherwise
     */
    public void setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
    }

    /**
     * Get the item's associated item flags.
     * @return A mutable list of item flags for the item
     */
    public List<String> getFlags() {
        return flags;
    }

    /**
     * Add an item flag to the item.
     * @param flag An item flag to add
     */
    public void addItemFlag(String flag) {
        flags.add(flag);
    }

    /**
     * Set the item's associated item flags
     * @param flags A list of item flags to set
     */
    public void setFlags(List<String> flags) {
        this.flags = flags;
    }

    /**
     * Get the enchantments associated with the item.
     * @return A mutable list containing all enchantments associated with the item
     * @see dev.magicmq.itemapi.utils.Enchantment
     */
    public List<Enchantment> getEnchantments() {
        return enchantments;
    }

    /**
     * Add an enchantment to the item.
     * @param enchantment The enchantment to add
     * @see dev.magicmq.itemapi.utils.Enchantment
     */
    public void addEnchantment(Enchantment enchantment) {
        enchantments.add(enchantment);
    }

    /**
     * Remove an enchantment from the item.
     * @param enchantment The enchantment to remove
     * @see dev.magicmq.itemapi.utils.Enchantment
     */
    public void removeEnchantment(String enchantment) {
        org.bukkit.enchantments.Enchantment bukkitEnchantment = org.bukkit.enchantments.Enchantment.getByName(dev.magicmq.itemapi.utils.Enchantment.getByName(name).getBukkitEnchantment());
        if (bukkitEnchantment == null)
            throw new EnchantmentNotFoundException("Enchantment " + name + " not found! Please make sure this enchantment is supported for this MC version.");
        enchantments.removeIf(toCheck -> toCheck.getBukkitEnchantment().equals(bukkitEnchantment));
    }

    /**
     * Set the enchantments associated with the item.
     * @param enchantments A list of enchantments to set
     * @see dev.magicmq.itemapi.utils.Enchantment
     */
    public void setEnchantments(List<Enchantment> enchantments) {
        this.enchantments = enchantments;
    }

    /**
     * Get the model of an item.
     * @return An int representing the model of the item.
     */
    public int getModel() {
        return model;
    }

    /**
     * Set the model of an item. This value can be associated with a custom client-side item model.
     * @param model The data to set; use null to clear.
     */
    public void setModel(Integer model) {
        this.model = model;
    }

    /**
     * Apply all metadatata in this class to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     * @throws EnchantmentNotFoundException If an enchantment name was used that could not be found
     */
    public ItemStack applyMetadata(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        if (name != null && !name.isEmpty())
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        if (lore != null && lore.size() > 0)
            meta.setLore(lore.stream().map(string -> ChatColor.translateAlternateColorCodes('&', string)).collect(Collectors.toList()));

        meta.setUnbreakable(unbreakable);

        if (flags != null) {
            for (String flag : flags) {
                meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
            }
        }

        if (enchantments != null) {
            for (Enchantment enchantment : enchantments) {
                org.bukkit.enchantments.Enchantment bukkitEnchantment = enchantment.getBukkitEnchantment();
                meta.addEnchant(bukkitEnchantment, enchantment.getLevel(), true);
            }
        }

        if (MCVersion.isCurrentVersionAtLeast(MCVersion.v1_14_R1)) {
            if (model != null) {
                meta.setCustomModelData(model);
            }
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all metadata contained within this class to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    public void saveToConfig(WrappedConfigurationSection section) {
        if (this.name != null && !this.name.isEmpty())
            section.set("name", this.name.replaceAll("\u00A7", "&"));
        if (this.lore != null && this.lore.size() > 0)
            section.set("lore", this.lore.stream().map(string -> string.replaceAll("\u00A7", "&")).collect(Collectors.toList()));
        if (this.unbreakable)
            section.set("unbreakable", true);
        if (this.flags != null && this.flags.size() > 0)
            section.set("item-flags", this.flags);
        if (this.enchantments != null && this.enchantments.size() > 0) {
            List<String> toSet = new ArrayList<>();
            this.enchantments.forEach(enchantment -> toSet.add(enchantment.name + ":" + enchantment.level));
            section.set("enchantments", toSet);
        }
        if (this.model != null)
            section.set("model", this.model);
    }

    private void setEnchantmentsFromConfig(List<String> enchantments) {
        for (String enchantment : enchantments) {
            String[] split = enchantment.split(":");
            if (split.length == 1) {
                this.enchantments.add(new Enchantment(split[0]));
            } else {
                this.enchantments.add(new Enchantment(split[0], Integer.parseInt(split[1])));
            }
        }
    }

    /**
     * A class used to hold enchantment data.
     */
    static class Enchantment {

        private final String name;
        private final int level;

        /**
         * Initialize a new enchantment with the given name and a level of 1.
         * @param name The name of the enchantment
         */
        public Enchantment(String name) {
            this.name = name;
            this.level = 1;
        }

        /**
         * Initialize a new enchantment with the given name and level.
         * @param name The name of the enchantment
         * @param level The level of the enchantment
         */
        public Enchantment(String name, int level) {
            this.name = name;
            this.level = level;
        }

        /**
         * Get the name of the enchantment.
         * @return The enchantment name
         */
        public String getName() {
            return name;
        }

        /**
         * Get the level of the enchantment.
         * @return The enchantment level
         */
        public int getLevel() {
            return level;
        }

        /**
         * Attempts to get the Bukkit enchantment from the name of this enchantment.
         * @return The Bukkit enchantment associated with this item
         * @throws EnchantmentNotFoundException If no enchantment was found associated with the name of this enchantment
         */
        public org.bukkit.enchantments.Enchantment getBukkitEnchantment() {
            org.bukkit.enchantments.Enchantment bukkitEnchantment = org.bukkit.enchantments.Enchantment.getByName(dev.magicmq.itemapi.utils.Enchantment.getByName(name).getBukkitEnchantment());

            if (bukkitEnchantment == null)
                throw new EnchantmentNotFoundException("Enchantment " + name + " not found! Please make sure this enchantment is supported for this MC version.");

            return bukkitEnchantment;
        }
    }
}
