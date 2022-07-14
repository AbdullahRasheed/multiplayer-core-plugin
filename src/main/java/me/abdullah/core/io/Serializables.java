package me.abdullah.core.io;

import me.abdullah.core.Core;
import me.abdullah.core.data.GamePlayer;
import me.abdullah.core.economy.BankAccountData;
import me.abdullah.core.io.raw.BinSerializable;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Serializables {

    public static GamePlayer.GamePlayerInfo readPlayerNullable(UUID uuid) throws IOException {
        GamePlayer.GamePlayerInfo player = null;
        try {
             player = (GamePlayer.GamePlayerInfo) BinSerializable.read(
                    new File(Core.getInstance().getPlayersFolder(), uuid.toString())
            );
        }catch (IOException e){
            throw new IOException(e);
        }catch (ClassNotFoundException ce){
            Bukkit.getLogger().severe("Could not read player file! " + ce.getMessage());
        }

        return player;
    }

    public static BankAccountData[] readBankAccountFolder() throws IOException {
        File folder = Core.getInstance().getBankFolder();

        File[] files = folder.listFiles();
        BankAccountData[] bank = new BankAccountData[files.length];

        try {
            for (int i = 0; i < files.length; i++) {
                bank[i] = (BankAccountData) BinSerializable.read(files[i]);
            }
        }catch (IOException e){
            throw new IOException(e);
        }catch (ClassNotFoundException ce){
            Bukkit.getLogger().severe("Could not read bank folder! " + ce.getMessage());
            return null;
        }

        return bank;
    }
}
