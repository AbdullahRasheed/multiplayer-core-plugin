package me.abdullah.core.data;

import me.abdullah.core.Core;
import me.abdullah.core.economy.BankAccount;
import me.abdullah.core.economy.BankAccountData;
import me.abdullah.core.type.GameItem;
import me.abdullah.core.util.StringContext;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GamePlayer {

    private Map<String, Runnable> pendingActions;

    private UUID uuid;
    private Player player;

    private GamePlayer(GamePlayerInfo info){
        this.uuid = info.uuid;
        this.player = Bukkit.getPlayer(info.uuid);

        this.pendingActions = new HashMap<>();
    }

    public void sendMessage(String s){
        player.sendMessage(s);
    }

    public void sendMessage(BaseComponent... components){
        player.spigot().sendMessage(components);
    }

    public void sendFormattedMessage(String s){
        player.sendMessage(Core.getInstance().getLang().getPossibleString(s));
    }

    public void sendFormattedMessage(String s, StringContext context){
        player.sendMessage(Core.getInstance().getLang().getPossibleString(s, context));
    }

    public PlayerInventory getInventory(){
        return player.getInventory();
    }

    public HashMap<Integer, ItemStack> addItem(GameItem item){
        return getInventory().addItem(item.getItemStack());
    }

    public ItemStack getItemInMainHand(){
        return player.getInventory().getItemInMainHand();
    }

    public boolean createAccount(){
        return Core.getInstance().getBank().createAccount(uuid);
    }

    public void setOnline(){
        this.player = Bukkit.getPlayer(uuid);
    }

    public void setPendingAction(String key, Runnable runnable){
        pendingActions.put(key, runnable);
    }

    public Runnable getPendingAction(String key){
        return pendingActions.get(key);
    }

    public void runPendingAction(String key){
        pendingActions.get(key);
    }

    public void completePendingAction(String key){
        runPendingAction(key);
        pendingActions.remove(key);
    }

    public boolean isOnline(){
        return player.isOnline();
    }

    public BankAccount getAccount() {
        return Core.getInstance().getBank().get(uuid);
    }

    public UUID getUUID(){
        return uuid;
    }

    public GamePlayerInfo getAsSerializable(){
        return new GamePlayerInfo(uuid);
    }

    public static GamePlayer get(UUID uuid){
        return Core.getInstance().getMainPlayerCache().get(uuid);
    }

    public static GamePlayer get(Player player){
        if(player == null) return null;
        return get(player.getUniqueId());
    }

    public static GamePlayer get(String name){
        return get(Bukkit.getPlayer(name));
    }

    public static GamePlayer create(GamePlayerInfo info){
        return new GamePlayer(info);
    }

    public static class GamePlayerInfo implements Serializable {
        public UUID uuid;

        public GamePlayerInfo(UUID uuid){
            this.uuid = uuid;
        }
    }
}