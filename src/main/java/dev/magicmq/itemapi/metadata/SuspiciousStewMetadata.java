package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import dev.magicmq.itemapi.utils.Potion;
import dev.magicmq.itemapi.utils.PotionEffect;
import dev.magicmq.itemapi.utils.StringUtils;
import dev.magicmq.itemapi.utils.exception.PotionNotFoundException;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class that contians all Metadata associated with Suspicious Stews
 * <b>Warning:</b> This class should only be used with Minecraft version 1.14 and above.
 * @since MC 1.14
 */
public class SuspiciousStewMetadata extends Metadata {

    private static final long serialVersionUID = -2717384473804129341L;

    private List<PotionEffect> potionEffects;

    /**
     * Create a new SuspiciousStewMetadata class from scratch with default values.
     */
    public SuspiciousStewMetadata() {
        super();

        this.potionEffects = new ArrayList<>();
    }

    /**
     * Create a new SuspiciousStewMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item
     * @param section The section from which the data will be parsed
     */
    public SuspiciousStewMetadata(WrappedConfigurationSection section) {
        super(section);

        this.potionEffects = new ArrayList<>();
        WrappedConfigurationSection stewEffectsSection = section.getConfigurationSection("stew-potion-effects");
        for (String key : stewEffectsSection.getKeys(false)) {
            WrappedConfigurationSection subSection = stewEffectsSection.getConfigurationSection(key);
            potionEffects.add(new PotionEffect(
                    subSection.getString("potion"),
                    subSection.getString("duration"),
                    subSection.getInt("amplifier", 1),
                    subSection.getBoolean("ambient", true),
                    subSection.getBoolean("particles", true),
                    subSection.getBoolean("icon", true)
            ));
        }
    }

    /**
     * Create a new SuspiciousStewMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which suspicious stew metadata will be extracted
     */
    public SuspiciousStewMetadata(ItemStack item) {
        super(item);

        this.potionEffects = new ArrayList<>();
        SuspiciousStewMeta meta = (SuspiciousStewMeta) item.getItemMeta();
        if (meta != null) {
            for (org.bukkit.potion.PotionEffect effect : meta.getCustomEffects()) {
                potionEffects.add(new PotionEffect(
                        effect.getType().getName(),
                        StringUtils.formatTicks(effect.getDuration()),
                        effect.getAmplifier(),
                        effect.isAmbient(),
                        effect.hasParticles(),
                        effect.hasIcon()
                ));
            }
        } else {
            this.potionEffects = new ArrayList<>();
        }
    }

    /**
     * Get a list of potion effects associated with this metadata
     * @return A mutable list containing all potion effects
     */
    public List<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    /**
     * Add a new potion effect to this metadata.
     * @param potionEffect The potion effect to add
     */
    public void addPotionEffect(PotionEffect potionEffect) {
        potionEffects.add(potionEffect);
    }

    /**
     * Set all potion effects associated with this sucpicious stew
     * @param potionEffects A list of potion effects to set
     */
    public void setPotionEffects(List<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }

    /**
     * Apply the suspicious stew metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        SuspiciousStewMeta meta = (SuspiciousStewMeta) item.getItemMeta();
        if (potionEffects != null) {
            for (PotionEffect effect : potionEffects) {
                Potion potionName = Potion.getByName(effect.getPotion());
                if (potionName == null)
                    throw new PotionNotFoundException("Potion type " + effect.getPotion() + " not found! Please check that the name is correct.");

                org.bukkit.potion.PotionEffect bukkitPotionEffect = new org.bukkit.potion.PotionEffect(
                        PotionType.valueOf(potionName.getBukkitPotionType()).getEffectType(),
                        StringUtils.parseTime(effect.getDuration()),
                        effect.getAmplifier(),
                        effect.isAmbient(),
                        effect.hasParticles(),
                        effect.hasIcon()
                );
                meta.addCustomEffect(bukkitPotionEffect, false);
            }
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the suspicious stew metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        if (this.potionEffects != null && this.potionEffects.size() > 0) {
            WrappedConfigurationSection stewSection = section.createConfigurationSection("stew-potion-effects");
            int i = 0;
            for (PotionEffect effect : potionEffects) {
                WrappedConfigurationSection stewEffect = stewSection.createConfigurationSection("" + i);
                stewEffect.set("potion", effect.getPotion());
                stewEffect.set("duration", effect.getDuration());
                stewEffect.set("amplifier", effect.getAmplifier());
                stewEffect.set("ambient", effect.isAmbient());
                stewEffect.set("particles", effect.hasParticles());
                stewEffect.set("icon", effect.hasIcon());
                i++;
            }
        }
    }
}
