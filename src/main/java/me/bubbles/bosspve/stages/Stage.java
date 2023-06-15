package me.bubbles.bosspve.stages;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntityBase;
import me.bubbles.bosspve.util.UtilUserData;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

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

    public double getXpMultiplier() {
        return xpMultiplier;
    }

    public double getMoneyMultiplier() {
        return moneyMultiplier;
    }

    public boolean isInside(Location location) {

        // POS 1
        double x1 = pos1.getX();
        double z1 = pos1.getZ();
        double y1 = pos1.getY();

        // POS 2
        double x2 = pos2.getX();
        double z2 = pos2.getZ();
        double y2 = pos2.getY();

        // WITHIN
        boolean withinX = (location.getX() > Math.min(x1,x2) && (location.getX() < Math.max(x1,x2)));
        boolean withinZ = (location.getZ() > Math.min(z1,z2) && (location.getZ() < Math.max(z1,z2)));
        boolean withinY = (location.getY() > Math.min(y1,y2) && (location.getY() < Math.max(y1,y2)));

        return withinX && withinY && withinZ;

    }

    public Stage getInside(Location location) {
        if(isInside(location)) {
            return this;
        }
        return null;
    }

    public boolean isAllowed(Player player) {
        UtilUserData utilUserData = plugin.getMySQL().getData(player.getUniqueId());
        return utilUserData.getLevel()>=getLevelRequirement();
    }

}