package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import dev.magicmq.itemapi.utils.Pattern;
import org.bukkit.DyeColor;
import org.bukkit.block.Banner;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class that contians all Metadata associated with Shields.
 */
public class ShieldMetadata extends Metadata implements Serializable {

    private static final long serialVersionUID = 7137905741683612278L;

    private String baseColor;
    private List<Pattern> patterns;

    /**
     * Create a new ShieldMetadata class from scratch with default values.
     */
    public ShieldMetadata() {
        super();

        this.baseColor = "WHITE";
        this.patterns = new ArrayList<>();
    }

    /**
     * Create a new ShieldMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item, not the "shield-patterns" section.
     * @param section The section from which the data will be parsed
     */
    public ShieldMetadata(WrappedConfigurationSection section) {
        super(section);

        this.patterns = new ArrayList<>();
        WrappedConfigurationSection shieldSection = section.getConfigurationSection("shield-patterns");
        baseColor = section.getString("base-color");
        for (String key : shieldSection.getKeys(false)) {
            WrappedConfigurationSection subSection = shieldSection.getConfigurationSection(key);
            patterns.add(new Pattern(subSection.getString("color"), subSection.getString("type")));
        }
    }

    /**
     * Create a new ShieldMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which shield metadata will be extracted
     */
    public ShieldMetadata(ItemStack item) {
        super(item);

        BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
        if (meta != null) {
            Banner banner = (Banner) meta.getBlockState();
            this.baseColor = banner.getBaseColor().name();
            this.patterns = new ArrayList<>();
            for (org.bukkit.block.banner.Pattern pattern : banner.getPatterns()) {
                patterns.add(new Pattern(pattern.getColor().name(), pattern.getPattern().toString()));
            }
        } else {
            this.baseColor = "WHITE";
            this.patterns = new ArrayList<>();
        }

    }

    /**
     * Get the base color of the shield.
     * @return The base color as an enum of {@link DyeColor}
     */
    public String getBaseColor() {
        return baseColor;
    }

    /**
     * Set the base color of the shield.
     * @param baseColor The base color to set as an enum of {@link DyeColor}
     */
    public void setBaseColor(String baseColor) {
        this.baseColor = baseColor;
    }

    /**
     * Get a list of patterns associated with the shield.
     * @return A mutable list containing all patterns on the shield
     * @see Pattern
     */
    public List<Pattern> getPatterns() {
        return patterns;
    }

    /**
     * Add a pattern to the shield.
     * @param pattern The pattern to add
     * @see Pattern
     */
    public void addPattern(Pattern pattern) {
        patterns.add(pattern);
    }

    /**
     * Set all patterns associated with the shield.
     * @param patterns A list of patterns to set
     * @see Pattern
     */
    public void setPatterns(List<Pattern> patterns) {
        this.patterns = patterns;
    }

    /**
     * Apply the shield metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
        Banner banner = (Banner) meta.getBlockState();
        if (baseColor != null)
            banner.setBaseColor(DyeColor.valueOf(baseColor));
        if (patterns != null) {
            for (Pattern pattern : patterns)
                banner.addPattern(new org.bukkit.block.banner.Pattern(DyeColor.valueOf(pattern.getColor()), PatternType.valueOf(pattern.getPatternType())));
        }

        banner.update();
        meta.setBlockState(banner);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the shield metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        if (this.baseColor != null)
            section.set("base-color", this.baseColor);
        WrappedConfigurationSection shieldSection = section.createConfigurationSection("shield-patterns");
        if (this.patterns != null && this.patterns.size() > 0) {
            int i = 0;
            for (Pattern pattern : this.patterns) {
                WrappedConfigurationSection patternSection = shieldSection.createConfigurationSection("" + i);
                patternSection.set("color", pattern.getColor());
                patternSection.set("type", pattern.getPatternType());
                i++;
            }
        }
    }
}
