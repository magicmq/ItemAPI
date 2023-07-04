package dev.magicmq.itemapi.metadata;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTCompoundList;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.util.UUID;

/**
 * Wrapper class that contians all Metadata associated with Player Heads/Skulls.
 */
public class PlayerHeadMetadata extends Metadata {

    private static final long serialVersionUID = -1951235728757017265L;

    private String skullOwner;
    private String skinBase64;
    private String skinName;

    /**
     * Create a new PlayerHeadMetadata class from scratch with default values.
     */
    public PlayerHeadMetadata() {
        super();

        this.skullOwner = null;
        this.skinBase64 = null;
    }

    /**
     * Create a new PlayerHeadMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item, not the "player-head-data" section.
     * @param section The section from which the data will be parsed
     */
    public PlayerHeadMetadata(WrappedConfigurationSection section) {
        super(section);

        WrappedConfigurationSection headSection = section.getConfigurationSection("player-head-data");
        skullOwner = headSection.getString("skull-owner");
        skinBase64 = headSection.getString("skin-texture");
        skinName = headSection.getString("skin-name");
    }

    /**
     * Create a new PlayerHeadMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which player head metadata will be extracted
     */
    public PlayerHeadMetadata(ItemStack item) {
        super(item);

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        if (meta != null) {
            if (meta.getOwningPlayer() != null) {
                this.skullOwner = meta.getOwningPlayer().getUniqueId().toString();
            } else {
                NBTItem nbtItem = new NBTItem(item);
                if (nbtItem.hasKey("SkullOwner")) {
                    NBTCompound skull = nbtItem.getCompound("SkullOwner");
                    this.skinName = skull.getString("Name");
                    if (skull.hasKey("Properties")) {
                        NBTCompound properties = nbtItem.getCompound("Properties");
                        if (properties.hasKey("textures")) {
                            NBTCompoundList list = properties.getCompoundList("textures");
                            list.forEach(compound -> {
                                if (compound.hasKey("Value")) {
                                    PlayerHeadMetadata.this.skinBase64 = compound.getString("Value");
                                }
                            });
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the player associated with this player head.
     * Note: This may return null if a custom skin is being used for this player head.
     * @return Either a name or UUID representing the player associated, or null if the skinBase64 value is being used
     */
    public String getSkullOwner() {
        return skullOwner;
    }

    /**
     * Set the player associated with this player head.
     * @param skullOwner The player associated either as a name or UUID, or null if the skinBase64 value is being used
     */
    public void setSkullOwner(String skullOwner) {
        this.skullOwner = skullOwner;
    }

    /**
     * Get the skin value of the player head.
     * Note: This may return null if the skull owner field is being used for this player head.
     * @return The skin of the player head as a Base64 string, or null if the skull owner is set instead
     */
    public String getSkinBase64() {
        return skinBase64;
    }

    /**
     * Set the skin value of the player head.
     * @param skinBase64 The skin of the player head as a Base64 string, or null if the skull owner is set instead
     */
    public void setSkinBase64(String skinBase64) {
        this.skinBase64 = skinBase64;
    }

    /**
     * Get the name of the skin being used.
     * Note: This works in conjunction with the Base64 skin texture value, and can also return null if the skull owner field is being used.
     * @return The name of the skin being used, or null if it is not set or if the skull owner is set instead
     */
    public String getSkinName() {
        return skinName;
    }

    /**
     * Set the name of the skin being used.
     * Note: This works in conjunction with the Base64 skin texture value.
     * @param skinName The name of the skin being used, or null to clear the skin name or if skull owner is being used instead
     */
    public void setSkinName(String skinName) {
        this.skinName = skinName;
    }

    /**
     * Apply the player head metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        if (skullOwner != null) {
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            try {
                meta.setOwningPlayer(Bukkit.getOfflinePlayer(UUID.fromString(skullOwner)));
            } catch (IllegalArgumentException ignored) {
                meta.setOwningPlayer(Bukkit.getOfflinePlayer(skullOwner));
            }
            item.setItemMeta(meta);
        } else if (skinBase64 != null) {
            NBTItem nbtItem = new NBTItem(item);
            NBTCompound skull = nbtItem.addCompound("SkullOwner");
            skull.setString("Name", skinName != null ? skinName : "None");
            skull.setString("Id", UUID.randomUUID().toString());

            NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
            texture.setString("Value", skinBase64);

            item = nbtItem.getItem();
        }

        return item;
    }

    /**
     * Serialize all the player head metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        if (this.skullOwner != null || this.skinBase64 != null || this.skinName != null) {
            WrappedConfigurationSection headSection = section.createConfigurationSection("player-head-data");
            if (this.skullOwner != null)
                headSection.set("skull-owner", this.skullOwner);
            if (this.skinBase64 != null)
                headSection.set("skin-texture", this.skinBase64);
            if (this.skinName != null)
                headSection.set("skin-name", this.skinName);
        }
    }
}
