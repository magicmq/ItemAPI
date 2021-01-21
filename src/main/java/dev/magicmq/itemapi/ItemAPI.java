package dev.magicmq.itemapi;

import com.google.common.base.Preconditions;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the main class of ItemAPI. Static methods here are designed to interface with more subsurface features of ItemAPI. You may also interface with those classes/methods yourself, but if in doubt, use this class.
 */
public class ItemAPI {

    /**
     * Parse a list of items out of a config file.
     * @param section A WrappedConfigurationSection containing multiple subsections, where each subsection has an item defined within.
     * @return A list of Items that were parsed
     * @see WrappedConfigurationSection
     */
    public static List<WrappedItem> parseItems(WrappedConfigurationSection section) {
        Preconditions.checkArgument(section != null, "section cannot be null!");

        List<WrappedItem> toReturn = new ArrayList<>();
        for (String key : section.getKeys(false)) {
            toReturn.add(new WrappedItem(section.getConfigurationSection(key)));
        }
        return toReturn;
    }

    /**
     * Parse a single item defined in a ConfigurationSection.
     * @param section A ConfigurationSection with the item defined within.
     * @return An {@link WrappedItem Item} representing what was parsed from the ConfigurationSection file.
     * @see WrappedConfigurationSection
     */
    public static WrappedItem parseItem(WrappedConfigurationSection section) {
        Preconditions.checkArgument(section != null, "section cannot be null!");
        
        return new WrappedItem(section);
    }

    public static List<WrappedItem> parseItems(List<ItemStack> items) {
        Preconditions.checkArgument(items != null, "items cannot be null!");

        List<WrappedItem> toReturn = new ArrayList<>();
        for (ItemStack item : items) {
            toReturn.add(new WrappedItem(item));
        }
        return toReturn;
    }

    public static WrappedItem parseItem(ItemStack item) {
        Preconditions.checkArgument(item != null, "item cannot be null!");

        return new WrappedItem(item);
    }

    /**
     * Parse a list of items similar to the {@link #parseItems(WrappedConfigurationSection) parseItems} method, but this method is designed for items part of a loot table or chanced rewards. A "chance" value can be used.
     * @param section A ConfigurationSection containing multiple subsections, where each subsection has an item defined within.
     * @return A list of {@link ChancedWrappedItem ChancedItems} that were parsed.
     * @see WrappedConfigurationSection
     */
    public static List<ChancedWrappedItem> getChancedItems(WrappedConfigurationSection section) {
        Preconditions.checkArgument(section != null, "section cannot be null!");
        
        List<ChancedWrappedItem> toReturn = new ArrayList<>();
        for (String key : section.getKeys(false)) {
            toReturn.add(new ChancedWrappedItem(section.getConfigurationSection(key)));
        }
        return toReturn;
    }

    /**
     * Parse a single item out of a ConfigurationSection. This item also contains a "chance" value for use with things such as loot tables, chanced rewards, etc.
     * @param section A ConfigurationSection with the item defined within.
     * @return A {@link ChancedWrappedItem ChancedItem} wherein the Item as well as the chance value can be obtained.
     * @see WrappedConfigurationSection
     */
    public static ChancedWrappedItem getChancedItem(WrappedConfigurationSection section) {
        Preconditions.checkArgument(section != null, "section cannot be null!");
        
        return new ChancedWrappedItem(section);
    }

    /**
     * Parse a list of items similar to the {@link #parseItems(WrappedConfigurationSection) parseItems} method, but this method is designed for GUI items. Items can also be defined with a slot value in the config.
     * @param section A ConfigurationSection containing multiple subsections, where each subsection has an item defined within.
     * @return A list of Items that were parsed.
     * @see WrappedConfigurationSection
     */
    public static List<GuiWrappedItem> getGuiItems(WrappedConfigurationSection section) {
        Preconditions.checkArgument(section != null, "section cannot be null!");

        List<GuiWrappedItem> toReturn = new ArrayList<>();
        for (String key : section.getKeys(false)) {
            toReturn.add(new GuiWrappedItem(section.getConfigurationSection(key)));
        }
        return toReturn;
    }

    /**
     * Parse a single item defined in a ConfigurationSection. This item also contains a "slot" value designed for use with GUI items.
     * @param section A ConfigurationSection with the item defined within.
     * @return A {@link GuiWrappedItem GuiItem} wherein the Item as well as a slot value can be obtained.
     * @see WrappedConfigurationSection
     */
    public static GuiWrappedItem getGuiItem(WrappedConfigurationSection section) {
        Preconditions.checkArgument(section != null, "section cannot be null!");

        return new GuiWrappedItem(section);
    }
}
