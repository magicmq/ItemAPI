package dev.magicmq.itemapi;

import com.google.common.base.Preconditions;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

/**
 * This is the main class of ItemAPI. Static methods here are designed to interface with more subsurface features of ItemAPI. You may also interface with those classes/methods yourself, but if in doubt, use this class.
 */
public class ItemAPI {

    /**
     * Parse a list of items out of a config file.
     * @param section A WrappedConfigurationSection containing multiple subsections, where each subsection has an item defined within
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
     * Parse a list of items out of a config file. Unlike {@link ItemAPI#parseItems(WrappedConfigurationSection)}, this method will return a HashMap where the key is the name of the configuration section for each item that was parsed.
     * @param section A WrappedConfigurationSection containing multiple subsections, where each subsection has an item defined within
     * @return A HashMap of WrappedItems that were parsed, where each key was the config section name for that item and the value is the item
     * @see WrappedConfigurationSection
     */
    public static HashMap<String, WrappedItem> parseItemsWithKeys(WrappedConfigurationSection section) {
        Preconditions.checkArgument(section != null, "section cannot be null!");

        HashMap<String, WrappedItem> toReturn = new HashMap<>();
        for (String key : section.getKeys(false)) {
            toReturn.put(key, new WrappedItem(section.getConfigurationSection(key)));
        }
        return toReturn;
    }

    /**
     * Parse a single item defined in a ConfigurationSection.
     * @param section A ConfigurationSection with the item defined within
     * @return An {@link WrappedItem Item} representing what was parsed from the ConfigurationSection file
     * @see WrappedConfigurationSection
     */
    public static WrappedItem parseItem(WrappedConfigurationSection section) {
        Preconditions.checkArgument(section != null, "section cannot be null!");
        
        return new WrappedItem(section);
    }

    /**
     * Parse a list of ItemStacks into WrappedItems for later serialization or other use with ItemAPI.
     * @param items A list of items to parse
     * @return A list of WrappedItems that were parsed
     */
    public static List<WrappedItem> parseItems(List<ItemStack> items) {
        Preconditions.checkArgument(items != null, "items cannot be null!");

        List<WrappedItem> toReturn = new ArrayList<>();
        for (ItemStack item : items) {
            toReturn.add(new WrappedItem(item));
        }
        return toReturn;
    }

    /**
     * Parse an ItemStack into a WrappedItem for later serialization or other use with ItemAPI.
     * @param item The item to parse
     * @return A WrappedItem representing the ItemStack that was parsed
     */
    public static WrappedItem parseItem(ItemStack item) {
        Preconditions.checkArgument(item != null, "item cannot be null!");

        return new WrappedItem(item);
    }

    /**
     * Parse a Base64-encoded String into a WrappedItem.
     * @param base64 A Base64 string representing the WrappedItem to be deserialized
     * @return A WrappedItem representing the Base64 string that was parsed
     * @throws IOException If parsing the Base64 string failed due to an I/O issue
     * @throws ClassNotFoundException If the class being read from the Base64 string cannot be found or if a WrappedItem could not be extracted from the Base64 string
     */
    public static WrappedItem parseItem(String base64) throws IOException, ClassNotFoundException {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(base64);

        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream input = new ObjectInputStream(bais)) {
            Object object = input.readObject();
            if (object instanceof WrappedItem)
                return (WrappedItem) object;
            else
                throw new ClassNotFoundException("WrappedItem could not be parsed from the Base64 string!");
        }
    }

    /**
     * Parse a list of items similar to the {@link #parseItems(WrappedConfigurationSection) parseItems} method, but this method is designed for items part of a loot table or chanced rewards. A "chance" value can be used.
     * @param section A ConfigurationSection containing multiple subsections, where each subsection has an item defined within
     * @return A list of {@link ChancedWrappedItem ChancedItems} that were parsed
     * @see WrappedConfigurationSection
     * @deprecated Deprecated. See {@link ChancedWrappedItem} for alternative usage
     */
    @Deprecated
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
     * @param section A ConfigurationSection with the item defined within
     * @return A {@link ChancedWrappedItem ChancedItem} wherein the Item as well as the chance value can be obtained
     * @see WrappedConfigurationSection
     * @deprecated Deprecated. See {@link ChancedWrappedItem} for alternative usage
     */
    @Deprecated
    public static ChancedWrappedItem getChancedItem(WrappedConfigurationSection section) {
        Preconditions.checkArgument(section != null, "section cannot be null!");
        
        return new ChancedWrappedItem(section);
    }

    /**
     * Parse a list of items similar to the {@link #parseItems(WrappedConfigurationSection) parseItems} method, but this method is designed for GUI items. Items can also be defined with a slot value in the config.
     * @param section A ConfigurationSection containing multiple subsections, where each subsection has an item defined within
     * @return A list of Items that were parsed
     * @see WrappedConfigurationSection
     * @deprecated Deprecated. See {@link GuiWrappedItem} for alternative usage
     */
    @Deprecated
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
     * @param section A ConfigurationSection with the item defined within
     * @return A {@link GuiWrappedItem GuiItem} wherein the Item as well as a slot value can be obtained
     * @see WrappedConfigurationSection
     * @deprecated Deprecated. See {@link GuiWrappedItem} for alternative usage
     */
    @Deprecated
    public static GuiWrappedItem getGuiItem(WrappedConfigurationSection section) {
        Preconditions.checkArgument(section != null, "section cannot be null!");

        return new GuiWrappedItem(section);
    }
}
