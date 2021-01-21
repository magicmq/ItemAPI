package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SpawnEggMeta;

import java.io.File;
import java.io.Serializable;

/**
 * Legacy support for spawn eggs prior to 1.13.
 * <b>Warning:</b> This class should not be used in Minecraft version 1.13 and later.
 */
public class SpawnEggMetadata extends Metadata implements Serializable {

    private static final long serialVersionUID = 1252365821063963317L;

    private String entityType;

    /**
     * Create a new SpawnEggMetadata class from scratch with default values.
     */
    public SpawnEggMetadata() {
        super();

        this.entityType = null;
    }

    /**
     * Create a new SpawnEggMetadata class with values parsed from a configuration section.
     * @param section The section from which the data will be parsed
     */
    public SpawnEggMetadata(WrappedConfigurationSection section) {
        super(section);

        this.entityType = section.getString("mob-type");
    }

    /**
     * Create a new SpawnEggMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which spawn egg metadata will be extracted
     */
    public SpawnEggMetadata(ItemStack item) {
        super(item);

        SpawnEggMeta meta = (SpawnEggMeta) item.getItemMeta();
        if (meta != null)
            this.entityType = meta.getSpawnedType().name();
        else
            this.entityType = null;
    }

    /**
     * Get the entity type associated with this spawn egg.
     * @return The entity type of this spawn egg as an enum of {@link EntityType}
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * Set the entity type associated with this spawn egg.
     * @param entityType The entity type to set as an enum of {@link EntityType}
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    /**
     * Apply the spawn egg metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        SpawnEggMeta meta = (SpawnEggMeta) item.getItemMeta();
        if (entityType != null) {
            meta.setSpawnedType(EntityType.valueOf(entityType));
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the spawn egg metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        if (this.entityType != null)
            section.set("mob-type", this.entityType);
    }
}
