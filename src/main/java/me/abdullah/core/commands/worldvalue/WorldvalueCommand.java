package me.abdullah.core.commands.worldvalue;

import me.abdullah.core.commands.Command;
import me.abdullah.core.data.GamePlayer;
import me.abdullah.core.world.WorldValues;

public class WorldvalueCommand extends Command {

    public WorldvalueCommand() {
        super("worldvalue");
    }

    @Override
    protected void onCommand(GamePlayer player, String[] args) {
        // TODO clean this up (and make a command value to make it console only instead of the if statement)
        if(player == null) return;

        if(args[0].equalsIgnoreCase("CREEPER_EXPLOSIONS")){
            WorldValues.CREEPER_EXPLOSIONS = Boolean.parseBoolean(args[1]);
        }else if(args[0].equalsIgnoreCase("GHAST_EXPLOSIONS")){
            WorldValues.GHAST_EXPLOSIONS = Boolean.parseBoolean(args[1]);
        }else if(args[0].equalsIgnoreCase("ENDERMAN_GRIEFING")){
            WorldValues.ENDERMAN_GRIEFING = Boolean.parseBoolean(args[1]);
        }
    }
}
