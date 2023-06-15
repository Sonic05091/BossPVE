package me.bubbles.bosspve.stages;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.configs.Config;

import java.util.HashSet;
import java.util.Optional;

public class StageManager {

    private BossPVE plugin;
    private HashSet<Stage> stageList;
    private Config config;

    public StageManager(BossPVE plugin, Config config) {
        this.plugin=plugin;
        this.config=config;
    }

    private void loadStages() {
        for(String stageKey : config.getFileConfiguration().getKeys(false)) {
            Stage stage = new Stage(plugin, config.getFileConfiguration().getConfigurationSection(stageKey)).getStage();
            if(stage!=null) {
                stageList.add(stage);
            }
        }
    }

    public boolean setSpawningEnabled(int level, boolean bool) {
        Optional<Stage> optStage = stageList.stream().filter(stage -> stage.getLevelRequirement()==level).findFirst();
        if(!optStage.isPresent()) {
            return false;
        }
        optStage.get().setEnabled(bool);
        return true;
    }

    public void setSpawningEnabledAll(boolean bool) {
        stageList.forEach(stage -> stage.setEnabled(bool));
    }

}
