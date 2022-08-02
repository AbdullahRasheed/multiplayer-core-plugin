package me.abdullah.core.data;

import me.abdullah.core.Core;
import me.abdullah.core.data.cache.ICache;
import me.abdullah.core.data.cache.SingleLoadCache;
import me.abdullah.core.economy.BankAccount;
import me.abdullah.core.economy.BankAccountData;
import me.abdullah.core.io.Serializables;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

public class BankCache implements SingleLoadCache<UUID, BankAccount> {

    private Map<UUID, BankAccount> accounts;
    public BankCache(){
        this.accounts = new HashMap<>();
    }

    @Override
    public void beginScheduledCacheStoringRoutine(ScheduledExecutorService service, long delay, TimeUnit unit){
        service.schedule(() -> {
            try {
                Serializables.storeBank(this);
            } catch (IOException e) {
                Bukkit.getLogger().severe("Could not store the player cache! " + e.getMessage());
            }
        }, delay, unit);
    }

    @Override
    public BankAccount get(UUID uuid){
        return getOrDefault(uuid, null);
    }

    public BankAccount get(String name){
        try {
            return Executors.newSingleThreadExecutor().submit(new Callable<BankAccount>() {
                @Override
                public BankAccount call() throws Exception {
                    for (UUID uuid : accounts.keySet()) {
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

    @Override
    public BankAccount getOrDefault(UUID key, BankAccount def) {
        return accounts.getOrDefault(key, def);
    }

    @Override
    public boolean containsKey(UUID key) {
        return accounts.containsKey(key);
    }

    public void load() {
        load(Core.getInstance().getBankFolder());
    }

    @Override
    public void load(File folder){
        try{
            BankAccountData[] bank = Serializables.readBankAccountFolder(folder);
            for (BankAccountData data : bank) {
                accounts.put(data.uuid, new BankAccount(data));
            }
        }catch (IOException e){
            Bukkit.getLogger().severe("Could not continue retrieving the bank cache: " + e.getMessage());
            return;
        }
    }

    public boolean createAccount(UUID uuid){
        if(containsKey(uuid)) return false;

        accounts.put(uuid, new BankAccount(new BankAccountData(uuid)));
        return true;
    }

    public Map<UUID, BankAccount> getAccounts(){
        return accounts;
    }
}
