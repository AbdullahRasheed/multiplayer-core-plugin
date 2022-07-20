package me.abdullah.core.commands.accept;

import me.abdullah.core.commands.Command;
import me.abdullah.core.data.GamePlayer;

public class AcceptCommand extends Command {

    public AcceptCommand() {
        super("accept");
    }

    @Override
    protected void onCommand(GamePlayer player, String[] args) {
        if(!player.hasPendingAction(args[0])){
            // TODO error message
            return;
        }

        player.completePendingAction(args[0]);
    }
}
