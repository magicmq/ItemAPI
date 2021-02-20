package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.DyeColor;
import org.bukkit.entity.TropicalFish;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.TropicalFishBucketMeta;

import java.io.File;

/**
 * Wrapper class that contians all Metadata associated with Tropical Fish Buckets.
 * <b>Warning:</b> This class should only be used with Minecraft version 1.13 and above.
 */
public class TropicalFishBucketMetadata extends Metadata {

    private static final long serialVersionUID = 227634342010266649L;

    private String bodyColor;
    private String pattern;
    private String patternColor;

    /**
     * Create a new TropicalFishBucketMetadata class from scratch with default values.
     */
    public TropicalFishBucketMetadata() {
        super();

        this.bodyColor = "WHITE";
        this.pattern = "FLOPPER";
        this.patternColor = "ORANGE";
    }

    /**
     * Create a new TropicalFishBucketMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item, not the "tropical-fish-bucket-data" section.
     * @param section The section from which the data will be parsed
     */
    public TropicalFishBucketMetadata(WrappedConfigurationSection section) {
        super(section);

        WrappedConfigurationSection tropicalSection = section.getConfigurationSection("tropical-fish-bucket-data");
        this.bodyColor = tropicalSection.getString("body-color");
        this.pattern = tropicalSection.getString("pattern");
        this.patternColor = tropicalSection.getString("pattern-color");
    }

    /**
     * Create a new TropicalFishBucketMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which tropical fish bucket metadata will be extracted
     */
    public TropicalFishBucketMetadata(ItemStack item) {
        super(item);

        TropicalFishBucketMeta meta = (TropicalFishBucketMeta) item.getItemMeta();
        if (meta != null) {
            this.bodyColor = meta.getBodyColor().name();
            this.pattern = meta.getPattern().name();
            this.patternColor = meta.getPatternColor().name();
        } else {
            this.bodyColor = "WHITE";
            this.pattern = "FLOPPER";
            this.patternColor = "ORANGE";
        }
    }

    /**
     * Get the fish's body color.
     * @return The body color of the fish as an enum of {@link DyeColor}
     */
    public String getBodyColor() {
        return bodyColor;
    }

    /**
     * Set the fish's body color.
     * @param bodyColor The body color to set as an enum of {@link DyeColor}
     */
    public void setBodyColor(String bodyColor) {
        this.bodyColor = bodyColor;
    }

    /**
     * Get the pattern of the fish.
     * @return The pattern of the fish as an enum of {@link org.bukkit.entity.TropicalFish.Pattern}
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Set the pattern of the fish.
     * @param pattern The pattern of the fish to set as an enum of {@link org.bukkit.entity.TropicalFish.Pattern}
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    /**
     * Get the pattern color of the fish.
     * @return The pattern color of the fish as an enum of {@link DyeColor}
     */
    public String getPatternColor() {
        return patternColor;
    }

    /**
     * Set the pattern color of the fish.
     * @param patternColor The pattern color to set as an enum of {@link DyeColor}
     */
    public void setPatternColor(String patternColor) {
        this.patternColor = patternColor;
    }

    /**
     * Apply the tropical fish bucket metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        TropicalFishBucketMeta meta = (TropicalFishBucketMeta) item.getItemMeta();
        if (bodyColor != null)
            meta.setBodyColor(DyeColor.valueOf(bodyColor));
        if (pattern != null)
            meta.setPattern(TropicalFish.Pattern.valueOf(pattern));
        if (patternColor != null)
            meta.setPatternColor(DyeColor.valueOf(patternColor));

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the tropical fish bucket metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        WrappedConfigurationSection tropicalSection = section.createConfigurationSection("tropical-fish-bucket-data");
        if (this.bodyColor != null)
            tropicalSection.set("body-color", this.bodyColor);
        if (this.pattern != null)
            tropicalSection.set("pattern", this.pattern);
        if (this.patternColor != null)
            tropicalSection.set("pattern-color", this.patternColor);
    }
}
