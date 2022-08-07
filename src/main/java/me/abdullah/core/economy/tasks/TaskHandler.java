package me.abdullah.core.economy.tasks;

import com.mojang.datafixers.util.Pair;
import me.abdullah.core.Core;
import me.abdullah.core.data.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class TaskHandler {

    private Inventory inventory;
    private Map<GamePlayer, Task> tasks;

    public TaskHandler(){
        this.inventory = Bukkit.createInventory(null, 9, "Task List");
        this.tasks = new ArrayList<>(9);
    }

    public void populate(){
        for (int i = 0; i < inventory.getSize(); i++) {
            Task task = createTask();
            tasks.add();
        }
    }

    public Task createTask(){
        double c = Math.random();
        for (TaskTier taskTier : Core.getInstance().getCityConfig().getTaskTiers()) {
            if(c <= taskTier.getChance()){
                Pair<EntityType, Integer> mob = taskTier.generate();
                return new Task(mob.getFirst())
            }

            c += taskTier.getChance();
        }
    }

    public class Task {

        private Pair<EntityType, Integer> mob;
        private PotionEffectType potionEffectType;
        private int count;
        public Task(Pair<EntityType, Integer> mob, PotionEffectType potionEffectType){
            this.mob = mob;
            this.potionEffectType = potionEffectType;
            this.count = 0;
        }

        public EntityType getEntityType() {
            return mob.getFirst();
        }

        public PotionEffectType getPotionEffectType() {
            return potionEffectType;
        }

        public int getMaxAmount(){
            return mob.getSecond();
        }

        public int getCount(){
            return count;
        }
    }
}