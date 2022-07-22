package me.abdullah.core.commands.bank;

import me.abdullah.core.commands.Command;
import me.abdullah.core.data.GamePlayer;
import me.abdullah.core.economy.item.BankNote;
import me.abdullah.core.util.StringContext;

public class BankWithdrawCommand extends Command {

    public BankWithdrawCommand() {
        super("withdraw");
    }

    @Override
    public void onCommand(GamePlayer player, String[] args) {
        if(args.length == 0){
            // TODO error message
            return;
        }

        int money;
        try {
            money = getInteger(args);
            if(money <= 0) throw new NumberFormatException();
        }catch (NumberFormatException e){
            player.sendMessage("You must provide a positive integer to withdraw!");
            player.sendMessage("/bank withdraw {amount}");
            return;
        }

        if(player.getAccount().safeAddMoney(-money)){
            if(player.addItem(new BankNote(player.getUUID(), money)).isEmpty()){
                player.sendFormattedMessage("check_created", new StringContext(money));
            }else{
                player.sendFormattedMessage("not_enough_money", new StringContext(money));
            }
        }
    }
}
