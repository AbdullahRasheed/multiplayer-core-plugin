package me.abdullah.core.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StringContext {

    private Map<String, String> placeholders = new HashMap<>();

    public StringContext(UUID uuid, int money){
        placeholders.put("%uuid%", uuid.toString());
        placeholders.put("%money%", Integer.toString(money));
    }

    public StringContext(int money){
        placeholders.put("%money%", Integer.toString(money));
    }

    public StringContext(UUID uuid){
        placeholders.put("%uuid%", uuid.toString());
    }

    public StringContext(String player){
        placeholders.put("%player%", player);
    }

    public StringContext(String player, int money){
        placeholders.put("%player%", player);
        placeholders.put("%money%", Integer.toString(money));
    }

    public StringContext(UUID uuid, int money, Date date){
        placeholders.put("%uuid%", uuid.toString());
        placeholders.put("%money%", Integer.toString(money));

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        placeholders.put("%datetime%", format.format(date));
    }

    public String complete(String s){
        for (String key : placeholders.keySet()) {
            s = s.replaceAll(key, placeholders.get(key));
        }

        return s;
    }
}
