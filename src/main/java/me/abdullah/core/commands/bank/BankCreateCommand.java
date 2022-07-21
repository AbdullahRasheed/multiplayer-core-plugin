package me.abdullah.core.commands.bank;

import me.abdullah.core.commands.Command;
import me.abdullah.core.data.GamePlayer;

public class BankCreateCommand extends Command {

    public BankCreateCommand() {
        super("create");
    }

    @Override
    public void onCommand(GamePlayer player, String[] args) {
        if(player.createAccount()){
            player.sendFormattedMessage("bank_created");
        }else{
            // TODO send an error message (they already have an account)
        }


    }
}
