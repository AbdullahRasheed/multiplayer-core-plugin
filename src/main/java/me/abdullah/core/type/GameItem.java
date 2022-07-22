package me.abdullah.core.type;

import org.bukkit.inventory.ItemStack;

public abstract class GameItem {

    protected String id;
    public GameItem(String id){
        this.id = id;
    }

    public abstract ItemStack getItemStack();

    public String getId() {
        return id;
    }
}
