package me.abdullah.core.type;

import org.bukkit.event.player.PlayerInteractEvent;

public abstract class Clickable extends GameItem {

    public Clickable(String id) {
        super(id);
    }

    public abstract void onInteract(PlayerInteractEvent event);

}
