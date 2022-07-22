package me.abdullah.core.commands.sell;

import me.abdullah.core.Core;
import me.abdullah.core.commands.Command;
import me.abdullah.core.config.CityConfig;
import me.abdullah.core.data.GamePlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
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

            player.setPendingAction(getCommandName(), () -> {
                if(!player.getInventory().contains(itemStack)){
                    // TODO error message
                    return;
                }

                player.getInventory().remove(itemStack);
                player.getAccount().unsafeAddMoney(price*itemStack.getAmount());
            });

            TextComponent component = new net.md_5.bungee.api.chat.TextComponent("YES");
            component.setColor(ChatColor.GREEN);
            component.setBold(true);
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept sell"));

            player.sendMessage(component);
        }
    }
}
