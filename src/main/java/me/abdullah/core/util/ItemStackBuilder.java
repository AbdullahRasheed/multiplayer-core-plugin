package me.abdullah.core.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemStackBuilder {

    private ItemStack itemStack;
    private ItemMeta meta;
    public ItemStackBuilder(Material materal){
        this.itemStack = new ItemStack(materal);
        this.meta = itemStack.getItemMeta();
    }

    public ItemStackBuilder setDisplayName(String name){
        meta.setDisplayName(name);
        return this;
    }

    public ItemStackBuilder setLore(String... lore){
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore){
        meta.setLore(lore);
        return this;
    }

    public ItemStack build(){
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
