package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

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
        for (Map.Entry<org.bukkit.enchantments.Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
            this.enchantments.add(new Enchantment(entry.getKey().getKey().getKey(), entry.getValue()));
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
     * Add an enchantment to the enchanted book
     * @param enchantment The enchantment to add
     * @see dev.magicmq.itemapi.utils.Enchantment
     */
    public void addEnchantment(Enchantment enchantment) {
        enchantments.add(enchantment);
    }

    /**
     * Set the enchantments associated with the item.
     * @param enchantments A list of enchantments to set
     * @see dev.magicmq.itemapi.utils.Enchantment
     */
    public void setEnchantments(List<Enchantment> enchantments) {
        this.enchantments = enchantments;
    }
}
