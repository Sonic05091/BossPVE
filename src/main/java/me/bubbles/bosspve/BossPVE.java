package me.bubbles.bosspve;

import me.bubbles.bosspve.commands.manager.CommandManager;
import me.bubbles.bosspve.configs.ConfigManager;
import me.bubbles.bosspve.entities.manager.EntityManager;
import me.bubbles.bosspve.events.manager.EventManager;
import me.bubbles.bosspve.items.manager.EnchantManager;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.ticker.Ticker;
import me.bubbles.bosspve.ticker.TimerManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;

public final class BossPVE extends JavaPlugin {
    private CommandManager commandManager;
    private EventManager eventManager;
    private ConfigManager configManager;
    private ItemManager itemManager;
    private TimerManager timerManager;
    private EntityManager entityManager;
    private Ticker ticker;
    private String name="bosspve";

    @Override
    public void onEnable() {
        //// INSTANCE VARIABLES

        // Configs
        configManager=new ConfigManager(this);
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        configManager.addConfig(
                "config.yml",
                "messages.yml"
        );

        // MANAGERS
        // THIS ORDER IS VERY IMPORTANT, SWAPPING THINGS AROUND WILL CAUSE VALUES TO BE RETURNED AS NULL
        timerManager=new TimerManager(this);
        eventManager=new EventManager(this);
        itemManager=new ItemManager(this);
        entityManager=new EntityManager(this);
        commandManager=new CommandManager(this);

        // Ticker
        ticker=new Ticker(this).setEnabled(true);

    }

    @Override
    public void onDisable() {

    }

    // RELOAD CFG
    public void reload() {
        getConfigManager().reloadAll();
    }

    // TICKER
    public void onTick() {
        itemManager.onTick();
        timerManager.onTick();
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
    public Ticker getTicker() {
        return ticker;
    }

}
