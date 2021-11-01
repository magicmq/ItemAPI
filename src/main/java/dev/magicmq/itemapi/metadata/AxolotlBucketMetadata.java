package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.entity.Axolotl;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.AxolotlBucketMeta;

import java.io.File;

/**
 * Wrapper class that contians all Metadata associated with Axolotl Buckets.
 * <b>Warning:</b> This class should only be used with Minecraft version 1.17 and above.
 * @since MC 1.17
 */
public class AxolotlBucketMetadata extends Metadata {

    private static final long serialVersionUID = -2292855400389426170L;

    private String variant;

    /**
     * Create a new AxolotlBucketMetadata class from scratch with default values.
     */
    public AxolotlBucketMetadata() {
        super();

        this.variant = "LUCY";
    }

    /**
     * Create a new AxolotlBucketMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item
     * @param section The section from which the data will be parsed
     */
    public AxolotlBucketMetadata(WrappedConfigurationSection section) {
        super(section);

        this.variant= section.getString("axolotl-variant");
    }

    /**
     * Create a new AxolotlBucketMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which axolotl bucket metadata will be extracted
     */
    public AxolotlBucketMetadata(ItemStack item) {
        super(item);

        AxolotlBucketMeta meta = (AxolotlBucketMeta) item.getItemMeta();
        if (meta != null) {
            this.variant = meta.getVariant().name();
        } else {
            this.variant = "LUCY";
        }
    }

    /**
     * Get the variant of the axolotl in this axolotl bucket.
     * @return The axolotl variant as an enum of {@link org.bukkit.entity.Axolotl.Variant}
     */
    public String getVariant() {
        return variant;
    }

    /**
     * Set the variant of the axolotl in this axolotl bucket.
     * @param variant The axolotl variant as an enum of {@link org.bukkit.entity.Axolotl.Variant}
     */
    public void setVariant(String variant) {
        this.variant = variant;
    }

    /**
     * Apply the axolotl bucket metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        AxolotlBucketMeta meta = (AxolotlBucketMeta) item.getItemMeta();
        if (variant != null)
            meta.setVariant(Axolotl.Variant.valueOf(variant));

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the axolotl bucket metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        if (this.variant != null)
            section.set("axolotl-variant", this.variant);
    }
}
