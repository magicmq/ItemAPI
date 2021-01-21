package dev.magicmq.itemapi.nbt;

import java.io.Serializable;

/**
 * A class representing an individual NBT tag.
 */
public class NBTTag implements Serializable {

    private static final long serialVersionUID = -4827853524004316582L;

    private final NBTTagType type;
    private final NBTTagType listType;
    private final String key;
    private Object value;

    /**
     * Create a new NBT tag with the specified type, key, and value
     * @param type The {@link NBTTagType} representing the type that the tag is
     * @param key The key of the tag
     * @param value The value of the tag, should be another NBTTag if the tag is a compound tag
     */
    public NBTTag(NBTTagType type, String key, Object value) {
        this(type, null, key, value);
    }

    /**
     * Create a new NBT tag with the specified type, list type, key, and value
     * @param type The {@link NBTTagType} representing the type that the tag is
     * @param listType If the <code>type</code> is a list, this should be the type representing values in the list; this should be null otherwise
     * @param key The key of the tag
     * @param value The value of the tag, should be another NBTTag if the tag is a compound tag
     */
    public NBTTag(NBTTagType type, NBTTagType listType, String key, Object value) {
        this.type = type;
        this.listType = listType;
        this.key = key;
        this.value = value;
    }

    /**
     * Get this tag's type.
     * @return The type of the tag
     * @see NBTTagType
     */
    public NBTTagType getType() {
        return type;
    }

    /**
     * Get this tag's list type.
     * @return The list type of the tag if the tag is a LIST, null if otherwise
     * @see NBTTagType
     */
    public NBTTagType getListType() {
        return listType;
    }

    /**
     * Check if this tag is a compound tag.
     * @return True if the tag is a compound tag, false if otherwise
     */
    public boolean isCompoundTag() {
        return type == NBTTagType.COMPOUND;
    }

    /**
     * Check if this tag is a list tag.
     * @return True if the tag is a list tag, false if othwerwise
     */
    public boolean isList() {
        return type == NBTTagType.LIST;
    }

    /**
     * Get the key of the tag.
     * @return The key of the tag
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the value of the tag.
     * <b>Note:</b> The type of the object returned from this method will depend on the type of this tag and casting will be required.
     * @return The value of this tag
     */
    public Object getValue() {
        return value;
    }

    /**
     * Set the value of the tag.
     * @param value The value of the tag to set
     */
    public void setValue(Object value) {
        this.value = value;
    }
}
