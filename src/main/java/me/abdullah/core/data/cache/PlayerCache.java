package me.abdullah.core.data.cache;

import me.abdullah.core.Core;
import me.abdullah.core.data.GamePlayer;
import me.abdullah.core.economy.BankAccount;
import me.abdullah.core.economy.BankAccountData;
import me.abdullah.core.io.Serializables;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerCache extends GameCache<UUID, GamePlayer> {

    @Override
    public GamePlayer get(UUID uuid){
        return getOrDefault(uuid, null);
    }

    @Override
    public void serialize(File folder) {
        try {
            Serializables.storeFolder(map, GamePlayer.GamePlayerInfo.class, GamePlayer::getAsSerializable, folder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deserialize(File folder) {
        try {
            GamePlayer.GamePlayerInfo[] players = Serializables.readFolder(GamePlayer.GamePlayerInfo.class, folder);
            for (GamePlayer.GamePlayerInfo data : players) {
                map.put(data.uuid, GamePlayer.create(data));
            }
        }catch (IOException e){
            Bukkit.getLogger().severe("Could not continue retrieving the bank cache: " + e.getMessage());
            return;
        }
    }

    @Override
    public void beginScheduledGarbageCollection(File folder, ScheduledExecutorService executor, long delay, TimeUnit unit){
        executor.schedule(() -> {
            Iterator<Map.Entry<UUID, GamePlayer>> iter = map.entrySet().iterator();
            while(iter.hasNext()){
                if(!iter.next().getValue().isOnline()) iter.remove();
            }
        }, delay, unit);
    }
}
