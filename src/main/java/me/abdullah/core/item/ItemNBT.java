package me.abdullah.core.item;

import me.abdullah.core.Core;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class ItemNBT {

    public static <Z, T> T getValue(ItemStack itemStack, String key, PersistentDataType<Z, T> type){
        return itemStack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(Core.getInstance(), key), type);
    }

    public static <Z, T> T getSafeValue(ItemStack itemStack, String key, PersistentDataType<Z, T> type){
        return itemStack.getItemMeta().getPersistentDataContainer().getOrDefault(new NamespacedKey(Core.getInstance(), key), type, null);
    }
}
