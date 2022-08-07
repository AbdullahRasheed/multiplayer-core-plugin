package me.abdullah.core.config;

import com.mojang.datafixers.util.Pair;
import me.abdullah.core.economy.tasks.TaskTier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;

public class CityConfig {

    private FileConfiguration config;
    private List<TaskTier> taskTiers; // TODO use a better data structure for this
    public CityConfig(FileConfiguration config){
        this.config = config;
        this.taskTiers = new ArrayList<>();

        ConfigurationSection tiersSection = config.getConfigurationSection("tiers");
        for (String tier : tiersSection.getKeys(false)) {
            double chance = tiersSection.getDouble(tier + ".chance");
            List<Pair<EntityType, Integer>> mobs = new ArrayList<>();

            for (String s : tiersSection.getStringList(tier + ".mobs")) {
                String[] args = s.split(",");
                EntityType entityType = EntityType.valueOf(args[0].toUpperCase());
                int count = Integer.parseInt(args[1]);

                mobs.add(new Pair<>(entityType, count));
            }

            TaskTier taskTier = new TaskTier(chance, mobs);
            taskTiers.add(taskTier);
        }

        taskTiers.sort((t1, t2) -> t1.getChance() < t2.getChance() ? 1 : -1);
    }

    public List<TaskTier> getTaskTiers(){
        return taskTiers;
    }
}
