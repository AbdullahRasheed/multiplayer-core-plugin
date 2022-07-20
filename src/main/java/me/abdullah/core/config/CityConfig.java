package me.abdullah.core.config;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityConfig {

    private Map<Material, Integer> priceMap;

    private FileConfiguration config;
    public CityConfig(FileConfiguration config){
        config.options().copyDefaults(true);

        this.config = config;

        this.priceMap = new HashMap<>();
        List<String> list = config.getStringList("price-map");
        for (String s : list) {
            priceMap.put(Material.getMaterial(s.split(":")[0].toUpperCase()),
                    Integer.parseInt(s.split(":")[1]));
        }
    }

    public int getPricePerBlock(){
        return config.getInt("price-per-block");
    }

    public double getAdditionalLayerMultiplier(){
        return config.getDouble("additional-layer-multiplier");
    }

    public Map<Material, Integer> getPriceMap(){
        return priceMap;
    }

    public int getPrice(Material material){
        return priceMap.get(material);
    }

    public boolean hasPrice(Material material){
        return priceMap.containsKey(material);
    }

}
