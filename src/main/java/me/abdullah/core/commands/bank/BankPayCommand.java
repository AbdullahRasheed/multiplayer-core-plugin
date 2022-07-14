package me.abdullah.core.commands.bank;

import me.abdullah.core.Core;
import me.abdullah.core.commands.Command;
import me.abdullah.core.data.GamePlayer;
import me.abdullah.core.economy.BankAccount;
import me.abdullah.core.util.StringContext;

public class BankPayCommand extends Command {

    public BankPayCommand() {
        super("pay");
    }

    // TODO use lang for messages
    // TODO run all bank queries in an async thread
    @Override
    public void onCommand(GamePlayer player, String[] args) {
        if(args.length < 2){
            // TODO error message
            return;
        }

        player.sendMessage("Looking up " + args[0] + "...");
        BankAccount recipient = getAccount(args);
        if(recipient == null){
            player.sendMessage("That player does not have a bank account");
            return;
        }

        player.sendMessage("Found!");

        int money;
        try {
             money = getInteger(args);
             if(money <= 0) throw new NumberFormatException();
        }catch (NumberFormatException e){
            player.sendMessage("You must provide a positive integer of how much you want to send!");
            player.sendMessage("/bank pay {username} {amount}");
            return;
        }

        if(player.getAccount().safeAddMoney(-money)){
            recipient.unsafeAddMoney(money);
            player.sendMessage("Successfully sent " + args[0] + " " + money + "!");
            return;
        }

        player.sendFormattedMessage("not_enough_money", new StringContext(money));
    }
}
