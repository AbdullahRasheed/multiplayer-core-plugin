package me.abdullah.core.item;

import me.abdullah.core.type.Clickable;
import me.abdullah.core.type.Craftable;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class ItemHandler implements Listener {

    private Map<String, Clickable> gameItems;
    public ItemHandler(){
        this.gameItems = new HashMap<>();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(!event.hasItem()) return;

        String id = ItemNBT.getSafeValue(event.getItem(), "game_id", PersistentDataType.STRING);
        if(id == null) return;

        gameItems.get(id).onInteract(event);
    }

    public void registerClickable(String id, Clickable item){
        gameItems.put(id, item);
        Bukkit.getLogger().info(item.getClass().getName() + " clickable registered");
    }

    public void registerCraftable(Craftable item){
        Bukkit.addRecipe(item.getRecipe());
        Bukkit.getLogger().info(item.getClass().getName() + " craftable registered");
    }
}
