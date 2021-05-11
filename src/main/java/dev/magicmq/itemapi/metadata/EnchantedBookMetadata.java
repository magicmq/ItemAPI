package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import dev.magicmq.itemapi.utils.MCVersion;
import dev.magicmq.itemapi.utils.exception.EnchantmentNotFoundException;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Wrapper class that contains all Metadata associated with Enchanted Books.
 */
public class EnchantedBookMetadata extends Metadata {

    private static final long serialVersionUID = 206153984688756194L;

    private List<Enchantment> enchantments;

    /**
     * Create a new EnchantedBookMetadata class from scratch with default values.
     */
    public EnchantedBookMetadata() {
        super();

        this.enchantments = new ArrayList<>();
    }

    /**
     * Create a new EnchantedBookMetadata class with values parsed from a configuration section.
     * @param section The section from which the data will be parsed
     */
    public EnchantedBookMetadata(WrappedConfigurationSection section) {
        super(section);

        this.enchantments = new ArrayList<>();
        for (String enchantment : section.getStringList("book-enchantments")) {
            String[] split = enchantment.split(":");
            if (split.length == 1) {
                this.enchantments.add(new Enchantment(split[0]));
            } else {
                this.enchantments.add(new Enchantment(split[0], Integer.parseInt(split[1])));
            }
        }
    }

    /**
     * Create a new EnchantedBookMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which enchanted book metadata will be extracted
     */
    public EnchantedBookMetadata(ItemStack item) {
        super(item);

        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        this.enchantments = new ArrayList<>();
        if (meta != null) {
            for (Map.Entry<org.bukkit.enchantments.Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
                if (MCVersion.isCurrentVersionAtLeast(MCVersion.v1_13_R1))
                    this.enchantments.add(new Enchantment(entry.getKey().getKey().getKey(), entry.getValue()));
                else
                    this.enchantments.add(new Enchantment(entry.getKey().getName(), entry.getValue()));
            }
        }
    }

    /**
     * Get the enchantments associated with this enchanted book.
     * @return A mutable list containing all enchantments associated with the enchanted book
     * @see dev.magicmq.itemapi.utils.Enchantment
     */
    public List<Enchantment> getEnchantments() {
        return enchantments;
    }

    /**
     * Add an enchantment to the enchanted book.
     * @param enchantment The enchantment to add
     * @see dev.magicmq.itemapi.utils.Enchantment
     */
    public void addEnchantment(Enchantment enchantment) {
        enchantments.add(enchantment);
    }

    /**
     * Remove an enchantment from the enchanted book.
     * @param enchantment The name of the enchantment to remove
     * @see dev.magicmq.itemapi.utils.Enchantment
     */
    public void removeEnchantment(String enchantment) {
        org.bukkit.enchantments.Enchantment bukkitEnchantment = org.bukkit.enchantments.Enchantment.getByName(dev.magicmq.itemapi.utils.Enchantment.getByName(enchantment).getBukkitEnchantment());
        if (bukkitEnchantment == null)
            throw new EnchantmentNotFoundException("Enchantment " + enchantment + " not found! Please make sure this enchantment is supported for this MC version.");
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
     * Apply the enchanted book metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
        if (enchantments != null) {
            for (Enchantment enchantment : enchantments) {
                org.bukkit.enchantments.Enchantment bukkitEnchantment = enchantment.getBukkitEnchantment();
                meta.addEnchant(bukkitEnchantment, enchantment.getLevel(), true);
            }
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the enchanted book metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        List<String> toSet = new ArrayList<>();
        this.enchantments.forEach(enchantment -> toSet.add(enchantment.getName() + ":" + enchantment.getLevel()));
        section.set("enchantments", toSet);
    }
}
