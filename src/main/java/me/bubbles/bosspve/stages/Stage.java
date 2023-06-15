package me.bubbles.bosspve.stages;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntityBase;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;

public class Stage {

    private BossPVE plugin;
    private Location pos1;
    private Location pos2;
    private Location spawn;
    private double xpMultiplier;
    private double moneyMultiplier;
    private HashSet<StageEntity> entityList;
    private ConfigurationSection section;
    private boolean valid;
    private final List<String> requiredStageKeys =
            List.of(
                    "spawn",
                    "pos1",
                    "pos2",
                    "xpMultiplier",
                    "moneyMultiplier"
            );

    private final List<String> requiredEntityKeys =
            List.of(
                    "pos",
                    "interval"
            );

    public Stage(BossPVE plugin, ConfigurationSection section) {
        this.plugin=plugin;
        this.section=section;
        for(String key : requiredStageKeys) {
            if(!section.contains(key)) {
                valid=false;
                plugin.getLogger().log(Level.SEVERE,"Could not load stage: " + getLevelRequirement() +" @ "+key);
                return;
            }
        }
        this.entityList=new HashSet<>();
        this.spawn=section.getLocation("spawn");
        this.pos1=section.getLocation("pos1");
        this.pos2=section.getLocation("pos2");
        this.xpMultiplier=section.getDouble("xpMultiplier");
        this.moneyMultiplier=section.getDouble("moneyMultiplier");
        loadEntities();
    }

    private void loadEntities() {
        ConfigurationSection entities = section.getConfigurationSection("entities");
        for(String entityKey : entities.getKeys(false)) {
            IEntityBase entityBase = plugin.getEntityManager().getEntityByName(entityKey);
            if(entityBase==null) {
                plugin.getLogger().log(Level.WARNING, "Could not load entity: "+entityKey+" @ "+getLevelRequirement());
                continue;
            }
            ConfigurationSection entitySection = entities.getConfigurationSection(entityKey);
            for(String key : requiredEntityKeys) {
                if(!entitySection.contains(key)) {
                    plugin.getLogger().log(Level.SEVERE,"Could not load entity: " + entityKey +"."+key+" @ "+key);
                    continue;
                }
            }
            StageEntity stageEntity = new StageEntity(plugin,
                    entityBase,
                    entitySection.getLocation("pos"),
                    entitySection.getInt("interval")
            );
            entityList.add(stageEntity);
        }
    }

    public Stage getStage() {
        if(valid) {
            return this;
        } else {
            return null;
        }
    }

    public Stage setEnabled(boolean bool) {
        entityList.forEach(stageEntity -> stageEntity.setEnabled(bool));
        return this;
    }

    public int getLevelRequirement() {
        return Integer.parseInt(section.getName());
    }

}