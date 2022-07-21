package me.abdullah.core.commands.bank;

import me.abdullah.core.commands.Command;
import me.abdullah.core.data.GamePlayer;
import me.abdullah.core.economy.BankAccount;
import me.abdullah.core.util.StringContext;

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

        player.sendFormattedMessage("bank_balance", new StringContext(account.getMoney()));
    }
}
