package dev.magicmq.itemapi.config;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * A wrapper class abstracting methods from both Bukkit's YAML parsing library as well as Configurate. Use {@link BukkitConfigSection BukkitConfigSection} for Bukkit, and {@link ConfigurateConfigSection ConfigurateConfigSection} for Configurate.
 * @see BukkitConfigSection
 * @see ConfigurateConfigSection
 */
public interface WrappedConfigurationSection {

    WrappedConfigurationSection createConfigurationSection(String key);

    WrappedConfigurationSection getConfigurationSection(String key);

    void clearConfigurationSection();

    Set<String> getKeys(boolean deep);

    boolean contains(String key);

    Object get(String key);

    String getString(String key);

    String getString(String key, String def);

    int getInt(String key);

    int getInt(String key, int def);

    double getDouble(String key);

    double getDouble(String key, double def);

    boolean getBoolean(String key);

    boolean getBoolean(String key, boolean def);

    List<String> getStringList(String key);

    void set(String key, Object value);

    void save(File file) throws IOException;
}
