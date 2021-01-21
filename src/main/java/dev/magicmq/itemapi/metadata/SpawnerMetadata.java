package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.io.File;
import java.io.Serializable;

/**
 * Wrapper class that contians all Metadata associated with Spawners.
 */
public class SpawnerMetadata extends Metadata implements Serializable {

    private static final long serialVersionUID = 381573329107207808L;

    private String entityType;

    /**
     * Create a new SpawnerMetadata class from scratch with default values.
     */
    public SpawnerMetadata() {
        super();

        this.entityType = null;
    }

    /**
     * Create a new SpawnerMetadata class with values parsed from a configuration section.
     * @param section The section from which the data will be parsed
     */
    public SpawnerMetadata(WrappedConfigurationSection section) {
        super(section);

        this.entityType = section.getString("mob-type");
    }

    /**
     * Create a new SpawnerMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which spawner metadata will be extracted
     */
    public SpawnerMetadata(ItemStack item) {
        super(item);

        BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
        CreatureSpawner spawner = (CreatureSpawner) meta.getBlockState();

        this.entityType = spawner.getSpawnedType().name();
    }

    /**
     * Get the entity type associated with this spawner.
     * @return The entity type of this spawner as an enum of {@link EntityType}
     */
    public String getEntityType() {
        return entityType;
    }

    /**
     * Set the entity type associated with this spawner.
     * @param entityType The entity type to set as an enum of {@link EntityType}
     */
    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    /**
     * Apply the spawner metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
        CreatureSpawner spawner = (CreatureSpawner) meta.getBlockState();
        if (entityType != null)
            spawner.setSpawnedType(EntityType.valueOf(entityType));

        spawner.update();
        meta.setBlockState(spawner);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the spawner metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        if (this.entityType != null) {
            section.set("mob-type", this.entityType);
        }
    }
}
