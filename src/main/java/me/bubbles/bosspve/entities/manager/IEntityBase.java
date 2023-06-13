package me.bubbles.bosspve.entities.manager;

import net.minecraft.world.entity.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public interface IEntityBase {

    void spawn(Location location);
    void onEvent(Event event);
    List<ItemStack> getDrops();
    int getMoney();
    int getXp();
    int getDefaultHp();
    String uncoloredName();
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
