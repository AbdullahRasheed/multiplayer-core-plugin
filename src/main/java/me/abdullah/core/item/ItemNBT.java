package me.abdullah.core.item;

import com.saicone.rtag.RtagItem;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.function.Consumer;

public class ItemNBT {

    public static RtagItem getRTag(ItemStack itemStack){
        return new RtagItem(itemStack);
    }

    public static void loadNBT(ItemStack itemStack, Consumer<RtagItem> consumer){
        RtagItem rTag = getRTag(itemStack);

        consumer.accept(rTag);
        rTag.load();
    }

    public static Object getSafeValue(ItemStack itemStack, Object... path){
        return getRTag(itemStack).getOptional(path).value();
    }

    public static Object getSafeValue(RtagItem rTag, Object... path){
        return rTag.getOptional(path).value();
    }
}
