package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import dev.magicmq.itemapi.utils.Colors;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.io.File;
import java.io.Serializable;

/**
 * Wrapper class that contians all Metadata associated with Leather Armor.
 */
public class LeatherArmorMetadata extends Metadata implements Serializable {

    private static final long serialVersionUID = -3738849499609361691L;

    private String color;

    /**
     * Create a new LeatherArmorMetadata class from scratch with default values.
     */
    public LeatherArmorMetadata() {
        super();

        this.color = null;
    }

    /**
     * Create a new LeatherArmorMetadata class with values parsed from a configuration section.
     * @param section The section from which the data will be parsed
     */
    public LeatherArmorMetadata(WrappedConfigurationSection section) {
        super(section);

        color = section.getString("armor-color");
    }

    /**
     * Create a new FireworkStarMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which leather armor metadata will be extracted
     */
    public LeatherArmorMetadata(ItemStack item) {
        super(item);

        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        if (meta != null)
            this.color = meta.getColor().getRed() + "," + meta.getColor().getGreen() + "," + meta.getColor().getBlue();
        else
            this.color = null;
    }

    /**
     * Get the color of the leather armor.
     * @return The color, either as a enum value of as a comma-separated RGB string
     */
    public String getColor() {
        return color;
    }

    /**
     * Set the color of the leather armor.
     * @param color The color, either as an enum value of {@link Colors Colors}, {@link DyeColor DyeColor}, or as a comma-separated RGB string
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Apply the leather armor metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        if (color != null) {
            try {
                DyeColor color = DyeColor.valueOf(this.color);
                meta.setColor(color.getColor());
            } catch (IllegalArgumentException ignored) {
                try {
                    Colors color = Colors.valueOf(this.color);
                    meta.setColor(color.getBukkitColor());
                } catch (IllegalArgumentException ignored2) {
                    String[] split = color.split(",");
                    meta.setColor(Color.fromRGB(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
                }
            }
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the leather armor metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        if (this.color != null)
            section.set("armor-color", this.color);
    }
}
