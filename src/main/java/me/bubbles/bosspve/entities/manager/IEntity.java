package me.bubbles.bosspve.entities.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.util.UtilCustomEvents;
import net.minecraft.world.entity.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public interface IEntity {

    Entity spawn(Location location);
    default void onEvent(BossPVE plugin, Event event) {
        if(event instanceof EntityDeathEvent) {
            UtilCustomEvents uce = new UtilCustomEvents(plugin,event);
            uce.customEntityDeathEvent(this);
        }
        if(event instanceof EntityDamageByEntityEvent) {
            UtilCustomEvents uce = new UtilCustomEvents(plugin,event);
            uce.customEntityDamageByEntityEvent(this);
        }
    }
    List<ItemStack> getDrops();
    double getMoney();
    int getXp();
    int getDefaultHp();
    int getDamage();
    String getUncoloredName();
    String getNBTIdentifier();
    default boolean hasSameTagAs(Entity entity) {
        if(entity.getTags().isEmpty()) {
            return false;
        }
        return entity.getTags().contains(getNBTIdentifier());
    }

    default boolean hasSameTagAs(org.bukkit.entity.Entity bukkitEntity) {
        return hasSameTagAs(((CraftEntity) bukkitEntity).getHandle());
    }

}
