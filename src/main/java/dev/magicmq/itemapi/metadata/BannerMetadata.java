package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import dev.magicmq.itemapi.utils.Pattern;
import org.bukkit.DyeColor;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class that contians all Metadata associated with Banners.
 */
public class BannerMetadata extends Metadata implements Serializable {

    private static final long serialVersionUID = 4310549535664337336L;

    private List<Pattern> patterns;

    /**
     * Create a new BannerMetadata class from scratch with default values.
     */
    public BannerMetadata() {
        super();

        this.patterns = new ArrayList<>();
    }

    /**
     * Create a new BannerMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item, not the "banner-patterns" section.
     * @param section The section from which the data will be parsed
     */
    public BannerMetadata(WrappedConfigurationSection section) {
        super(section);

        this.patterns = new ArrayList<>();
        WrappedConfigurationSection bannerSection = section.getConfigurationSection("banner-patterns");
        for (String key : bannerSection.getKeys(false)) {
            WrappedConfigurationSection subSection = section.getConfigurationSection(key);
            patterns.add(new Pattern(subSection.getString("color"), subSection.getString("type")));
        }
    }

    /**
     * Create a new BannerMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which banner metadata will be extracted
     */
    public BannerMetadata(ItemStack item) {
        super(item);

        BannerMeta meta = (BannerMeta) item.getItemMeta();
        this.patterns = new ArrayList<>();
        if (meta != null) {
            for (org.bukkit.block.banner.Pattern pattern : meta.getPatterns()) {
                patterns.add(new Pattern(pattern.getColor().name(), pattern.getPattern().toString()));
            }
        }
    }

    /**
     * Get a list of patterns present in this metadata.
     * @return A mutable list containing all patterns associated with this metadata
     * @see Pattern
     */
    public List<Pattern> getPatterns() {
        return patterns;
    }

    /**
     * Add a new pattern to the list of patterns.
     * @param pattern The pattern to add
     * @see Pattern
     */
    public void addPattern(Pattern pattern) {
        patterns.add(pattern);
    }

    /**
     * Set all patterns associated with this banner.
     * @param patterns A list of patterns to set
     */
    public void setPatterns(List<Pattern> patterns) {
        this.patterns = patterns;
    }

    /**
     * Apply the banner metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        BannerMeta meta = (BannerMeta) item.getItemMeta();
        if (patterns != null) {
            for (Pattern pattern : patterns)
                meta.addPattern(new org.bukkit.block.banner.Pattern(DyeColor.valueOf(pattern.getColor()), PatternType.valueOf(pattern.getPatternType())));
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the banner metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        if (this.patterns != null && this.patterns.size() > 0) {
            WrappedConfigurationSection bannerSection = section.createConfigurationSection("banner-patterns");
            int i = 0;
            for (Pattern pattern : this.patterns) {
                WrappedConfigurationSection patternSection = bannerSection.createConfigurationSection("" + i);
                patternSection.set("color", pattern.getColor());
                patternSection.set("type", pattern.getPatternType());
                i++;
            }
        }
    }
}
