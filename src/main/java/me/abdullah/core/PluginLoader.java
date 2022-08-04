package me.abdullah.core;

import me.abdullah.core.commands.Command;
import me.abdullah.core.commands.CommandHandler;
import me.abdullah.core.item.GameItemRecipe;
import me.abdullah.core.item.ItemHandler;
import me.abdullah.core.type.Clickable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class PluginLoader {

    private CommandHandler commandHandler;
    private ItemHandler itemHandler;

    private Plugin plugin;
    public PluginLoader(Plugin plugin){
        this.plugin = plugin;
    }

    public int getVersionCode(){
        String version = plugin.getDescription().getVersion();
        int v = 0;

        String[] split = version.split("\\.");
        for (int i = 0; i < split.length; i++) {
            v += Integer.parseInt(split[i]) * Math.pow(10, split.length - i - 1);
        }

        return v;
    }

    public File mkdirs(File folder){
        if(!folder.exists()){
            folder.mkdirs();
        }

        return folder;
    }

    public FileConfiguration loadConfig(String name){
        File file = new File(plugin.getDataFolder(), name);
        if(!file.exists()){
            file.getParentFile().mkdirs();
            plugin.saveResource(name, false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        Reader reader = new InputStreamReader(plugin.getResource(name));
        FileConfiguration def = YamlConfiguration.loadConfiguration(reader);

        config.setDefaults(def);

        return config;
    }

    public void registerListener(Listener listener){
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public void registerItemHandler(ItemHandler itemHandler){
        this.itemHandler = itemHandler;
        registerListener(itemHandler);
    }

    public void registerCommand(Command command){
        commandHandler.registerCommand(command.getCommandName(), command);
        plugin.getServer().getPluginCommand(command.getCommandName())
                .setExecutor(commandHandler);
    }

    public void registerCommandHandler(CommandHandler handler){
        this.commandHandler = handler;
    }

    public void registerClickable(Clickable clickable){
        itemHandler.registerClickable(clickable.getId(), clickable);
    }

    public void registerGameItemRecipe(GameItemRecipe recipe){
        try {
            Bukkit.addRecipe(recipe.getRecipe().call());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void registerGameItemRecipes(){
        for (GameItemRecipe value : GameItemRecipe.values()) {
            registerGameItemRecipe(value);
        }
    }

    public void beginUpdateChecker(Core core){
        try {
            // TODO read version directly from plugin.yml instead
            String versionUrl = "https://raw.githubusercontent.com/AbdullahRasheed/multiplayer-core-plugin/master/target/classes/plugin.yml";
            String downloadUrl = "https://github.com/AbdullahRasheed/multiplayer-core-plugin/raw/master/target/MultiplayerCore.jar";
            new Updater(core, versionUrl, downloadUrl).start();
        }catch (IOException e){
            Bukkit.getLogger().severe("Could not start the update checker! " + e.getMessage());
        }
    }
}
