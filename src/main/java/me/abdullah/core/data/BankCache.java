package me.abdullah.core.data;

import me.abdullah.core.Core;
import me.abdullah.core.economy.BankAccount;
import me.abdullah.core.economy.BankAccountData;
import me.abdullah.core.io.Serializables;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class BankCache {

    private Map<UUID, BankAccount> accounts;
    public BankCache(){
        this.accounts = new HashMap<>();
    }

    public void retrieveCache(){
        try{
            BankAccountData[] bank = Serializables.readBankAccountFolder();
            for (BankAccountData data : bank) {
                accounts.put(data.uuid, new BankAccount(data));
            }
        }catch (IOException e){
            Bukkit.getLogger().severe("Could not continue retrieving the bank cache");
            return;
        }
    }

    public void retrieveCache(File folder){
        // TODO allow manual set up with custom folder
    }

    public boolean createAccount(UUID uuid){
        if(accounts.containsKey(uuid)) return false;

        accounts.put(uuid, new BankAccount(new BankAccountData(uuid)));
        return true;
    }

    public BankAccount get(UUID uuid){
        return accounts.getOrDefault(uuid, null);
    }

    public BankAccount get(String name){
        try {
            return Executors.newSingleThreadExecutor().submit(new Callable<BankAccount>() {
                @Override
                public BankAccount call() throws Exception {
                    for (UUID uuid : accounts.keySet()) {
                        if(Bukkit.getOfflinePlayer(uuid).getName().equalsIgnoreCase(name)){
                            return accounts.get(uuid);
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
}
