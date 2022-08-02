package me.abdullah.core.world.events;

import me.abdullah.core.world.WorldValues;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplosionListener implements Listener {

    @EventHandler
    public void onExplode(EntityExplodeEvent event){
        if(event.getEntity().getType() == EntityType.CREEPER){
            event.setCancelled(!WorldValues.CREEPER_EXPLOSIONS);
        }

        if(event.getEntity().getType() == EntityType.GHAST){
            event.setCancelled(!WorldValues.GHAST_EXPLOSIONS);
        }
    }
}
