package me.abdullah.core.economy.tasks;

import com.mojang.datafixers.util.Pair;
import me.abdullah.core.Core;
import org.bukkit.entity.EntityType;

import java.util.List;

public class TaskTier {

    private double chance;
    private List<Pair<EntityType, Integer>> mobs;

    public TaskTier(double chance, List<Pair<EntityType, Integer>> mobs){
        this.chance = chance;
        this.mobs = mobs;
    }

    public double getChance() {
        return chance;
    }

    public Pair<EntityType, Integer> generate(){
        return mobs.get(Core.getInstance().getRandom().nextInt(mobs.size()));
    }

    public List<Pair<EntityType, Integer>> getMobs() {
        return mobs;
    }
}
