package me.abdullah.core.util;

import org.bukkit.Bukkit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StringContext {

    private UUID uuid;
    private int money;
    private Date date;

    private Map<String, String> placeholders = new HashMap<>();

    public StringContext(UUID uuid, int money){
        placeholders.put("%uuid%", uuid.toString());
        placeholders.put("%money%", Integer.toString(money));
    }

    public StringContext(int money){
        placeholders.put("%money%", Integer.toString(money));
    }

    public StringContext(UUID uuid, int money, Date date){
        placeholders.put("%uuid%", uuid.toString());
        placeholders.put("%money%", Integer.toString(money));

        DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        placeholders.put("%datetime%", format.format(date));
    }

    public String complete(String s){
        placeholders.forEach((key, value) -> s.replaceAll(key, value));
        return s;
    }
}
