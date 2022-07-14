package me.abdullah.core.data;

import me.abdullah.core.Core;
import me.abdullah.core.io.Serializables;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerCache implements Listener {

    private Map<UUID, GamePlayer> players;

    public PlayerCache(){
        this.players = new HashMap<>();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Bukkit.getScheduler().runTaskAsynchronously(Core.getInstance(), () -> {
            UUID uuid = event.getPlayer().getUniqueId();

            if(!players.containsKey(uuid)) {
                try {
                    GamePlayer.GamePlayerInfo player = Serializables.readPlayerNullable(uuid);
                    if(player == null){
                        players.put(uuid, GamePlayer.create(new GamePlayer.GamePlayerInfo(uuid)));
                        return;
                    }

                    players.put(uuid, GamePlayer.create(player));
                }catch(IOException e){
                    players.put(uuid, GamePlayer.create(new GamePlayer.GamePlayerInfo(uuid)));
                }

                return;
            }

            players.get(uuid).setOnline();
        });
    }

    // TODO routine to clean up cache every ~30-60 mins

    public GamePlayer get(UUID uuid){
        return players.getOrDefault(uuid, null);
    }
}
