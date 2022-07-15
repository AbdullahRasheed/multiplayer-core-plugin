package me.abdullah.core.data;

import me.abdullah.core.Core;
import me.abdullah.core.io.Serializables;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    public void beginScheduledGarbageCollection(ScheduledExecutorService executor, long delay, TimeUnit unit){
        executor.schedule(() -> {
            Iterator<Map.Entry<UUID, GamePlayer>> iter = players.entrySet().iterator();
            while(iter.hasNext()){
                if(!iter.next().getValue().isOnline()) iter.remove();
            }
        }, delay, unit);
    }

    public void beginScheduledCacheStoringRoutine(ScheduledExecutorService service, long delay, TimeUnit unit){
        service.schedule(() -> {
            try {
                Serializables.storePlayerCache(this);
            } catch (IOException e) {
                Bukkit.getLogger().severe("Could not store the player cache! " + e.getMessage());
            }
        }, delay, unit);
    }

    public GamePlayer get(UUID uuid){
        return players.getOrDefault(uuid, null);
    }

    public Map<UUID, GamePlayer> getPlayers(){
        return players;
    }
}
