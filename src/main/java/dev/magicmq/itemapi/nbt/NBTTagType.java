package dev.magicmq.itemapi.nbt;

import de.tr7zw.changeme.nbtapi.NBTType;

/**
 * An enum representing all possible NBT tag types accepted by Minecraft.
 */
public enum NBTTagType {

    BOOLEAN("N/A"),
    BYTE("NBTTagByte"),
    SHORT("NBTTagShort"),
    INT("NBTTagInt"),
    LONG("NBTTagLong"),
    FLOAT("NBTTagFloat"),
    DOUBLE("NBTTagDouble"),
    BYTE_ARRAY("NBTTagByteArray"),
    STRING("NBTTagString"),
    COMPOUND("NBTTagCompound"),
    INT_ARRAY("NBTTagIntArray"),
    LIST("NBTTagList");

    private final String nbtAPIType;

    NBTTagType(String nbtAPIType) {
        this.nbtAPIType = nbtAPIType;
    }

    /**
     * Designed for interfacing with NBTAPI, this will convert between NBTAPI's tag types and the tag types in this enum.
     * @param apiType The NBTAPI tag type to convert
     * @return The converted tag if one was found, null if no tag was found
     */
    public static NBTTagType getFromNBTAPIType(NBTType apiType) {
        for (NBTTagType type : values()) {
            if (type.nbtAPIType.equals(apiType.name()))
                return type;
        }
        return null;
    }

}
