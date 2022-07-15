package me.abdullah.core.item;

import com.saicone.rtag.RtagItem;
import me.abdullah.core.type.Clickable;
import me.abdullah.core.type.Craftable;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

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

        RtagItem rTag = ItemNBT.getRTag(event.getItem());
        Object value = ItemNBT.getSafeValue(rTag, "game_id");
        if(value == null) return;

        String id = (String) value;
        gameItems.get(id).onInteract(event, rTag);
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
