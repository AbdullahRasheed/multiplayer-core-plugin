package me.abdullah.core.io;

import me.abdullah.core.Core;
import me.abdullah.core.data.cache.BankCache;
import me.abdullah.core.data.GamePlayer;
import me.abdullah.core.data.cache.PlayerCache;
import me.abdullah.core.economy.BankAccountData;
import me.abdullah.core.io.raw.BinSerializable;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.UUID;

public class Serializables {

    public static GamePlayer.GamePlayerInfo readPlayerNullable(UUID uuid) throws IOException {
        BinSerializable serializable = new BinSerializable();

        GamePlayer.GamePlayerInfo player = null;
        try {
             player = (GamePlayer.GamePlayerInfo) serializable.read(
                    new File(Core.getInstance().getPlayersFolder(), uuid.toString() + ".bin")
            );
        }catch (IOException e){
            throw new IOException(e);
        }catch (ClassNotFoundException ce){
            Bukkit.getLogger().severe("Could not read player file! " + ce.getMessage());
        }

        return player;
    }

    // TODO abstractify caches to reduce these methods into one method called by storePlayer, storePlayerCache, storeAccount, and storeBank
    public static void storePlayer(UUID uuid, PlayerCache cache) throws IOException {
        BinSerializable serializable = new BinSerializable();
        serializable.store(cache.get(uuid).getAsSerializable(), new File(Core.getInstance().getPlayersFolder(), uuid.toString() + ".bin"));
    }

    public static void storePlayerCache(PlayerCache cache) throws IOException {
        for (UUID uuid : cache.getPlayers().keySet()) {
            storePlayer(uuid, cache);
        }
    }

    public static BankAccountData[] readBankAccountFolder(File folder) throws IOException {
        File[] files = folder.listFiles();
        BankAccountData[] bank = new BankAccountData[files.length];

        BinSerializable serializable = new BinSerializable();

        try {
            for (int i = 0; i < files.length; i++) {
                bank[i] = (BankAccountData) serializable.read(files[i]);
            }
        }catch (IOException e){
            throw new IOException(e);
        }catch (ClassNotFoundException ce){
            Bukkit.getLogger().severe("Could not read bank folder! " + ce.getMessage());
            return null;
        }

        return bank;
    }

    public static void storeAccount(UUID uuid, BankCache cache) throws IOException {
        BinSerializable serializable = new BinSerializable();
        serializable.store(cache.get(uuid).getAsSerializable(), new File(Core.getInstance().getBankFolder(), uuid.toString() + ".bin"));
    }

    public static void storeBank(BankCache cache) throws IOException {
        for (UUID uuid : cache.getAccounts().keySet()) {
            storeAccount(uuid, cache);
        }
    }
}
