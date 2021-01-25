package dev.magicmq.itemapi;

import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.Material;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * A {@link WrappedItem WrappedItem} that contains an additional slot variable designed for use with GUIs.
 * @see WrappedItem
 * @deprecated This method is deprecated in favor of using {@link WrappedItem#getConfigSection()} to obtain a slot value from the config section yourself.
 */
@Deprecated
public class GuiWrappedItem extends WrappedItem implements Serializable {

    private static final long serialVersionUID = 4700804153513839523L;

    private int slot;

    /**
     * Create a new GuiWrappedItem from scratch using a specific material type and default values.
     * @param material The {@link Material Material} that the item should be.
     */
    public GuiWrappedItem(String material) {
        super(material);

        this.slot = 0;
    }

    /**
     * Create a new GuiWrappedItem using a configuration section. This is functinally the same as calling the {@link ItemAPI#getGuiItem(WrappedConfigurationSection) getGuiItem} method in the ItemAPI class.
     * @param section The configuration section to be parsed
     * @see WrappedConfigurationSection
     */
    public GuiWrappedItem(WrappedConfigurationSection section) {
        super(section);

        this.slot = section.getInt("slot", 0);
    }

    /**
     * Get the slot for this item
     * @return The slot
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Set the slot of this item
     * @param slot The slot to set
     */
    public void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * Serialize all data within this GuiWrappedItem (and its underlying WrappedItem) to a configuration file for later use.
     * @see WrappedItem#saveToConfig(File, WrappedConfigurationSection)
     */
    @Override
    public void saveToConfig(File file, WrappedConfigurationSection section) throws IOException {
        super.saveToConfig(file, section);

        section.set("slot", this.slot);

        section.save(file);
    }
}
