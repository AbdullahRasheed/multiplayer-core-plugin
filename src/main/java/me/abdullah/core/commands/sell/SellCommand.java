package me.abdullah.core.commands.sell;

import me.abdullah.core.Core;
import me.abdullah.core.commands.Command;
import me.abdullah.core.config.CityConfig;
import me.abdullah.core.data.GamePlayer;
import org.bukkit.inventory.ItemStack;

public class SellCommand extends Command {

    public SellCommand() {
        super("sell");
    }

    @Override
    protected void onCommand(GamePlayer player, String[] args) {
        if(args.length == 0){
            ItemStack itemStack = player.getItemInMainHand();
            if(itemStack == null){
                // TODO error message
                return;
            }

            CityConfig config = Core.getInstance().getCityConfig();

            if(!config.hasPrice(itemStack.getType())){
                // TODO error message
                return;
            }

            int price = config.getPrice(itemStack.getType());
            // TODO confirmation clickable message

            player.setPendingAction(getCommandName(), () -> {
                if(!player.getInventory().contains(itemStack)){
                    // TODO error message
                    return;
                }

                player.getInventory().remove(itemStack);
                player.getAccount().unsafeAddMoney(price*itemStack.getAmount());
            });
        }
    }
}
