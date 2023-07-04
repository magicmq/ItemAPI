package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.block.ShulkerBox;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper class that contians all Metadata associated with Shulker Boxes.
 */
public class ShulkerBoxMetadata extends Metadata {

    private static final long serialVersionUID = 4869406315206615339L;

    private HashMap<WrappedItem, Integer> contents;

    /**
     * Create a new ShulkerBoxMetadata class from scratch with default values.
     */
    public ShulkerBoxMetadata() {
        super();

        this.contents = new HashMap<>();
    }

    /**
     * Create a new ShulkerBoxMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item, not the "shulker-box-items" section.
     * @param section The section from which the data will be parsed
     */
    public ShulkerBoxMetadata(WrappedConfigurationSection section) {
        super(section);

        this.contents = new HashMap<>();
        WrappedConfigurationSection shulkerSection = section.getConfigurationSection("shulker-box-items");
        for (String key : shulkerSection.getKeys(false)) {
            WrappedConfigurationSection subSection = shulkerSection.getConfigurationSection(key);
            int slot = subSection.getInt("slot", -1);
            contents.put(new WrappedItem(subSection), slot == -1 ? null : slot);
        }
    }

    /**
     * Create a new ShulkerBoxMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which shulker box metadata will be extracted
     */
    public ShulkerBoxMetadata(ItemStack item) {
        super(item);

        this.contents = new HashMap<>();
        BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
        if (meta != null) {
            ShulkerBox shulkerBox = (ShulkerBox) meta.getBlockState();
            ItemStack[] contents = shulkerBox.getSnapshotInventory().getContents();
            for (int i = 0; i < contents.length; i++) {
                if (contents[i] == null)
                    continue;

                this.contents.put(new WrappedItem(item), i);
            }
        } else {
            this.contents = new HashMap<>();
        }
    }

    /**
     * Get the contents of the shulker box intentory along with their respective slots. <b>Note:</b> The value (slot) may be null if the item does not have a defined slot. If this is the case, then the item is added to the shulker box inventory in the first available slot.
     * @return A mutable HashMap containing the contents of the inventory; the key is the item and the value is the slot number (could be null)
     */
    public HashMap<WrappedItem, Integer> getContents() {
        return contents;
    }

    /**
     * Add an item to the shulker box inventory with no set slot. The item will be added in the first avaiable slot.
     * @param item The item to add to the shulker box
     */
    public void addItem(WrappedItem item) {
        addItem(item, null);
    }

    /**
     * Add an item to the shulker box inventory in a specific slot.
     * @param item The item to add to the shulker box
     * @param slot The inventory slot in which the item should be placed
     */
    public void addItem(WrappedItem item, Integer slot) {
        if (contents == null)
            contents = new HashMap<>();

        contents.put(item, slot);
    }

    /**
     * Set the entire contents of the shulker box inventory. Set the slot value to null if the item does not need to have a specific slot. Setting to an empty HashMap or null will clear the contents of the shulker box.
     * @param contents A HashMap representing the contents of the inventory where the keys are the items and the values are their slots
     */
    public void setContents(HashMap<WrappedItem, Integer> contents) {
        this.contents = contents;
    }

    /**
     * Apply the shulker box metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
        ShulkerBox shulkerBox = (ShulkerBox) meta.getBlockState();
        Inventory inventory = shulkerBox.getSnapshotInventory();
        for (Map.Entry<WrappedItem, Integer> toAdd : contents.entrySet()) {
            WrappedItem itemToAdd = toAdd.getKey();
            if (itemToAdd.getMaterial().contains("SHULKER_BOX"))
                throw new UnsupportedOperationException("Shulker boxes cannot be nested within other shulker boxes!");

            if (toAdd.getValue() == null)
                inventory.addItem(itemToAdd.getAsItemStack());
            else
                inventory.setItem(toAdd.getValue(), itemToAdd.getAsItemStack());
        }

        shulkerBox.update();
        meta.setBlockState(shulkerBox);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the shulker box metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        if (this.contents != null && this.contents.size() > 0) {
            WrappedConfigurationSection shulkerSection = section.createConfigurationSection("shulker-box-items");
            int i = 0;
            for (Map.Entry<WrappedItem, Integer> entry : this.contents.entrySet()) {
                WrappedConfigurationSection itemSection = shulkerSection.createConfigurationSection("" + i);
                if (entry.getValue() != null)
                    itemSection.set("slot", entry.getValue());
                entry.getKey().writeToSection(itemSection);
                i++;
            }
        }
    }
}
