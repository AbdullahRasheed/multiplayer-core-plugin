package me.abdullah.core.commands.bank;

import me.abdullah.core.commands.Command;
import me.abdullah.core.data.GamePlayer;
import me.abdullah.core.economy.BankAccount;

public class BankBalanceCommand extends Command {

    public BankBalanceCommand() {
        super("balance");
    }

    @Override
    protected void onCommand(GamePlayer player, String[] args) {
        BankAccount account = player.getAccount();
        if(account == null){
            // TODO error message
            return;
        }

        player.sendMessage(Integer.toString(account.getMoney()));
    }
}
