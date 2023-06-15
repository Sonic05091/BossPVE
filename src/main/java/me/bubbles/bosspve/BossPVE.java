package me.bubbles.bosspve;

import me.bubbles.bosspve.commands.manager.CommandManager;
import me.bubbles.bosspve.configs.ConfigManager;
import me.bubbles.bosspve.entities.manager.EntityManager;
import me.bubbles.bosspve.events.manager.EventManager;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.mysql.MySQL;
import me.bubbles.bosspve.stages.StageManager;
import me.bubbles.bosspve.ticker.Ticker;
import me.bubbles.bosspve.ticker.TimerManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;

public final class BossPVE extends JavaPlugin {
    private CommandManager commandManager;
    private EventManager eventManager;
    private ConfigManager configManager;
    private ItemManager itemManager;
    private TimerManager timerManager;
    private EntityManager entityManager;
    private StageManager stageManager;
    private Economy economy;
    private MySQL mySQL;
    private Ticker ticker;

    @Override
    public void onEnable() {
        //// INSTANCE VARIABLES

        // Configs
        configManager=new ConfigManager(this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        configManager.addConfig(
                "config.yml",
                "messages.yml",
                "stages.yml"
        );

        // MANAGERS
        // THIS ORDER IS VERY IMPORTANT, SWAPPING THINGS AROUND WILL CAUSE VALUES TO BE RETURNED AS NULL
        timerManager=new TimerManager(this);
        eventManager=new EventManager(this);
        itemManager=new ItemManager(this);
        entityManager=new EntityManager(this);
        stageManager=new StageManager(this, configManager.getConfig("stages.yml"));
        commandManager=new CommandManager(this);
        mySQL=new MySQL(configManager.getConfig("config.yml").getFileConfiguration().getConfigurationSection("mySQL"));
        setupEconomy();

        // Ticker
        ticker=new Ticker(this).setEnabled(true);

    }

    @Override
    public void onDisable() {

    }

    // RELOAD CFG
    public void reload() {
        getStageManager().setSpawningAll(false);
        getConfigManager().reloadAll();
        stageManager=new StageManager(this,configManager.getConfig("stages.yml"));
    }

    // TICKER
    public void onTick() {
        itemManager.onTick();
        timerManager.onTick();
    }

    // VAULT
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    // GETTERS
    public long getEpochTimestamp() {
        return Instant.now().getEpochSecond();
    }
    public CommandManager getCommandManager() {
        return commandManager;
    }
    public EventManager getEventManager() {
        return eventManager;
    }
    public ConfigManager getConfigManager() {
        return configManager;
    }
    public ItemManager getItemManager() {
        return itemManager;
    }
    public TimerManager getTimerManager() {
        return timerManager;
    }
    public EntityManager getEntityManager() {
        return entityManager;
    }
    public StageManager getStageManager() {
        return stageManager;
    }
    public Economy getEconomy() {
        return economy;
    }
    public MySQL getMySQL() {
        return mySQL;
    }
    public Ticker getTicker() {
        return ticker;
    }

}
