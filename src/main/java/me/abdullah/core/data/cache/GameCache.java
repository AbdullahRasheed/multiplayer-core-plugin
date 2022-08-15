package me.abdullah.core.data.cache;

import me.abdullah.core.data.GamePlayer;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class GameCache<K, V> {

    protected Map<K, V> map;
    public GameCache(){
        this.map = new HashMap<>();
    }

    public V get(K key){
        return map.get(key);
    }

    public V getOrDefault(K key, V value){
        return map.getOrDefault(key, value);
    }

    public void set(K key, V val){
        map.put(key, val);
    }

    public boolean containsKey(K key){
        return map.containsKey(key);
    }

    public abstract void serialize(File folder);

    /***
     * Deserializes the provided folder AND loads it into the cache
     * @param folder
     */
    public abstract void deserialize(File folder);

    public void beginScheduledCacheStoringRoutine(File folder, ScheduledExecutorService service, long delay, TimeUnit unit){
        service.schedule(() -> serialize(folder), delay, unit);
    }

    public void beginScheduledGarbageCollection(File folder, ScheduledExecutorService service, long delay, TimeUnit unit){
        service.schedule(() -> {
            Iterator<Map.Entry<K, V>> iter = map.entrySet().iterator();
            while(iter.hasNext()){
                if(iter.next().getValue() == null) iter.remove();
            }
        }, delay, unit);
    }

    public Map<K, V> getMap(){
        return map;
    }
}
