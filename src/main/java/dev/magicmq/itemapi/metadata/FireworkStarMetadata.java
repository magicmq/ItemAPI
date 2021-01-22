package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import dev.magicmq.itemapi.utils.Colors;
import dev.magicmq.itemapi.utils.FireworkEffect;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper class that contians all Metadata associated with Firework Stars.
 */
public class FireworkStarMetadata extends Metadata implements Serializable {

    private static final long serialVersionUID = 2581034352965703552L;

    private FireworkEffect effect;

    /**
     * Create a new FireworkStarMetadata class from scratch with default values.
     */
    public FireworkStarMetadata() {
        super();

        this.effect = new FireworkEffect(null, null, null, false, false);
    }

    /**
     * Create a new FireworkStarMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item, not the "firework-effect" section.
     * @param section The section from which the data will be parsed
     */
    public FireworkStarMetadata(WrappedConfigurationSection section) {
        super(section);

        WrappedConfigurationSection fireworkSection = section.getConfigurationSection("firework-effect");
        this.effect = new FireworkEffect(
                fireworkSection.getString("type"),
                fireworkSection.getStringList("colors"),
                fireworkSection.getStringList("fade-colors"),
                fireworkSection.getBoolean("flicker", false),
                fireworkSection.getBoolean("trail", false)
        );
    }

    /**
     * Create a new FireworkStarMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which firework star metadata will be extracted
     */
    public FireworkStarMetadata(ItemStack item) {
        super(item);

        FireworkEffectMeta meta = (FireworkEffectMeta) item.getItemMeta();
        if (meta.hasEffect()) {
            this.effect = new FireworkEffect(
                    meta.getEffect().getType().name(),
                    meta.getEffect().getColors().stream().map(color -> Colors.getFromRGB(color.asRGB()).name()).collect(Collectors.toList()),
                    meta.getEffect().getFadeColors().stream().map(color -> Colors.getFromRGB(color.asRGB()).name()).collect(Collectors.toList()),
                    meta.getEffect().hasFlicker(),
                    meta.getEffect().hasTrail()
            );
        } else
            this.effect = new FireworkEffect(null, null, null, false, false);
    }

    /**
     * Get the {@link FireworkEffect FireworkEffect} associated with this firework star.
     * @return The associated FireworkEffect
     * @see FireworkEffect
     */
    public FireworkEffect getEffect() {
        return effect;
    }

    /**
     * Set the {@link FireworkEffect FireworkEffect} asociated with this firework star.
     * @param effect The FireworkEffect to set
     * @see FireworkEffect
     */
    public void setEffect(FireworkEffect effect) {
        this.effect = effect;
    }

    /**
     * Apply the firework star metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        FireworkEffectMeta meta = (FireworkEffectMeta) item.getItemMeta();
        org.bukkit.FireworkEffect.Builder toApply = org.bukkit.FireworkEffect.builder();
        if (effect.getType() != null) {
            toApply.with(org.bukkit.FireworkEffect.Type.valueOf(effect.getType()));
        }
        if (effect.getColors() != null) {
            List<Color> colors = effect.getColors().stream().map(color -> Colors.valueOf(color).getBukkitColor()).collect(Collectors.toList());
            toApply.withColor(colors);
        }
        if (effect.getFadeColors() != null) {
            List<Color> fadeColors = effect.getFadeColors().stream().map(color -> Colors.valueOf(color).getBukkitColor()).collect(Collectors.toList());
            toApply.withFade(fadeColors);
        }
        toApply.flicker(effect.isFlicker());
        toApply.trail(effect.isTrail());
        meta.setEffect(toApply.build());

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the firework star metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        WrappedConfigurationSection effectSection = section.createConfigurationSection("firework-effect");
        if (effect.getType() != null)
            effectSection.set("type", effect.getType());
        if (effect.getColors() != null && effect.getColors().size() > 0)
            effectSection.set("colors", effect.getColors());
        if (effect.getFadeColors() != null && effect.getFadeColors().size() > 0)
            effectSection.set("fade-colors", effect.getFadeColors());
        if (effect.isFlicker())
            effectSection.set("flicker", true);
        if (effect.isTrail())
            effectSection.set("trail", true);
    }
}
