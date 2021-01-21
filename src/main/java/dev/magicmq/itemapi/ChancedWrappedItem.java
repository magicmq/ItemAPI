package dev.magicmq.itemapi;

import dev.magicmq.itemapi.config.WrappedConfigurationSection;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * A {@link WrappedItem WrappedItem} that contains an additional chance variable for use with things such as loot tables.
 * @see WrappedItem
 */
public class ChancedWrappedItem extends WrappedItem implements Serializable {

    private static final long serialVersionUID = 763502558982644462L;

    private double chance;

    /**
     * Create a new ChancedWrappedItem from scratch with a specific material type and default values.
     * @param material The material that the item should be.
     */
    public ChancedWrappedItem(String material) {
        super(material);

        this.chance = 100d;
    }

    /**
     * Create a new GuiWrappedItem using a configuration section. This is functinally the same as calling the {@link ItemAPI#getChancedItem(WrappedConfigurationSection) getChancedItem} method in the ItemAPI class.
     * @param section The configuration section to be parsed
     * @see WrappedConfigurationSection
     */
    public ChancedWrappedItem(WrappedConfigurationSection section) {
        super(section);

        this.chance = section.getDouble("chance", 100d);
    }

    /**
     * Get the chance value for this item
     * @return The chance value associated with this item
     */
    public double getChance() {
        return chance;
    }

    /**
     * Set the chance value for this item
     * @param chance The chance value that the item should have
     */
    public void setChance(double chance) {
        this.chance = chance;
    }

    /**
     * Serialize all data within this ChancedWrappedItem (and its underlying WrappedItem) to a configuration file for later use.
     * @see WrappedItem#saveToConfig(File, WrappedConfigurationSection)
     */
    @Override
    public void saveToConfig(File file, WrappedConfigurationSection section) throws IOException {
        super.saveToConfig(file, section);

        section.set("chance", this.chance);

        section.save(file);
    }
}
