package me.abdullah.core.data.cache;

import me.abdullah.core.economy.BankAccount;
import me.abdullah.core.economy.BankAccountData;
import me.abdullah.core.io.Serializables;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.*;

public class BankCache extends GameCache<UUID, BankAccount> {

    @Override
    public BankAccount get(UUID uuid){
        return getOrDefault(uuid, null);
    }

    // TODO use cached thread pool instead
    public BankAccount get(String name){
        try {
            return Executors.newSingleThreadExecutor().submit(new Callable<BankAccount>() {
                @Override
                public BankAccount call() throws Exception {
                    for (UUID uuid : map.keySet()) {
                        if(Bukkit.getOfflinePlayer(uuid).getName().equalsIgnoreCase(name)){
                            return get(uuid);
                        }
                    }

                    return null;
                }
            }).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean createAccount(UUID uuid){
        if(containsKey(uuid)) return false;

        set(uuid, new BankAccount(new BankAccountData(uuid)));
        return true;
    }

    @Override
    public void serialize(File folder) {
        try {
            Serializables.storeFolder(map, BankAccountData.class, BankAccount::getAsSerializable, folder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deserialize(File folder) {
        try {
            BankAccountData[] bank = Serializables.readFolder(BankAccountData.class, folder);
            for (BankAccountData data : bank) {
                map.put(data.uuid, new BankAccount(data));
            }
        }catch (IOException e){
            Bukkit.getLogger().severe("Could not continue retrieving the bank cache: " + e.getMessage());
            return;
        }
    }
}
