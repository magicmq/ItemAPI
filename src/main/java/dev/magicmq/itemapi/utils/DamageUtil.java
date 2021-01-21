package dev.magicmq.itemapi.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Utility class designed to apply and extract damage (durability) from items in 1.13+.
 * @see ItemStack#setDurability(short) For durability prior to 1.13
 * @see Damageable
 */
public class DamageUtil {

    public static ItemStack applyDamage(ItemStack item, short damage) {
        if (damage > 0) {
            ItemMeta meta = item.getItemMeta();
            if (meta instanceof Damageable) {
                ((Damageable) meta).setDamage(damage);
                item.setItemMeta(meta);
            }
        }
        return item;
    }

    public static short extractDamage(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta instanceof Damageable) {
            return (short) ((Damageable) meta).getDamage();
        }
        return 0;
    }
}
