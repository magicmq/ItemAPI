package dev.magicmq.itemapi.config;

import dev.magicmq.itemapi.ItemAPI;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;
import org.spongepowered.configurate.serialize.SerializationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A wrapper class designed to interface with Configurate.
 */
public class ConfigurateConfigSection implements WrappedConfigurationSection {

    private final ConfigurationLoader<?> loader;
    private final ConfigurationNode section;

    /**
     * Create a new instance of the wrapper to be passed to methods in {@link ItemAPI ItemAPI}.
     * @param loader The configuration loader that represents the entire configuration being read/written.
     * @param section The name of the specific section within the entire config that ItemAPI will read to/write from.
     */
    public ConfigurateConfigSection(ConfigurationLoader<?> loader, ConfigurationNode section) {
        this.loader = loader;
        this.section = section;
    }

    @Override
    public Set<String> getKeys(boolean deep) {
        Set<String> toReturn = new HashSet<>();
        section.childrenMap().keySet().forEach(key -> toReturn.add((String) key));
        return toReturn;
    }

    @Override
    public boolean contains(String key) {
        return section.hasChild(key);
    }

    @Override
    public WrappedConfigurationSection createConfigurationSection(String key) {
        return getConfigurationSection(key);
    }

    @Override
    public WrappedConfigurationSection getConfigurationSection(String key) {
        return new ConfigurateConfigSection(loader, fetchSubNode(key));
    }

    @Override
    public void clearConfigurationSection() {
        try {
            for (String key : getKeys(false))
                fetchSubNode(key).set(null);
        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object get(String key) {
        ConfigurationNode keyNode = fetchSubNode(key);
        try {
            return keyNode.get(Object.class);
        } catch (SerializationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getString(String key) {
        ConfigurationNode keyNode = fetchSubNode(key);
        return keyNode.getString();
    }

    @Override
    public String getString(String key, String def) {
        if (contains(key))
            return getString(key);
        else
            return def;
    }

    @Override
    public int getInt(String key) {
        ConfigurationNode keyNode = fetchSubNode(key);
        return keyNode.getInt();
    }

    @Override
    public int getInt(String key, int def) {
        if (contains(key))
            return getInt(key);
        else
            return def;
    }

    @Override
    public double getDouble(String key) {
        ConfigurationNode keyNode = fetchSubNode(key);
        return keyNode.getDouble();
    }

    @Override
    public double getDouble(String key, double def) {
        if (contains(key))
            return getDouble(key);
        else
            return def;
    }

    @Override
    public boolean getBoolean(String key) {
        ConfigurationNode keyNode = fetchSubNode(key);
        return keyNode.getBoolean();
    }

    @Override
    public boolean getBoolean(String key, boolean def) {
        if (contains(key))
            return getBoolean(key);
        else
            return def;
    }

    @Override
    public List<String> getStringList(String key) {
        ConfigurationNode keyNode = fetchSubNode(key);
        try {
            return keyNode.getList(String.class);
        } catch (SerializationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void set(String key, Object value) {
        ConfigurationNode keyNode = fetchSubNode(key);
        try {
            //Type conversion due to byte not being a supported type for Configurate
            if (value instanceof Byte)
                keyNode.set(((Byte) value).intValue());
            else if (value instanceof List<?>) {
                Object val = ((List) value).get(0);
                if (val != null) {
                    if (val instanceof Byte) {
                        List<Integer> converted = new ArrayList<>();
                        ((List<Byte>) value).forEach(bytee -> converted.add(bytee.intValue()));
                        keyNode.set(converted);
                        return;
                    }
                }
            }
            keyNode.set(value);
        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(File file) throws IOException {
        if (section.parent() != null)
            new ConfigurateConfigSection(loader, section.parent()).save(file);
        else
            loader.save(section);
    }

    private ConfigurationNode fetchSubNode(String key) {
        Object[] array = new Object[1];
        if(key.contains(".")) {
            String[] split = key.split("\\.");
            array = new Object[split.length];
            System.arraycopy(split, 0, array, 0, split.length);
        } else {
            array[0] = key;
        }
        return section.node(array);
    }
}
