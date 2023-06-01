package me.bubbles.bosspve;

import me.bubbles.bosspve.commands.manager.CommandManager;
import me.bubbles.bosspve.configs.ConfigManager;
import me.bubbles.bosspve.events.manager.EventManager;
import me.bubbles.bosspve.ticker.Ticker;
import me.bubbles.bosspve.users.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class BossPVE extends JavaPlugin {
    private CommandManager commandManager;
    private EventManager eventManager;
    private ConfigManager configManager;
    private UserManager userManager;
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
                "config.yml"
        );

        // MANAGERS
        commandManager=new CommandManager(this);
        eventManager=new EventManager(this);
        userManager=new UserManager(this);

        // Ticker
        // TODO MAKE TICKER USEFUL
        ticker=new Ticker(this).setEnabled(false);

        //// USERS (in case of plugin is loaded after startup)
        if(!(getServer().getOnlinePlayers().size()==0)) {
            for(Player player : getServer().getOnlinePlayers()) {
                userManager.addPlayer(player);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        ticker.setEnabled(false);
    }

    // RELOAD CFG
    public void reload() {
        getConfigManager().reloadAll();
    }

    // TICKER
    public void onTick() {} // TODO

    // GETTERS
    public CommandManager getCommandManager() {
        return commandManager;
    }
    public EventManager getEventManager() {
        return eventManager;
    }
    public ConfigManager getConfigManager() {
        return configManager;
    }
    public UserManager getUserManager() {
        return userManager;
    }
    public Ticker getTicker() {
        return ticker;
    }

}
