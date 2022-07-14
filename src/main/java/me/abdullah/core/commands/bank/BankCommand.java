package me.abdullah.core.commands.bank;

import me.abdullah.core.commands.Command;
import me.abdullah.core.data.GamePlayer;

public class BankCommand extends Command {

    public BankCommand() {
        super("bank",
                new BankCreateCommand(),
                new BankPayCommand(),
                new BankWithdrawCommand(),
                new BankBalanceCommand());
    }

    @Override
    public void onCommand(GamePlayer player, String[] args) {
        // TODO list all subcommands to the player
    }
}
