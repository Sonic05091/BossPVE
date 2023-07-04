package me.bubbles.bosspve;

import com.onarandombox.MultiverseCore.MultiverseCore;
import me.bubbles.bosspve.commands.manager.CommandManager;
import me.bubbles.bosspve.configs.ConfigManager;
import me.bubbles.bosspve.entities.manager.EntityManager;
import me.bubbles.bosspve.events.manager.EventManager;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.mysql.MySQL;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.stages.StageManager;
import me.bubbles.bosspve.ticker.Ticker;
import me.bubbles.bosspve.ticker.TimerManager;
import me.bubbles.bosspve.users.UserManager;
import me.bubbles.bosspve.util.PAPI;
import me.bubbles.bosspve.util.UpdateXP;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

public final class BossPVE extends JavaPlugin {

    private MultiverseCore multiverseCore;
    private CommandManager commandManager;
    private EventManager eventManager;
    private ConfigManager configManager;
    private ItemManager itemManager;
    private TimerManager timerManager;
    private EntityManager entityManager;
    private StageManager stageManager;
    private UserManager userManager;
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
        if(!setupEconomy()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if(!setupMultiverse()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if(!setupPAPI()) {
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        userManager=new UserManager(this);
        mySQL=new MySQL(configManager.getConfig("config.yml").getFileConfiguration().getConfigurationSection("mySQL"));
        timerManager=new TimerManager();
        itemManager=new ItemManager(this);
        entityManager=new EntityManager(this);
        eventManager=new EventManager(this);
        initStageManager();
        commandManager=new CommandManager(this);

        // Ticker
        ticker=new Ticker(this).setEnabled(true);

        // XP Bar
        timerManager.addTimer(new UpdateXP(this));

    }

    @Override
    public void onDisable() {
        if(stageManager!=null) {
            stageManager.getStages().forEach(stage -> stage.setEnabled(false));
            stageManager.getStages().forEach(Stage::killAll);
        }
    }

    // RELOAD CFG
    public void reload() {
        getStageManager().setSpawningAll(false);
        getConfigManager().reloadAll();
        initStageManager();
    }

    public void initStageManager() {
        if(stageManager!=null) {
            stageManager.getStages().forEach(stage -> stage.setEnabled(false));
            stageManager.getStages().forEach(Stage::killAll);
        }
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
            getLogger().log(Level.SEVERE, "NO VAULT PLUGIN");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            getLogger().log(Level.SEVERE, "NO ECONOMY SUPPORT");
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    // Worlds
    private boolean setupMultiverse() {
        multiverseCore = (MultiverseCore) getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (multiverseCore == null) {
            getLogger().log(Level.SEVERE, "No Multiverse-Core");
            return false;
        }
        return true;
    }

    private boolean setupPAPI() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPI(this).register();
            return true;
        }
        return false;
    }

    // GETTERS
    public long getEpochTimestamp() {
        return Instant.now().getEpochSecond();
    }
    public MultiverseCore getMultiverseCore() {
        return multiverseCore;
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
    public UserManager getUserManager() {
        return userManager;
    }

}
