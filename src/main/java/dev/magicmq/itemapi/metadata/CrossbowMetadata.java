package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper class that contians all Metadata associated with Crossbows
 */
public class CrossbowMetadata extends Metadata {

    private static final long serialVersionUID = 7611993326680363375L;

    private List<WrappedItem> chargedProjectiles;

    /**
     * Create a new CrossbowMetadata class from scratch with default values.
     */
    public CrossbowMetadata() {
        super();

        this.chargedProjectiles = new ArrayList<>();
    }

    /**
     * Create a new CrossbowMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item
     * @param section The section from which the data will be parsed
     */
    public CrossbowMetadata(WrappedConfigurationSection section) {
        super(section);

        this.chargedProjectiles = new ArrayList<>();
        WrappedConfigurationSection chargedProjSection = section.getConfigurationSection("charged-projectiles");
        for (String key : chargedProjSection.getKeys(false)) {
            WrappedConfigurationSection subSection = chargedProjSection.getConfigurationSection(key);
            chargedProjectiles.add(new WrappedItem(subSection));
        }
    }

    /**
     * Create a new CrossbowMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which knowledge book metadata will be extracted
     */
    public CrossbowMetadata(ItemStack item) {
        super(item);

        CrossbowMeta meta = (CrossbowMeta) item.getItemMeta();
        if (meta != null) {
            meta.getChargedProjectiles().forEach(charge -> chargedProjectiles.add(new WrappedItem(charge)));
        }
    }

    /**
     * Get a list of charges loaded into the Crossbow associated with this metadata
     * @return A mutable list containing all loaded charges
     */
    public List<WrappedItem> getChargedProjectiles() {
        return chargedProjectiles;
    }

    /**
     * Add a new charge to the Crossbow associated with this metadata.
     * @param charge The projectile to add as a charge
     */
    public void addCharge(WrappedItem charge) {
        chargedProjectiles.add(charge);
    }

    /**
     * Set all charges associated with this Crossbow
     * @param chargedProjectiles A list of charges to set
     */
    public void setRecipes(List<WrappedItem> chargedProjectiles) {
        this.chargedProjectiles = chargedProjectiles;
    }

    /**
     * Apply the crossbow metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        CrossbowMeta meta = (CrossbowMeta) item.getItemMeta();
        if (chargedProjectiles != null) {
            meta.setChargedProjectiles(chargedProjectiles.stream().map(WrappedItem::getAsItemStack).collect(Collectors.toList()));
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the crossbow metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        WrappedConfigurationSection shulkerSection = section.createConfigurationSection("charged-projetiles");
        if (this.chargedProjectiles != null && this.chargedProjectiles.size() > 0) {
            int i = 0;
            for (WrappedItem item : chargedProjectiles) {
                WrappedConfigurationSection chargeSection = shulkerSection.createConfigurationSection("" + i);
                item.writeToSection(chargeSection);
                i++;
            }
        }
    }
}
