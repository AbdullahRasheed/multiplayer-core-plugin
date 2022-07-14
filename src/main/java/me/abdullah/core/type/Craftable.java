package me.abdullah.core.type;

import org.bukkit.inventory.Recipe;

public abstract class Craftable extends GameItem {

    public Craftable(String id) {
        super(id);
    }

    public abstract Recipe getRecipe();
}
