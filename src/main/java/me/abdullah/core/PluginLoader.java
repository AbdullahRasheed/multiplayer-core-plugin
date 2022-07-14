package me.abdullah.core;

import me.abdullah.core.commands.Command;
import me.abdullah.core.commands.CommandHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class PluginLoader {

    private CommandHandler handler;

    private Plugin plugin;
    public PluginLoader(Plugin plugin){
        this.plugin = plugin;
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

        return YamlConfiguration.loadConfiguration(file);
    }

    public void registerListener(Listener listener){
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public void registerCommand(Command command){
        handler.registerCommand(command.getCommandName(), command);
        plugin.getServer().getPluginCommand(command.getCommandName())
                .setExecutor(handler);
    }

    public void registerCommandHandler(CommandHandler handler){
        this.handler = handler;
    }
}
