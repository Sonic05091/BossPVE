package me.bubbles.bosspve.entities.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.Hellbringer;
import me.bubbles.bosspve.entities.Ogre;
import me.bubbles.bosspve.entities.Simpleton;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.HashSet;

public class EntityManager {

    private HashSet<IEntityBase> entities;
    private BossPVE plugin;

    public EntityManager(BossPVE plugin) {
        this.plugin=plugin;
        this.entities=new HashSet<>();
        registerEntities(
                new Simpleton(plugin),
                new Ogre(plugin),
                new Hellbringer(plugin)
        );
    }

    private void registerEntities(IEntityBase... entity) {
        entities.addAll(Arrays.asList(entity));
    }

    public void onEvent(Event event) {
        entities.forEach(iEntityBase -> iEntityBase.onEvent(plugin,event));
    }

    public HashSet<IEntityBase> getEntities() {
        return entities;
    }

    public IEntityBase getEntityByName(String name) {
        for(IEntityBase entity : entities) {
            if(ChatColor.stripColor(entity.getUncoloredName()).replace(" ","_").equalsIgnoreCase(name)) {
                return entity;
            }
        }
        return null;
    }

}
