package me.abdullah.core.io;

import me.abdullah.core.Core;
import me.abdullah.core.data.cache.BankCache;
import me.abdullah.core.data.GamePlayer;
import me.abdullah.core.data.cache.PlayerCache;
import me.abdullah.core.economy.BankAccountData;
import me.abdullah.core.io.raw.BinSerializable;
import org.bukkit.Bukkit;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class Serializables {

    public static <T extends Serializable> T readFileNullable(File folder, UUID uuid, Class<T> clazz) throws IOException {
        BinSerializable serializable = new BinSerializable();

        T t = null;
        try {
             t = (T) serializable.read(
                    new File(folder, uuid.toString() + ".bin")
            );
        }catch (IOException e){
            throw new IOException(e);
        }catch (ClassNotFoundException ce){
            Bukkit.getLogger().severe("Could not read serializable file! " + ce.getMessage());
        }

        return t;
    }

    public static <T extends Serializable> T[] readFolder(Class<T> clazz, File folder) throws IOException {
        File[] files = folder.listFiles();
        T[] cache = (T[]) Array.newInstance(clazz, files.length);

        BinSerializable serializable = new BinSerializable();

        try {
            for (int i = 0; i < files.length; i++) {
                cache[i] = (T) serializable.read(files[i]);
            }
        }catch (IOException e){
            throw new IOException(e);
        }catch (ClassNotFoundException ce){
            Bukkit.getLogger().severe("Could not read cache folder! " + ce.getMessage());
            return null;
        }

        return cache;
    }

    public static <T extends Serializable> void storeFolder(Map<UUID, T> storeMap, Class<T> clazz, File folder) throws IOException {
        BinSerializable serializable = new BinSerializable();

        for (UUID uuid : storeMap.keySet()){
            serializable.store(storeMap.get(uuid), new File(folder, uuid.toString() + ".bin"));
        }
    }

    public static <T, Z extends Serializable> void storeFolder(Map<UUID, T> storeMap, Class<Z> clazz, Function<T, Z> func, File folder) throws IOException {
        BinSerializable serializable = new BinSerializable();

        for (UUID uuid : storeMap.keySet()){
            serializable.store(func.apply(storeMap.get(uuid)), new File(folder, uuid.toString() + ".bin"));
        }
    }
}
