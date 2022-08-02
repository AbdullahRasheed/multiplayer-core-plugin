package me.abdullah.core.data;

import me.abdullah.core.Core;
import me.abdullah.core.economy.BankAccount;
import me.abdullah.core.economy.BankAccountData;
import me.abdullah.core.type.GameItem;
import me.abdullah.core.util.StringContext;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

public class GamePlayer {

    // TODO return optionals for pendingActions and pendingCallables (getPendingAction and getPendingCallable)
    private Map<String, Runnable> pendingActions;
    private Map<String, Object> pendingCallables;

    private UUID uuid;
    private Player player;

    private GamePlayer(GamePlayerInfo info){
        this.uuid = info.uuid;
        this.player = Bukkit.getPlayer(info.uuid);

        this.pendingActions = new HashMap<>();
        this.pendingCallables = new HashMap<>();
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

    public Location getLocation(){
        return player.getLocation();
    }

    public double getX(){
        return getLocation().getX();
    }

    public double getY(){
        return getLocation().getY();
    }

    public double getZ(){
        return getLocation().getZ();
    }

    public int getBlockX(){
        return getLocation().getBlockX();
    }

    public int getBlockY(){
        return getLocation().getBlockY();
    }

    public int getBlockZ(){
        return getLocation().getBlockZ();
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

    public void sendBlockChange(Location location, BlockData data){
        player.sendBlockChange(location, data);
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

    public boolean hasPendingAction(String key){
        return pendingActions.containsKey(key);
    }

    public void runPendingAction(String key){
        pendingActions.get(key).run();
    }

    public void removePendingAction(String key){
        pendingActions.remove(key);
    }

    public void completePendingAction(String key){
        runPendingAction(key);
        removePendingAction(key);
    }

    public void setPendingCallable(String key, Object callable){
        pendingCallables.put(key, callable);
    }

    public Object getPendingCallable(String key){
        return pendingCallables.get(key);
    }

    public boolean hasPendingCallable(String key){
        return pendingCallables.containsKey(key);
    }

    public void removePendingCallable(String key){
        pendingCallables.remove(key);
    }

    public void resetPendingQueues(){
        pendingActions.clear();
        pendingCallables.clear();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof GamePlayer)) return false;

        return this.uuid.equals(((GamePlayer) obj).uuid);
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