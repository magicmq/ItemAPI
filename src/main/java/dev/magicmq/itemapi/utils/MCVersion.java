package dev.magicmq.itemapi.utils;

import org.bukkit.Bukkit;

/**
 * Utility class designed to check for the running Minecraft version and if it is above/below other versions.
 */
public enum MCVersion {

    UNKNOWN(Integer.MAX_VALUE),
    v1_7_R4(174),
    v1_8_R3(183),
    v1_9_R1(191),
    v1_9_R2(192),
    v1_10_R1(1101),
    v1_11_R1(1111),
    v1_12_R1(1121),
    v1_13_R1(1131),
    v1_13_R2(1132),
    v1_14_R1(1141),
    v1_15_R1(1151),
    v1_16_R1(1161),
    v1_16_R2(1162),
    v1_16_R3(1163),
    v1_17_R1(1171),
    v1_18_R1(1181),
    v1_18_R2(1182),
    v1_19_R1(1191),
    v1_19_R2(1192);

    private final int versionId;

    MCVersion(int versionId) {
        this.versionId = versionId;
    }

    public static MCVersion getVersion() {
        String versionString = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        MCVersion version;
        try {
            version = MCVersion.valueOf(versionString);
        } catch (IllegalArgumentException e) {
            version = MCVersion.UNKNOWN;
        }

        return version;
    }

    public static boolean isCurrentVersionAtLeast(MCVersion version) {
        return getVersion().versionId >= version.versionId;
    }
}
