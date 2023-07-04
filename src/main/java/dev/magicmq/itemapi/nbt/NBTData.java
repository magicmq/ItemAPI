package dev.magicmq.itemapi.nbt;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import dev.magicmq.itemapi.WrappedItem;
import dev.magicmq.itemapi.config.WrappedConfigurationSection;
import dev.magicmq.itemapi.utils.exception.NBTException;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * A wrapper class representing all NBT Tags and data associated with an item.
 */
public class NBTData implements Serializable {

    private static final long serialVersionUID = 3226903116011105573L;

    private final List<NBTTag> nbtTags;

    /**
     * Create a new NBTData class from scratch with default values.
     */
    public NBTData() {
        nbtTags = new ArrayList<>();
    }

    /**
     * Create a new NBTData class with values parsed from a configuration section.
     * <b>Note:</b> This receives the configuration section of the item, not the "nbt" section.
     * @param section The section from which the data will be parsed
     */
    public NBTData(WrappedConfigurationSection section) {
        nbtTags = new ArrayList<>();

        parseConfig(section, null);
    }

    /**
     * Create a new NBTData class with NBT tags parsed from an existing ItemStack.
     * @param item The item from which NBT tags will be extracted
     */
    public NBTData(ItemStack item) {
        nbtTags = new ArrayList<>();

        NBTItem nbtItem = new NBTItem(item);
        parseItemStack(nbtItem, null);
        nbtItem.clearCustomNBT();
        for (String key : nbtItem.getKeys()) {
            removeNBTTag(key);
        }
    }

    private void parseConfig(WrappedConfigurationSection section, NBTTag compoundTag) {
        if (compoundTag == null) {
            for (String key : section.getKeys(false)) {
                NBTTag tag;
                WrappedConfigurationSection tagSection = section.getConfigurationSection(key);
                if (tagSection.getString("type") == null)
                    throw new NBTException("NBTTag does not have a type! Please make sure one is defined in the config");
                NBTTagType type = NBTTagType.valueOf(tagSection.getString("type"));
                if (type == NBTTagType.COMPOUND) {
                    tag = new NBTTag(type, tagSection.getString("key"), new ArrayList<NBTTag>());
                    parseConfig(tagSection.getConfigurationSection("children"), tag);
                } else {
                    if (tagSection.getString("key") == null)
                        throw new NBTException("NBTTag key cannot be null! Please ensure you have defined a key for all tags in the config.");
                    else if (tagSection.get("value") == null)
                        throw new NBTException("NBTTag value cannot be null! Please ensure you have defined a value for the " + tagSection.getString("key") + " tag.");

                    if (type == NBTTagType.LIST)
                        tag = new NBTTag(type, NBTTagType.valueOf(tagSection.getString("list-type")), tagSection.getString("key"), tagSection.getStringList("value"));
                    else
                        tag = new NBTTag(type, tagSection.getString("key"), tagSection.get("value"));
                }
                nbtTags.add(tag);
            }
        } else {
            for (String key : section.getKeys(false)) {
                WrappedConfigurationSection tagSection = section.getConfigurationSection(key);
                if (tagSection.getString("type") == null)
                    throw new NBTException("NBTTag does not have a type! Please make sure one is defined in the config");
                NBTTagType type = NBTTagType.valueOf(tagSection.getString("type"));
                if (type == NBTTagType.COMPOUND) {
                    NBTTag innerTag = new NBTTag(type, tagSection.getString("key"), new ArrayList<>());
                    ((ArrayList<NBTTag>) compoundTag.getValue()).add(innerTag);
                    parseConfig(tagSection.getConfigurationSection("children"), innerTag);
                } else {
                    if (tagSection.getString("key") == null)
                        throw new NBTException("NBTTag key cannot be null! Please ensure you have defined a key for all tags in the config.");
                    else if (tagSection.get("value") == null)
                        throw new NBTException("NBTTag value cannot be null! Please ensure you have defined a value for the " + tagSection.getString("key") + " tag.");

                    if (type == NBTTagType.LIST)
                        ((ArrayList<NBTTag>) compoundTag.getValue()).add(new NBTTag(type, NBTTagType.valueOf(tagSection.getString("list-type")), tagSection.getString("key"), tagSection.getStringList("value")));
                    else
                        ((ArrayList<NBTTag>) compoundTag.getValue()).add(new NBTTag(type, tagSection.getString("key"), tagSection.get("value")));
                }
            }
        }
    }

    private void parseItemStack(NBTCompound itemCompoundTag, NBTTag compoundTag) {
        if (compoundTag == null) {
            for (String key : itemCompoundTag.getKeys()) {
                NBTTag tag;
                NBTTagType type = NBTTagType.getFromNBTAPIType(itemCompoundTag.getType(key));
                if (type == NBTTagType.COMPOUND) {
                    tag = new NBTTag(type, key, new ArrayList<NBTTag>());
                    parseItemStack(itemCompoundTag.getCompound(key), tag);
                } else {
                    if (type == NBTTagType.LIST) {
                        NBTTagType listType = NBTTagType.getFromNBTAPIType(itemCompoundTag.getListType(key));
                        tag = extractNBT(itemCompoundTag, key, type, listType);
                    } else
                        tag = extractNBT(itemCompoundTag, key, type);
                }
                nbtTags.add(tag);
            }
        } else {
            for (String key : itemCompoundTag.getKeys()) {
                NBTTagType type = NBTTagType.getFromNBTAPIType(itemCompoundTag.getType(key));
                if (type == NBTTagType.COMPOUND) {
                    NBTTag innerTag = new NBTTag(type, key, new ArrayList<>());
                    ((ArrayList<NBTTag>) compoundTag.getValue()).add(innerTag);
                    parseItemStack(itemCompoundTag.getCompound(key), innerTag);
                } else {
                    if (type == NBTTagType.LIST) {
                        NBTTagType listType = NBTTagType.getFromNBTAPIType(itemCompoundTag.getListType(key));
                        ((ArrayList<NBTTag>) compoundTag.getValue()).add(extractNBT(itemCompoundTag, key, type, listType));
                    } else
                        ((ArrayList<NBTTag>) compoundTag.getValue()).add(extractNBT(itemCompoundTag, key, type));
                }
            }
        }
    }

    /**
     * Get all NBT tags associated with this item.
     * @return A mutable list containing all NBT tags associated with the item
     */
    public List<NBTTag> getNbtTags() {
        return nbtTags;
    }

    /**
     * Get an NBT Tag by its key.
     * @param key The key of the NBT tag to get
     * @return The NBT tag under the specified key, or null if nothing was found
     */
    public NBTTag getNbtTag(String key) {
        for (NBTTag tag : nbtTags) {
            if (tag.getKey().equals(key))
                return tag;
        }
        return null;
    }

    /**
     * Add a new NBT tag to the item.
     * @param type The {@link NBTTagType} representing the type that the tag is
     * @param key The key of the tag
     * @param value The value of the tag, should be another NBTTag if the tag is a compound tag
     */
    public void addNbtTag(NBTTagType type, String key, Object value) {
        addNbtTag(type, null, key, value);
    }

    /**
     * Add a new NBT tag to the item. This method should be used for all list tags.
     * @param type The {@link NBTTagType} representing the type that the tag is
     * @param listType If the <code>type</code> is <code>LIST</code>, this should be the type representing values in the list; this should be null otherwise
     * @param key The key of the tag
     * @param value The value of the tag, should be another NBTTag if the tag is a compound tag
     */
    public void addNbtTag(NBTTagType type, NBTTagType listType, String key, Object value) {
        nbtTags.add(new NBTTag(type, listType, key, value));
    }

    /**
     * Remove an NBT tag from the item.
     * @param key The key of the tag that should be removed
     */
    public void removeNBTTag(String key) {
        nbtTags.removeIf(next -> next.getKey().equals(key));
    }

    /**
     * Apply all NBT data in this class to an ItemStack.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#getAsItemStack() getAsItemStack} in the WrappedItem class!
     * @param item The item to which the NBT data will be applied
     * @return The item, with NBT data applied to it
     */
    public ItemStack applyNbtTags(ItemStack item) {
        return applyNbtTags(item, null, null);
    }

    /**
     * Serialize all the NBT data contained within this class to a configuration section.
     * <b>Warning:</b> This method is called automatically from {@link WrappedItem#saveToConfig(File, WrappedConfigurationSection)} saveToConfig} in the WrappedItem class!
     * @param section The configuration section to which the data will be serialized
     */
    public void saveNbtTags(WrappedConfigurationSection section) {
        if (nbtTags == null || nbtTags.size() == 0)
            return;
        WrappedConfigurationSection nbtSection = section.createConfigurationSection("nbt");
        saveNbtTags(nbtSection, null);
    }

    private void saveNbtTags(WrappedConfigurationSection section, NBTTag innerTag) {
        if (innerTag == null) {
            int i = 0;
            for (NBTTag tag : nbtTags) {
                WrappedConfigurationSection tagSection = section.createConfigurationSection("" + i);
                tagSection.set("type", tag.getType().name());
                tagSection.set("key", tag.getKey());
                if (tag.isCompoundTag()) {
                    WrappedConfigurationSection innerSection = tagSection.createConfigurationSection("children");
                    saveNbtTags(innerSection, tag);
                } else {
                    if (tag.isList())
                        tagSection.set("list-type", tag.getListType().name());
                    tagSection.set("value", tag.getValue());
                }
                i++;
            }
        } else {
            int i = 0;
            for (NBTTag tag : ((ArrayList<NBTTag>) innerTag.getValue())) {
                WrappedConfigurationSection tagSection = section.createConfigurationSection("" + i);
                tagSection.set("type", tag.getType().name());
                tagSection.set("key", tag.getKey());
                if (tag.isCompoundTag()) {
                    WrappedConfigurationSection innerSection = tagSection.createConfigurationSection("children");
                    saveNbtTags(innerSection, tag);
                } else {
                    if (tag.isList())
                        tagSection.set("list-type", tag.getListType().name());
                    tagSection.set("value", tag.getValue());
                }
                i++;
            }
        }
    }

    private ItemStack applyNbtTags(ItemStack item, NBTTag compoundTag, NBTCompound compound) {
        if (compoundTag == null) {
            NBTItem nbtItem = new NBTItem(item);
            for (NBTTag tag : nbtTags) {
                if (tag.isCompoundTag()) {
                    NBTCompound inner = nbtItem.addCompound(tag.getKey());
                    applyNbtTags(item, tag, inner);
                } else {
                    applyTag(nbtItem, tag);
                }
            }
            return nbtItem.getItem();
        } else {
            for (NBTTag tag : (ArrayList<NBTTag>) compoundTag.getValue()) {
                if (tag.isCompoundTag()) {
                    NBTCompound inner = compound.addCompound(tag.getKey());
                    applyNbtTags(item, tag, inner);
                } else {
                    applyTag(compound, tag);
                }
            }
            return null;
        }
    }

    private NBTTag extractNBT(NBTCompound nbtItem, String key, NBTTagType type) {
        return extractNBT(nbtItem, key, type, null);
    }

    private void applyTag(NBTCompound nbtItem, NBTTag tag) {
        switch (tag.getType()) {
            case BOOLEAN:
                nbtItem.setBoolean(tag.getKey(), (Boolean) tag.getValue());
                break;
            case BYTE:
                nbtItem.setByte(tag.getKey(), ((Number) tag.getValue()).byteValue());
                break;
            case SHORT:
                nbtItem.setShort(tag.getKey(), ((Number) tag.getValue()).shortValue());
                break;
            case INT:
                nbtItem.setInteger(tag.getKey(), (Integer) tag.getValue());
                break;
            case LONG:
                nbtItem.setLong(tag.getKey(), (Long) tag.getValue());
                break;
            case FLOAT:
                nbtItem.setFloat(tag.getKey(), ((Number) tag.getValue()).floatValue());
                break;
            case DOUBLE:
                nbtItem.setDouble(tag.getKey(), (Double) tag.getValue());
                break;
            case BYTE_ARRAY:
                List<? extends Number> list = (List<? extends Number>) tag.getValue();
                byte[] byteArray = new byte[list.size()];
                for (int i = 0; i < byteArray.length; i++) {
                    byteArray[i] = list.get(i).byteValue();
                }
                nbtItem.setByteArray(tag.getKey(), byteArray);
                break;
            case STRING:
                nbtItem.setString(tag.getKey(), (String) tag.getValue());
                break;
            case INT_ARRAY:
                List<Integer> list2 = (List<Integer>) tag.getValue();
                int[] intArray = new int[list2.size()];
                for (int i = 0; i < intArray.length; i++) {
                    intArray[i] = list2.get(i);
                }
                nbtItem.setIntArray(tag.getKey(), intArray);
                break;
            case LIST:
                ArrayList<String> list3 = (ArrayList<String>) tag.getValue();
                NBTTagType listType = tag.getListType();
                if (listType != null) {
                    if (listType == NBTTagType.INT) {
                        nbtItem.getIntegerList(tag.getKey()).addAll(list3.stream().map(Integer::parseInt).collect(Collectors.toList()));
                    } else if (listType == NBTTagType.FLOAT) {
                        nbtItem.getFloatList(tag.getKey()).addAll(list3.stream().map(Float::parseFloat).collect(Collectors.toList()));
                    } else if (listType == NBTTagType.DOUBLE) {
                        nbtItem.getDoubleList(tag.getKey()).addAll(list3.stream().map(Double::parseDouble).collect(Collectors.toList()));
                    } else if (listType == NBTTagType.LONG) {
                        nbtItem.getLongList(tag.getKey()).addAll(list3.stream().map(Long::parseLong).collect(Collectors.toList()));
                    } else {
                        nbtItem.getStringList(tag.getKey()).addAll(list3);
                    }
                }
                break;
        }
    }

    private NBTTag extractNBT(NBTCompound nbtItem, String key, NBTTagType type, NBTTagType listType) {
        switch (type) {
            case BYTE:
                return new NBTTag(type, key, nbtItem.getByte(key));
            case SHORT:
                return new NBTTag(type, key, nbtItem.getShort(key));
            case INT:
                return new NBTTag(type, key, nbtItem.getInteger(key));
            case LONG:
                return new NBTTag(type, key, nbtItem.getLong(key));
            case FLOAT:
                return new NBTTag(type, key, nbtItem.getFloat(key));
            case DOUBLE:
                return new NBTTag(type, key, nbtItem.getDouble(key));
            case BYTE_ARRAY:
                byte[] array = nbtItem.getByteArray(key);
                return new NBTTag(type, key, Arrays.asList(manualBox(new Byte[array.length], i -> Byte.valueOf(array[i]))));
            case STRING:
                return new NBTTag(type, key, nbtItem.getString(key));
            case INT_ARRAY:
                int[] array2 = nbtItem.getIntArray(key);
                return new NBTTag(type, key, Arrays.asList(manualBox(new Integer[array2.length], i -> Integer.valueOf(array2[i]))));
            case LIST:
                if (listType != null) {
                    if (listType == NBTTagType.INT) {
                        return new NBTTag(type, listType, key, nbtItem.getIntegerList(key));
                    } else if (listType == NBTTagType.FLOAT) {
                        return new NBTTag(type, listType, key, nbtItem.getFloatList(key));
                    } else if (listType == NBTTagType.DOUBLE) {
                        return new NBTTag(type, listType, key, nbtItem.getDoubleList(key));
                    } else if (listType == NBTTagType.LONG) {
                        return new NBTTag(type, listType, key, nbtItem.getLongList(key));
                    } else {
                        return new NBTTag(type, listType, key, nbtItem.getStringList(key));
                    }
                }
                break;
        }
        throw new NBTException("Unable to parse NBT data for item! Type: " + type + " List Type: " + listType + " Key: " + key);
    }

    private <T> T[] manualBox(T[] array, IntFunction<? extends T> supplier) {
        Arrays.setAll(array, supplier);
        return array;
    }
}
