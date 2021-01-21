package dev.magicmq.itemapi.metadata;

import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import dev.magicmq.itemapi.utils.Colors;
import dev.magicmq.itemapi.utils.FireworkEffect;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wrapper class that contians all Metadata associated with Firework Rockets.
 */
public class FireworkMetadata extends Metadata implements Serializable {

    private static final long serialVersionUID = -6914720406110884881L;

    private int power;
    private List<FireworkEffect> effects;

    /**
     * Create a new FireworkMetadata class from scratch with default values.
     */
    public FireworkMetadata() {
        super();

        this.power = -1;
        this.effects = new ArrayList<>();
    }

    /**
     * Create a new FireworkMetadata class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item, not the "firework-effects" section.
     * @param section The section from which the data will be parsed
     */
    public FireworkMetadata(WrappedConfigurationSection section) {
        super(section);

        power = section.getInt("power", -1);

        effects = new ArrayList<>();
        WrappedConfigurationSection fireworkSection = section.getConfigurationSection("firework-effects");
        for (String key : fireworkSection.getKeys(false)) {
            WrappedConfigurationSection subSection = fireworkSection.getConfigurationSection(key);
            effects.add(new FireworkEffect(
                    subSection.getString("type"),
                    subSection.getStringList("colors"),
                    subSection.getStringList("fade-colors"),
                    subSection.getBoolean("flicker", false),
                    subSection.getBoolean("trail", false)
            ));
        }
    }

    /**
     * Create a new FireworkMetadata class with values parsed from an existing ItemStack.
     * @param item The item from which firework metadata will be extracted
     */
    public FireworkMetadata(ItemStack item) {
        super(item);

        FireworkMeta meta = (FireworkMeta) item.getItemMeta();
        if (meta != null) {
            this.power = meta.getPower();
            for (org.bukkit.FireworkEffect effect : meta.getEffects()) {
                effects.add(new FireworkEffect(
                        effect.getType().toString(),
                        effect.getColors().stream().map(color -> Colors.getFromRGB(color.asRGB()).name()).collect(Collectors.toList()),
                        effect.getFadeColors().stream().map(color -> Colors.getFromRGB(color.asRGB()).name()).collect(Collectors.toList()),
                        effect.hasFlicker(),
                        effect.hasTrail()
                ));
            }
        } else {
            this.power = -1;
            this.effects = new ArrayList<>();
        }
    }

    /**
     * Get the power of the firework.
     * @return The firework's power as an integer greater than zero
     */
    public int getPower() {
        return power;
    }

    /**
     * Set the power of the firework.
     * @param power The firework's power as an integer greater than zero
     */
    public void setPower(int power) {
        this.power = power;
    }

    /**
     * Get a mutable {@link List list} containing all {@link FireworkEffect FireworkEffects} associated with this FireworkMetadata.
     * @return A mutable list containing all FireworkEffects. All changes made to the list will persist
     * @see FireworkEffect
     */
    public List<FireworkEffect> getEffects() {
        return effects;
    }

    /**
     * Add a new {@link FireworkEffect FireworkEffect} to this metadata.
     * @param effect The effect to add
     * @see FireworkEffect
     */
    public void addEffect(FireworkEffect effect) {
        effects.add(effect);
    }

    /**
     * Apply the firework metadata as well as basic metadata in {@link Metadata Metadata} to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the metadata will be applied
     * @return The item, with metadata applied to it
     */
    @Override
    public ItemStack applyMetadata(ItemStack item) {
        item = super.applyMetadata(item);

        FireworkMeta meta = (FireworkMeta) item.getItemMeta();
        if (power > -1)
            meta.setPower(power);
        if (effects != null) {
            for (FireworkEffect effect : effects) {
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
                meta.addEffect(toApply.build());
            }
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Serialize all the firework metadata contained within this class as well as basic metadata in {@link Metadata Metadata} to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection) saveToConfig} in the WrappedItem class!
     * @param section The configuration to which the data will be serialized
     */
    @Override
    public void saveToConfig(WrappedConfigurationSection section) {
        super.saveToConfig(section);

        if (this.power > -1)
            section.set("power", this.power);
        if (this.effects != null && this.effects.size() > 0) {
            WrappedConfigurationSection fireworkSection = section.createConfigurationSection("firework-effects");
            int i = 0;
            for (FireworkEffect effect : this.effects) {
                WrappedConfigurationSection effectSection = fireworkSection.createConfigurationSection("" + i);
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
    }
}
