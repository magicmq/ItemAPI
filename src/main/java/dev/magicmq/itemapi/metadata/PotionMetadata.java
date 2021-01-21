package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import dev.magicmq.itemapi.utils.Potion;
import dev.magicmq.itemapi.utils.exception.PotionNotFoundException;
import dev.magicmq.itemapi.utils.exception.UpgradeAndExtendException;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.io.File;

/**
 * Wrapper class that contians all Metadata associated with Potions.
 */
public class PotionMetadata extends Metadata {

    private static final long serialVersionUID = 8542900306744297131L;

    private String potionName;
    private int potionLevel;
    private boolean potionExtended;

    /**
     * Create a new PotionMetadata class from scratch with default values.
     */
    public PotionMetadata() {
        super();

        this.potionName = null;
        this.potionLevel = -1;
        this.potionExtended = false;
    }

    /**
     * Create a new PotionMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item, not the "potion-data" section.
     * @param section The section from which the data will be parsed
     */
    public PotionMetadata(WrappedConfigurationSection section) {
        super(section);

        WrappedConfigurationSection potionSection = section.getConfigurationSection("potion-data");
        this.potionName = potionSection.getString("type");
        this.potionLevel = potionSection.getInt("level", 1);
        this.potionExtended = potionSection.getBoolean("extended", false);
    }

    /**
     * Create a new PotionMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which potion metadata will be extracted
     */
    public PotionMetadata(ItemStack item) {
        super(item);

        PotionMeta meta = (PotionMeta) item.getItemMeta();
        if (meta != null) {
            PotionData baseData = meta.getBasePotionData();
            this.potionName = baseData.getType().name();
            this.potionLevel = baseData.isUpgraded() ? 2 : 1;
            this.potionExtended = baseData.isExtended();
        } else {
            this.potionName = null;
            this.potionLevel = -1;
            this.potionExtended = false;
        }
    }

    /**
     * Get the potion type/name.
     * @return An enum or name associated with a potion name in {@link Potion}
     * @see Potion
     */
    public String getPotionName() {
        return potionName;
    }

    /**
     * Set the potion type/name.
     * @param potionName The potion name to set, an enum or name associated with a potion name in {@link Potion}
     * @see Potion
     */
    public void setPotionName(String potionName) {
        this.potionName = potionName;
    }

    /**
     * Get the potion level (if it is upgraded).
     * @return Greater than one if the potion is upgraded, one if it is not upgraded.
     */
    public int getPotionLevel() {
        return potionLevel;
    }

    /**
     * Set the potion level (if it is upgraded).
     * @param potionLevel Any number greater than one if the potion should be upgraded, one if it should not be upgraded
     */
    public void setPotionLevel(int potionLevel) {
        this.potionLevel = potionLevel;
    }

    /**
     * Get whether or not this potion is extended.
     * @return True if the potion is extended, false if otherwise
     */
    public boolean isPotionExtended() {
        return potionExtended;
    }

    /**
     * Set whether or not this potion is extended.
     * @param potionExtended True if it should be extended, false if otherwise
     */
    public void setPotionExtended(boolean potionExtended) {
        this.potionExtended = potionExtended;
    }

    /**
     * Apply the potion metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        PotionMeta meta = (PotionMeta) item.getItemMeta();
        if (this.potionName != null) {
            if (potionLevel > 1 && potionExtended)
                throw new UpgradeAndExtendException("A potion cannot be both extended and upgraded at the same time.");
            Potion potionName = Potion.getByName(this.potionName);
            if (potionName == null)
                throw new PotionNotFoundException("Potion type " + this.potionName + " not found! Please check that the name is correct.");
            try {
                PotionType potionType = PotionType.valueOf(potionName.getBukkitPotionType());
                meta.setBasePotionData(new PotionData(potionType, potionExtended, potionLevel > 1));
            } catch (IllegalArgumentException ignored) {
                throw new PotionNotFoundException("Potion type " + this.potionName + " not found in Bukkit! Please make sure this potion type is supported for this MC version.");
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the potion metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        WrappedConfigurationSection potionSection = section.createConfigurationSection("potion-data");
        if (this.potionName != null)
            potionSection.set("type", this.potionName);
        if (this.potionLevel > -1)
            potionSection.set("level", this.potionLevel);
        if (this.potionExtended)
            potionSection.set("extended", true);
    }
}
