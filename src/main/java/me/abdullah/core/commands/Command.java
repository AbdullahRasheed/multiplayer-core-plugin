package me.abdullah.core.commands;

import me.abdullah.core.Core;
import me.abdullah.core.data.GamePlayer;
import me.abdullah.core.economy.BankAccount;

import java.util.*;

public abstract class Command {

    protected int cursor;

    private String command;
    private Map<String, Command> subcommands;
    public Command(String command, Command... subcommands){
        this.command = command;
        this.subcommands = new HashMap<>();

        for (Command subcommand : subcommands) {
            this.subcommands.put(subcommand.getCommandName(), subcommand);
        }

        this.cursor = 0;
    }

    protected abstract void onCommand(GamePlayer player, String[] args);

    public void runCommand(GamePlayer player, String[] args){
        cursor = 0;

        if(args.length == 0){
            onCommand(player, args);
            return;
        }

        if(subcommands.containsKey(args[0])){
            subcommands.get(args[0])
                    .runCommand(player,
                            Arrays.copyOfRange(args, 1, args.length));
            return;
        }

        onCommand(player, args);
    }

    public String getCommandName(){
        return command;
    }

    public String getString(String[] args){
        return args[cursor++];
    }

    public int getInteger(String[] args){
        return Integer.parseInt(args[cursor++]);
    }

    public GamePlayer getPlayer(String[] args){
        return GamePlayer.get(args[cursor++]);
    }

    public BankAccount getAccount(String[] args){
        return Core.getInstance().getBank().get(args[cursor++]);
    }

}
