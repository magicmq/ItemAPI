package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BundleMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper class that contains all Metadata associated with bundles.
 */
public class BundleMetadata extends Metadata {

    private static final long serialVersionUID = 1340768888950718922L;

    private List<WrappedItem> items;

    /**
     * Create a new BundleMetadata class from scratch with default values.
     */
    public BundleMetadata() {
        super();

        this.items = new ArrayList<>();
    }

    /**
     * Create a new BundleMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item, not the "bundle-items" section.
     * @param section The section from which the data will be parsed
     */
    public BundleMetadata(WrappedConfigurationSection section) {
        super(section);

        this.items = new ArrayList<>();
        WrappedConfigurationSection bundleSection = section.getConfigurationSection("bundle-items");
        for (String key : bundleSection.getKeys(false)) {
            WrappedConfigurationSection subSection = bundleSection.getConfigurationSection(key);
            items.add(new WrappedItem(subSection));
        }
    }

    /**
     * Create a new BundleMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which bundle metadata will be extracted
     */
    public BundleMetadata(ItemStack item) {
        super(item);

        this.items = new ArrayList<>();
        BundleMeta meta = (BundleMeta) item.getItemMeta();
        for (ItemStack itemStack : meta.getItems())
            this.items.add(new WrappedItem(itemStack));
    }

    /**
     * Get the contents of the bundle.
     * @return A mutable List containing the contents of the bundle
     */
    public List<WrappedItem> getItems() {
        return items;
    }

    /**
     * Add an item to the bundle.
     * @param item The item to add to the bundle contents
     */
    public void addItem(WrappedItem item) {
        items.add(item);
    }

    /**
     * Set the entire contents of the bundle. Setting to an empty list or null will clear the bundle's contents.
     * @param items A list containing the items that should be set to the bundle contents
     */
    public void setItems(List<WrappedItem> items) {
        this.items = items;
    }

    /**
     * Apply the bundle metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        BundleMeta meta = (BundleMeta) item.getItemMeta();
        meta.setItems(items.stream().map(WrappedItem::getAsItemStack).collect(Collectors.toList()));

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the bundle metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        if (this.items != null && this.items.size() > 0) {
            WrappedConfigurationSection bundleSection = section.createConfigurationSection("bundle-items");
            int i = 0;
            for (WrappedItem item : items) {
                WrappedConfigurationSection itemSection = bundleSection.createConfigurationSection("" + i);
                item.writeToSection(itemSection);
            }
        }
    }
}
