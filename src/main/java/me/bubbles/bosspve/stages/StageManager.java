package me.bubbles.bosspve.stages;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.configs.Config;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Optional;
import java.util.logging.Level;

public class StageManager {

    private BossPVE plugin;
    private HashSet<Stage> stageList;
    private Config config;

    public StageManager(BossPVE plugin, Config config) {
        this.plugin=plugin;
        this.config=config;
        this.stageList=new HashSet<>();
        loadStages();
    }

    private void loadStages() {
        if(config.getFileConfiguration().getKeys(false).isEmpty()) {
            plugin.getLogger().log(Level.WARNING, "No not find any stages.");
            return;
        }
        for(String stageKey : config.getFileConfiguration().getKeys(false)) {
            Stage stage = new Stage(plugin, config.getFileConfiguration().getConfigurationSection(stageKey)).getStage();
            if(stage!=null) {
                stageList.add(stage);
            }else{
                plugin.getLogger().log(Level.SEVERE, "A stage is null!");
            }
        }
        setSpawningAll(true);
    }

    public boolean setSpawning(int level, boolean bool) {
        Optional<Stage> optStage = stageList.stream().filter(stage -> stage.getLevelRequirement()==level).findFirst();
        if(!optStage.isPresent()) {
            return false;
        }
        optStage.get().setEnabled(bool);
        return true;
    }

    public void setSpawningAll(boolean bool) {
        stageList.forEach(stage -> stage.setEnabled(bool));
    }

    public Stage getStage(Location location) {
        Optional<Stage> optStage = stageList.stream().filter(stage -> stage.isInside(location)).findFirst();
        return optStage.orElse(null);
    }

    public Stage getStage(int requiredLevel) {
        Optional<Stage> optStage = stageList.stream().filter(stage -> stage.getLevelRequirement()==requiredLevel).findFirst();
        return optStage.orElse(null);
    }

    public HashSet<Stage> getStages() {
        return stageList;
    }

    public boolean inAStage(Player player) {
        return getStage(player.getLocation())!=null;
    }

}
