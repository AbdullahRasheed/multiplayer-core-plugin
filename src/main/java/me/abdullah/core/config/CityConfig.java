package me.abdullah.core.config;

import org.bukkit.configuration.file.FileConfiguration;

public class CityConfig {

    private FileConfiguration config;
    public CityConfig(FileConfiguration config){
        config.options().copyDefaults(true);

        this.config = config;
    }

    public int getPricePerBlock(){
        return config.getInt("price-per-block");
    }

    public double getAdditionalLayerMultiplier(){
        return config.getDouble("additional-layer-multiplier");
    }

}
