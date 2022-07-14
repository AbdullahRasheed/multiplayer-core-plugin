package me.abdullah.core.type;

import com.saicone.rtag.RtagItem;
import org.bukkit.event.player.PlayerInteractEvent;

public interface IClickable {

    void onInteract(PlayerInteractEvent event, RtagItem rTag);
}
