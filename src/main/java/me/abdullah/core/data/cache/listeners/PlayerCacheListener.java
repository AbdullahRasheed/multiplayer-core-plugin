package me.abdullah.core.data.cache.listeners;

import me.abdullah.core.Core;
import me.abdullah.core.data.GamePlayer;
import me.abdullah.core.data.cache.PlayerCache;
import me.abdullah.core.io.Serializables;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerCacheListener implements Listener {

    private PlayerCache cache;
    private File folder;
    public PlayerCacheListener(PlayerCache cache, File folder){
        this.cache = cache;
        this.folder = folder;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            UUID uuid = event.getPlayer().getUniqueId();

            if(!cache.containsKey(uuid)) {
                try {
                    GamePlayer.GamePlayerInfo player = Serializables.readFileNullable(folder, uuid, GamePlayer.GamePlayerInfo.class);
                    if(player == null){
                        cache.set(uuid, GamePlayer.create(new GamePlayer.GamePlayerInfo(uuid)));
                        return;
                    }

                    cache.set(uuid, GamePlayer.create(player));
                }catch(IOException e){
                    cache.set(uuid, GamePlayer.create(new GamePlayer.GamePlayerInfo(uuid)));
                }

                return;
            }

            cache.get(uuid).setOnline();
        });
    }

}
