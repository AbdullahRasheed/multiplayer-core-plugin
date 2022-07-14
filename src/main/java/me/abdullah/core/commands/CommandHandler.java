package me.abdullah.core.commands;

import me.abdullah.core.data.GamePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler implements CommandExecutor {

    private Map<String, Command> commands;
    public CommandHandler(){
        this.commands = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] args) {
        if(commandSender instanceof Player){
            Player player = (Player) commandSender;
            if(commands.containsKey(command.getName())){
                commands.get(command.getName()).runCommand(GamePlayer.get(player), args);
            }
        }
        return false;
    }

    public void registerCommand(String name, Command command){
        commands.put(name, command);
    }
}
