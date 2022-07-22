package me.abdullah.core.config;

import me.abdullah.core.util.StringContext;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Lang {

    // TODO cache color coded strings/string lists

    private FileConfiguration config;
    public Lang(FileConfiguration config){
        this.config = config;

        config.options().copyDefaults(true);
    }

    public String getString(String key){
        return color(config.getString(key));
    }

    public String getString(String key, StringContext context){
        return context.complete(getString(key));
    }

    public String getPossibleString(String key){
        if(config.contains(key)){
            return getString(key);
        }

        return key;
    }

    public String getPossibleString(String key, StringContext context){
        if(config.contains(key)){
            return getString(key, context);
        }

        return key;
    }

    public List<String> getStringList(String key){
        return config.getStringList(key);
    }

    public List<String> getStringList(String key, StringContext context){
        List<String> list = new ArrayList<>();
        for (String s : getStringList(key)) {
            list.add(color(context.complete(s)));
        }

        return list;
    }

    private String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
