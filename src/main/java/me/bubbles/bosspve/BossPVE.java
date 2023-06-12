package me.bubbles.bosspve;

import me.bubbles.bosspve.commands.manager.CommandManager;
import me.bubbles.bosspve.configs.ConfigManager;
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
    private EnchantManager enchantManager;
    private TimerManager timerManager;
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
        timerManager=new TimerManager(this);
        eventManager=new EventManager(this);
        itemManager=new ItemManager(this);
        enchantManager=new EnchantManager(this);
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
        enchantManager.onTick();
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
    public EnchantManager getEnchantManager() {
        return enchantManager;
    }
    public TimerManager getTimerManager() {
        return timerManager;
    }
    public Ticker getTicker() {
        return ticker;
    }

}
