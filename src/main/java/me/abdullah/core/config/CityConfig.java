package me.abdullah.core.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class CityConfig {

    private FileConfiguration config;
    private Map<Material, Integer> sellValues;
    public CityConfig(FileConfiguration config){
        this.config = config;

        this.sellValues = new HashMap<>();
        for (String s : config.getStringList("price-map")) {
            String[] args = s.split(":");
            sellValues.put(Material.valueOf(args[0].toUpperCase()), Integer.parseInt(args[1]));
        }
    }

    public boolean hasPrice(Material material){
        return sellValues.containsKey(material);
    }

    public int getPrice(Material material){
        return sellValues.get(material);
    }
}
