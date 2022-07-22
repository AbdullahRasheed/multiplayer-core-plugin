package me.abdullah.core.economy.item;

import me.abdullah.core.Core;
import me.abdullah.core.config.Lang;
import me.abdullah.core.data.GamePlayer;
import me.abdullah.core.item.ItemNBT;
import me.abdullah.core.type.Clickable;
import me.abdullah.core.util.ItemStackBuilder;
import me.abdullah.core.util.StringContext;
import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class BankNote extends Clickable {

    private ItemStack itemStack;

    public BankNote(UUID uuid, int amount){
        super("bank_note");

        Lang lang = Core.getInstance().getLang();
        StringContext context = new StringContext(uuid, amount);
        this.itemStack = new ItemStackBuilder(Material.PAPER)
                .setDisplayName(lang.getString("bank_note_displayname", context))
                .setLore(lang.getStringList("bank_note_lore", context))
                .setPersistentData("game_id", PersistentDataType.STRING, id)
                .setPersistentData("money", PersistentDataType.INTEGER, amount)
                .build();
    }

    public BankNote(){
        super("bank_note");
    }

    @Override
    public ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR
                || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            GamePlayer player = GamePlayer.get(event.getPlayer());
            int money = ItemNBT.getValue(event.getItem(), "money", PersistentDataType.INTEGER);

            player.getAccount().unsafeAddMoney(money*event.getItem().getAmount());
            player.sendFormattedMessage("check_deposit", new StringContext(money));

            player.getInventory().remove(event.getItem());
        }
    }
}
