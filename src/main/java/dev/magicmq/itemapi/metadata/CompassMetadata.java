package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

import java.io.File;

/**
 * Wrapper class that contians all Metadata associated with Compasses.
 * <b>Warning:</b> This class should only be used with Minecraft version 1.16 and above.
 * @since MC 1.16
 */
public class CompassMetadata extends Metadata {

    private static final long serialVersionUID = 8466367237673802631L;

    private Lodestone lodestone;
    private boolean tracking;

    public CompassMetadata() {
        super();

        lodestone = null;
        tracking = true;
    }

    public CompassMetadata(WrappedConfigurationSection section) {
        super(section);

        WrappedConfigurationSection compassSection = section.getConfigurationSection("compass-data");
        this.lodestone = new Lodestone(compassSection.getString("lodestone"));
        this.tracking = compassSection.getBoolean("track-lodestone", true);
    }

    public CompassMetadata(ItemStack item) {
        super(item);

        CompassMeta meta = (CompassMeta) item.getItemMeta();
        if (meta != null) {
            Location lodestone = meta.getLodestone();
            if (lodestone != null)
                this.lodestone = new Lodestone(lodestone.getWorld().getName(), lodestone.getBlockX(), lodestone.getBlockY(), lodestone.getBlockZ());
            else
                this.lodestone = null;
            this.tracking = meta.isLodestoneTracked();
        } else {
            this.lodestone = null;
            this.tracking = true;
        }
    }

    /**
     * Get the lodestone that the compass should point to.
     * @return The lodestone associated with this metadata
     */
    public Lodestone getLodestone() {
        return lodestone;
    }

    /**
     * Set the lodestone that the compass should point to.
     * @param lodestone The lodestone to set
     */
    public void setLodestone(Lodestone lodestone) {
        this.lodestone = lodestone;
    }

    /**
     * Get if the compass is actually tracking the lodestone.
     * @return True if the compass is tracking the lodetone, false if it is not
     */
    public boolean isTracking() {
        return tracking;
    }

    /**
     * Set if the compass should track the lodestone.
     * @param tracking True if the compass should track the lodestone, false if it should not
     */
    public void setTracking(boolean tracking) {
        this.tracking = tracking;
    }

    /**
     * Apply the compss metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        CompassMeta meta = (CompassMeta) item.getItemMeta();
        if (lodestone != null)
            meta.setLodestone(lodestone.toLocation());
        meta.setLodestoneTracked(tracking);

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the compass metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        WrappedConfigurationSection compassSection = section.getConfigurationSection("compass-data");
        if (this.lodestone != null)
            compassSection.set("lodestone", this.lodestone.toString());
        compassSection.set("track-lodestone", tracking);
    }

    /**
     * An easily-serializable class to store values that correspond to a compass's lodestone.
     */
    static class Lodestone {

        private String world;
        private int x;
        private int y;
        private int z;

        /**
         * Initialize a new lodestone.
         * @param world The world in which the lodestone is located
         * @param x The x value of the lodestone
         * @param y The y value of the lodestone
         * @param z The z value of the lodestone
         */
        public Lodestone(String world, int x, int y, int z) {
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        /**
         * Initialize a new lodestone from a serialized lodestone string value.
         * @param serialized The serialized lodestone as {@link Lodestone#toString()} would return
         */
        public Lodestone(String serialized) {
            String[] split = serialized.split(":");
            this.world = split[0];
            this.x = Integer.parseInt(split[1]);
            this.y = Integer.parseInt(split[2]);
            this.z = Integer.parseInt(split[3]);
        }

        /**
         * Get the world in which this lodestone is located.
         * @return The name of the world in which this lodestone is located
         */
        public String getWorld() {
            return world;
        }

        /**
         * Set the world associated with this lodestone.
         * @param world The world to set
         */
        public void setWorld(String world) {
            this.world = world;
        }

        /**
         * Get the x value of the lodestone.
         * @return The x value of this lodestone
         */
        public int getX() {
            return x;
        }

        /**
         * Set the x value of this lodestone.
         * @param x The x value to set
         */
        public void setX(int x) {
            this.x = x;
        }

        /**
         * Get the y value of the lodestone.
         * @return The y value of this lodestone
         */
        public int getY() {
            return y;
        }

        /**
         * Set the y value of this lodestone.
         * @param y The y value to set
         */
        public void setY(int y) {
            this.y = y;
        }

        /**
         * Get the z value of the lodestone.
         * @return The z value of this lodestone
         */
        public int getZ() {
            return z;
        }

        /**
         * Set the z value of this lodestone.
         * @param z The z value to set
         */
        public void setZ(int z) {
            this.z = z;
        }

        /**
         * Convert this lodestone object to a {@link org.bukkit.Location} object.
         * @return The Bukkit location representing this lodestone
         */
        public Location toLocation() {
            return new Location(Bukkit.getWorld(world), x, y, z);
        }

        /**
         * Get a string representing this lodestone for writing to config files.
         * @return The string value representing this lodestone
         */
        @Override
        public String toString() {
            return world + ":" + x + ":" + y + ":" + z;
        }
    }
}
