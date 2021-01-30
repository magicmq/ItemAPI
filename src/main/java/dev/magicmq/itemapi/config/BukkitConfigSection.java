package dev.magicmq.itemapi.config;

import com.google.common.base.Preconditions;
import dev.magicmq.itemapi.ItemAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * A wrapper class designed to interface with Bukkit YAML.
 */
public class BukkitConfigSection implements WrappedConfigurationSection {

    private final FileConfiguration config;
    private final ConfigurationSection section;

    /**
     * Create a new instance of the wrapper to be passed to methods in {@link ItemAPI ItemAPI}.
     * @param config The entire configuration that will be dealt with.
     * @param section The name of the specific section within the entire config that ItemAPI will read to/write from.
     */
    public BukkitConfigSection(FileConfiguration config, ConfigurationSection section) {
        Preconditions.checkArgument(config != null, "config cannot be null!");
        Preconditions.checkArgument(section != null, "section cannot be null!");

        this.config = config;
        this.section = section;
    }

    @Override
    public WrappedConfigurationSection createConfigurationSection(String key) {
        if (contains(key))
            return getConfigurationSection(key);
        return new BukkitConfigSection(config, section.createSection(key));
    }

    @Override
    public WrappedConfigurationSection getConfigurationSection(String key) {
        return new BukkitConfigSection(config, section.getConfigurationSection(key));
    }

    @Override
    public void clearConfigurationSection() {
        for (String key : section.getKeys(true))
            section.set(key, null);
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        return section.getKeys(false);
    }

    @Override
    public boolean contains(String key) {
        return section.contains(key);
    }

    @Override
    public Object get(String key) {
        return section.get(key);
    }

    @Override
    public String getString(String key) {
        return section.getString(key);
    }

    @Override
    public String getString(String key, String def) {
        return section.getString(key, def);
    }

    @Override
    public int getInt(String key) {
        return section.getInt(key);
    }

    @Override
    public int getInt(String key, int def) {
        return section.getInt(key, def);
    }

    @Override
    public double getDouble(String key) {
        return section.getDouble(key);
    }

    @Override
    public double getDouble(String key, double def) {
        return section.getDouble(key, def);
    }

    @Override
    public boolean getBoolean(String key) {
        return section.getBoolean(key);
    }

    @Override
    public boolean getBoolean(String key, boolean def) {
        return section.getBoolean(key, def);
    }

    @Override
    public List<String> getStringList(String key) {
        return section.getStringList(key);
    }

    @Override
    public void set(String key, Object value) {
        section.set(key, value);
    }

    @Override
    public void save(File file) throws IOException {
        config.save(file);
    }
}
