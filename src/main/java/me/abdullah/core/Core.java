package me.abdullah.core;

import me.abdullah.core.commands.CommandHandler;
import me.abdullah.core.commands.accept.AcceptCommand;
import me.abdullah.core.commands.bank.BankCommand;
import me.abdullah.core.commands.sell.SellCommand;
import me.abdullah.core.config.CityConfig;
import me.abdullah.core.config.Lang;
import me.abdullah.core.data.BankCache;
import me.abdullah.core.data.PlayerCache;
import me.abdullah.core.economy.item.BankNote;
import me.abdullah.core.item.ItemHandler;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Core extends JavaPlugin {

    private static Core INSTANCE;

    private int version;

    private File playerFolder;
    private File bankFolder;

    private PlayerCache playerCache;
    private BankCache bankCache;

    private Lang lang;
    private CityConfig cityConfig;

    public void onEnable(){
        INSTANCE = this;

        PluginLoader loader = new PluginLoader(this);

        this.version = loader.getVersionCode();

        this.lang = new Lang(loader.loadConfig("lang.yml"));
        this.cityConfig = new CityConfig(loader.loadConfig("city.yml"));

        this.playerFolder = loader.mkdirs(new File(getDataFolder(), "playerData"));
        this.bankFolder = loader.mkdirs(new File(getDataFolder(), "bank"));

        this.playerCache = new PlayerCache();
        this.bankCache = new BankCache();
        this.bankCache.retrieveCache();

        playerCache.beginScheduledGarbageCollection(Executors.newSingleThreadScheduledExecutor(), 60, TimeUnit.MINUTES);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        playerCache.beginScheduledCacheStoringRoutine(executor, 15, TimeUnit.MINUTES);
        bankCache.beginScheduledCacheStoringRoutine(executor, 15, TimeUnit.MINUTES);

        loader.registerListener(getMainPlayerCache());

        loader.registerCommandHandler(new CommandHandler());
        loader.registerCommand(new BankCommand());
        loader.registerCommand(new SellCommand());
        loader.registerCommand(new AcceptCommand());

        loader.registerItemHandler(new ItemHandler());
        loader.registerClickable(new BankNote());

        loader.beginUpdateChecker(this);
    }

    public void onDisable(){
        // TODO safely store caches while avoiding conflict with the scheduledexecutorservice
    }

    public int getVersionCode(){
        return version;
    }

    public PlayerCache getMainPlayerCache(){
        return playerCache;
    }

    public BankCache getBank(){
        return bankCache;
    }

    public File getPlayersFolder(){
        return playerFolder;
    }

    public File getBankFolder(){
        return bankFolder;
    }

    public Lang getLang(){
        return lang;
    }

    public CityConfig getCityConfig(){
        return cityConfig;
    }

    public static Core getInstance(){
        return INSTANCE;
    }
}
