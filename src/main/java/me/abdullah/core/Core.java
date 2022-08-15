package me.abdullah.core;

import me.abdullah.core.commands.CommandHandler;
import me.abdullah.core.commands.accept.AcceptCommand;
import me.abdullah.core.commands.bank.BankCommand;
import me.abdullah.core.commands.worldvalue.WorldvalueCommand;
import me.abdullah.core.config.CityConfig;
import me.abdullah.core.config.Lang;
import me.abdullah.core.data.cache.BankCache;
import me.abdullah.core.data.cache.PlayerCache;
import me.abdullah.core.data.cache.listeners.PlayerCacheListener;
import me.abdullah.core.economy.item.BankNote;
import me.abdullah.core.item.ItemHandler;
import me.abdullah.core.world.events.GriefListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Core extends JavaPlugin {

    // TODO go through all classes and resolve warnings

    private static Core INSTANCE;

    private int version;

    private Random random;

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

        this.random = new Random();

        this.lang = new Lang(loader.loadConfig("lang.yml"));
        this.cityConfig = new CityConfig(loader.loadConfig("city.yml"));

        this.playerFolder = loader.mkdirs(new File(getDataFolder(), "playerData"));
        this.bankFolder = loader.mkdirs(new File(getDataFolder(), "bank"));

        this.playerCache = new PlayerCache();
        this.bankCache = new BankCache();
        this.bankCache.deserialize(bankFolder);

        playerCache.beginScheduledGarbageCollection(playerFolder, Executors.newSingleThreadScheduledExecutor(), 60, TimeUnit.MINUTES);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
        playerCache.beginScheduledCacheStoringRoutine(playerFolder, executor, 15, TimeUnit.MINUTES);
        bankCache.beginScheduledCacheStoringRoutine(bankFolder, executor, 15, TimeUnit.MINUTES);

        loader.registerListener(new PlayerCacheListener(playerCache, playerFolder));
        loader.registerListener(new GriefListener());

        loader.registerCommandHandler(new CommandHandler());
        loader.registerCommand(new BankCommand());
        loader.registerCommand(new AcceptCommand());
        loader.registerCommand(new WorldvalueCommand());

        loader.registerItemHandler(new ItemHandler());
        loader.registerClickable(new BankNote());

        loader.registerGameItemRecipes();

        loader.beginUpdateChecker(this);
    }

    public void onDisable(){
        // TODO safely store caches while avoiding conflict with the scheduledexecutorservice
    }

    public int getVersionCode(){
        return version;
    }

    public Random getRandom(){
        return random;
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
