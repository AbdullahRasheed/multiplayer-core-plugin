package me.abdullah.core.economy.item;

import com.saicone.rtag.RtagItem;
import me.abdullah.core.Core;
import me.abdullah.core.config.Lang;
import me.abdullah.core.data.GamePlayer;
import me.abdullah.core.item.ItemNBT;
import me.abdullah.core.type.GameItem;
import me.abdullah.core.type.IClickable;
import me.abdullah.core.util.ItemStackBuilder;
import me.abdullah.core.util.StringContext;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BankNote extends GameItem implements IClickable {

    // TODO maybe make the itemstack static, initialize it onEnable, and
    // TODO then just update the "money" tag when getting a new instance
    // TODO except that would require a clone so nvm but keep thinking on that
    // TODO OR JUST MAKE A CONSTRUCTOR WITH NO PARAMETERS DUMMY

    private ItemStack itemStack;

    public BankNote(UUID uuid, int amount){
        super("bank_note");

        Lang lang = Core.getInstance().getLang();
        StringContext context = new StringContext(uuid, amount);
        this.itemStack = new ItemStackBuilder(Material.PAPER)
                .setDisplayName(lang.getString("bank_note_displayname", context))
                .setLore(lang.getStringList("bank_note_lore", context))
                .build();

        ItemNBT.loadNBT(this.itemStack, rTag -> {
            rTag.set("game_id", id);
            rTag.set(amount, "money");
        });
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public void onInteract(PlayerInteractEvent event, RtagItem rTag) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR
                || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            GamePlayer player = GamePlayer.get(event.getPlayer());
            int money = (int) ItemNBT.getSafeValue(rTag, "money");

            player.getAccount().unsafeAddMoney(money);
            player.sendMessage(Core.getInstance().getLang().getPossibleString("check_deposit", new StringContext(money)));

        }
    }
}
