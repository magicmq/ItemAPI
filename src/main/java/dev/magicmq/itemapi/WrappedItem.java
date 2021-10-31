package dev.magicmq.itemapi;

import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import dev.magicmq.itemapi.metadata.*;
import dev.magicmq.itemapi.nbt.NBTData;
import dev.magicmq.itemapi.utils.DamageUtil;
import dev.magicmq.itemapi.utils.MCVersion;
import dev.magicmq.itemapi.utils.exception.IncorrectMetaException;
import dev.magicmq.itemapi.utils.exception.VersionNotSupportedException;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.io.*;
import java.util.Base64;

public class WrappedItem implements Serializable {

    private static final long serialVersionUID = -2714617059791004452L;

    private transient WrappedConfigurationSection section;

    private String material;
    private int amount;
    private short damage;
    private NBTData nbtData;
    private Metadata metadata;

    /**
     * Create a new WrappedItem with a certain Material type and default values.
     * @param material The {@link Material Material} that the item should be
     */
    public WrappedItem(String material) {
        this.material = material;
        this.amount = 1;
        this.damage = 0;

        this.nbtData = new NBTData();

        this.metadata = new Metadata();
    }

    /**
     * Create a new WrappedItem from a configuration section. This is functionally the same as calling the {@link ItemAPI#parseItem(WrappedConfigurationSection) parseItem} method in the ItemAPI class.
     * @param section A subclass of WrappedConfigurationSection, depending on your configuration API of choice
     * @see WrappedConfigurationSection
     */
    public WrappedItem(WrappedConfigurationSection section) {
        this.section = section;

        //Basic item properties
        this.material = section.getString("material", "STONE").toUpperCase();
        this.amount = section.getInt("amount", 1);
        this.damage = (short) section.getInt("damage", 0);

        //NBT
        if (section.contains("nbt"))
            this.nbtData = new NBTData(section.getConfigurationSection("nbt"));
        else
            this.nbtData = new NBTData();

        //Spawn Eggs
        if (material.matches("(.*)_SPAWN_EGG")) {
            if (!MCVersion.isCurrentVersionAtLeast(MCVersion.v1_13_R1))
                throw new VersionNotSupportedException("Use the \"spawn-egg\" config value to set the mob type of spawn eggs. Versions lower than 1.13 do not have different material names for different spawn eggs.");
        } else {
            if (material.equals("MONSTER_EGG") ||
                    material.equals("MONSTER_EGGS") ||
                    material.equals("LEGACY_MONSTER_EGG") ||
                    material.equals("LEGACY_MONSTER_EGGS")) {
                if (MCVersion.isCurrentVersionAtLeast(MCVersion.v1_13_R1))
                    throw new VersionNotSupportedException("Use the material name to spawn the specific spawn egg you need (BAT_SPAWN_EGG, for example). Spawn eggs now have different mateial names and the mob type does not need to be specified separately.");
            }
        }

        //Metadata
        if (section.contains("mob-type")) {
            if (material.equals("MONSTER_EGG") ||
                    material.equals("MONSTER_EGGS") ||
                    material.equals("LEGACY_MONSTER_EGG") ||
                    material.equals("LEGACY_MONSTER_EGGS"))
                this.metadata = new SpawnEggMetadata(section);
            else if (material.equals("MOB_SPAWNER") || material.equals("SPAWNER"))
                this.metadata = new SpawnerMetadata(section);
        } else if (section.contains("shulker-box-items")) {
            this.metadata = new ShulkerBoxMetadata(section);
        } else if (section.contains("potion")) {
            this.metadata = new PotionMetadata(section);
        } else if (section.contains("banner-patterns")) {
            this.metadata = new BannerMetadata(section);
        } else if (section.contains("shield-patterns")) {
            this.metadata = new ShieldMetadata(section);
        } else if (section.contains("armor-color")) {
            this.metadata = new LeatherArmorMetadata(section);
        } else if (section.contains("player-head-data")) {
            this.metadata = new PlayerHeadMetadata(section);
        } else if (section.contains("book-data")) {
            this.metadata = new BookMetadata(section);
        } else if (section.contains("map-data")) {
            this.metadata = new MapMetadata(section);
        } else if (section.contains("firework-effects")) {
            this.metadata = new FireworkMetadata(section);
        } else if (section.contains("firework-effect")) {
            this.metadata = new FireworkStarMetadata(section);
        } else if (section.contains("tropical-fish-bucket-data")) {
            this.metadata = new TropicalFishBucketMetadata(section);
        } else if (section.contains("book-enchantments")) {
            this.metadata = new EnchantedBookMetadata(section);
        } else if (section.contains("compass-data")) {
            if (MCVersion.isCurrentVersionAtLeast(MCVersion.v1_16_R1))
                this.metadata = new CompassMetadata(section);
            else
                throw new VersionNotSupportedException("Compass data is only supported in Minecraft version 1.16 and above!");
        } else if (section.contains("recipes")) {
            this.metadata = new KnowledgeBookMetadata();
        } else if (section.contains("charged-projectiles")) {
            if (MCVersion.isCurrentVersionAtLeast(MCVersion.v1_14_R1))
                this.metadata = new CrossbowMetadata();
            else
                throw new VersionNotSupportedException("Crossbows are only supported in Minecraft version 1.14 and above!");
        }
        else {
            this.metadata = new Metadata(section);
        }
    }

    /**
     * Create a new WrappedItem using a Bukkit ItemStack. This is functionally the same as calling the {@link ItemAPI#parseItem(ItemStack) parseItem} method in the ItemAPI class.
     * @param item An ItemStack to parse
     */
    public WrappedItem(ItemStack item) {
        this.material = item.getType().name();
        this.amount = item.getAmount();
        this.damage = 0;
        if (MCVersion.isCurrentVersionAtLeast(MCVersion.v1_13_R1))
            damage = DamageUtil.extractDamage(item);
        else
            damage = item.getDurability();

        this.nbtData = new NBTData(item);

        ItemMeta meta = item.getItemMeta();
        if (!MCVersion.isCurrentVersionAtLeast(MCVersion.v1_13_R1)) {
            if (meta instanceof SpawnEggMeta)
                this.metadata = new SpawnEggMetadata(item);
        }
        if (material.equals("MOB_SPAWNER") || material.equals("SPAWNER"))
            this.metadata = new SpawnerMetadata(item);
        else if (material.equals("SKULL_ITEM") || material.equals("PLAYER_HEAD"))
            this.metadata = new PlayerHeadMetadata(item);
        else if (material.equals("SHIELD"))
            this.metadata = new ShieldMetadata(item);
        else if (material.equals("TROPICAL_FISH_BUCKET"))
            this.metadata = new TropicalFishBucketMetadata(item);
        else if (material.equals("COMPASS") || material.equals("LEGACY_COMPASS"))
            if (MCVersion.isCurrentVersionAtLeast(MCVersion.v1_16_R1))
                this.metadata = new CompassMetadata(item);
            else
                this.metadata = new Metadata(item);
        else if (material.equals("CROSSBOW")) {
            this.metadata = new CrossbowMetadata();
        } else {
            if (meta instanceof BannerMeta)
                this.metadata = new BannerMetadata(item);
            else if (meta instanceof BookMeta)
                this.metadata = new BookMetadata(item);
            else if (meta instanceof FireworkMeta)
                this.metadata = new FireworkMetadata(item);
            else if (meta instanceof FireworkEffectMeta)
                this.metadata = new FireworkStarMetadata(item);
            else if (meta instanceof LeatherArmorMeta)
                this.metadata = new LeatherArmorMetadata(item);
            else if (meta instanceof MapMeta)
                this.metadata = new MapMetadata(item);
            else if (meta instanceof PotionMeta)
                this.metadata = new PotionMetadata(item);
            else if (meta instanceof EnchantmentStorageMeta)
                this.metadata = new EnchantedBookMetadata(item);
            else if (meta instanceof KnowledgeBookMeta) {
                this.metadata = new KnowledgeBookMetadata(item);
            }
            else
                this.metadata = new Metadata(item);
        }
    }

    /**
     * Get the configuration section to which this item may have belonged. <b>Note:</b> This method will return null if this WrappedItem was created from scratch or if it was parsed from a Bukkit ItemStack.
     * @return The WrappedConfigurationSection that this item originated from, or null if it did not originate from a configuration section
     */
    public WrappedConfigurationSection getConfigSection() {
        return section;
    }

    /**
     * Get the material of the Item.
     * @return A string representing the Bukkit material of the item
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Set the material of the Item.
     * @param material A string representing the Bukkit material
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * Get the amount of the item.
     * @return An int representing the amount of the item
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Set the amount of the item.
     * @param amount The amount that the Item should be. Must be greater than zero. Using a value larger than the max stack size will cause the Item to split into multiple ItemStacks when placed in an inventory (this is Bukkit behavior)
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Get the damage of the item.
     * @return The damage of the item
     */
    public short getDamage() {
        return damage;
    }

    /**
     * Set the damage value of an item. NOTE: In 1.13+ damage values no longer refer to the "data" value of an item (such as color of wool). They only refer to the durability of an item. Durability begins at zero (max durability) and counts upwards.
     * @param damage The damage value that the item should be. Must be greater than zero
     */
    public void setDamage(short damage) {
        this.damage = damage;
    }

    /**
     * Get any NBT Data associated with an Item.
     * @return An {@link NBTData NBTData} instance representing any associated NBT data
     */
    public NBTData getNbtData() {
        return nbtData;
    }

    /**
     * Set the NBT Data for an item. Note: to set individual tags, call {@link #getNbtData() getNbtData} and utilize methods within the {@link NBTData NBTData} class.
     * @param nbtData The NBTData object to set as the NBT data for this Item
     * @see NBTData
     */
    public void setNbtData(NBTData nbtData) {
        this.nbtData = nbtData;
    }

    /**
     * Get the Metadata for an item. Note: This item may be a subclass of Metadata depending on if/how it was parsed from a ConfigurationSection.
     * @return The Metadata associated with the item
     */
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Set the Metadata for an item. Any subclass of Metadata can be used so long as that Metadata is valid for the item. If you wish to set individual metadata values, call {@link #getMetadata() getMetadata} and utilize methods within the {@link Metadata Metadata} class (or an appropriate subclass).
     * @param metadata The metadata of the item to set, can also be a subclass of {@link Metadata} depending on the material
     * @see Metadata
     */
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    /**
     * This will convert this Rosetta Item to a Bukkit ItemStack for further usage.
     * @return An ItemStack with all parameters (Metadata, NBT data, etc.) applied
     * @throws IncorrectMetaException If the Metadata cannot be properly applied to the item. For example, if Potion metadata is applied to a banner or vice versa
     */
    public ItemStack getAsItemStack() {
        Material material = Material.matchMaterial(this.material);
        if (material != null) {
            ItemStack item = new ItemStack(material, amount);
            if (MCVersion.isCurrentVersionAtLeast(MCVersion.v1_13_R1)) {
                item = DamageUtil.applyDamage(item, damage);
            } else {
                item.setDurability(damage);
            }

            try {
                item = metadata.applyMetadata(item);
            } catch (ClassCastException e) {
                throw new IncorrectMetaException("Tried to apply " + metadata.getClass().getName() + " to " + material + ", but this meta is not allowed for this material.", e.getCause());
            }

            item = nbtData.applyNbtTags(item);

            return item;
        } else
            throw new NullPointerException("Unable to find a Material with the name " + this.material + ". Did you make sure the material name is correct and that this material exists in this version?");
    }

    /**
     * Serialize all data within this WrappedItem to a configuration file for later use. WARNING: This will delete any existing data in the configuration section being written to!
     * @param file The config file that will be written to
     * @param section The configuration section to which all data associated with this item will be saved within the config
     * @throws IOException If writing to the configuration failed
     */
    public void saveToConfig(File file, WrappedConfigurationSection section) throws IOException {
        writeToSection(section);
        section.save(file);
    }

    /**
     * Write all data within this WrappedItem to a configuration section. <b>Warning:</b> This method does not save the configuration section!
     * @param section The configuration section to which all data associated with this item will be serialized
     */
    public void writeToSection(WrappedConfigurationSection section) {
        section.clearConfigurationSection();

        section.set("material", this.material);
        section.set("amount", this.amount);
        if (this.damage > 0)
            section.set("damage", this.damage);

        this.metadata.saveToConfig(section);

        this.nbtData.saveNbtTags(section);
    }

    /**
     * Serialize all data within this WrappedItem to a Base64 string for more compact and concise storage (such as storage within databases).
     * @return A String contianing a Base64 representation of this WrappedItem
     * @throws IOException If writing to an ObjectOutputStream fails for any reason
     */
    public String toBase64String() throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(baos)) {
            out.writeObject(this);
            out.flush();
            byte[] bytes = baos.toByteArray();

            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(bytes);
        }
    }
}
