package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntityBase;
import me.bubbles.bosspve.util.UtilChances;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.ZombieVillager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Ogre extends ZombieVillager implements IEntityBase {

    private final String customName = ChatColor.translateAlternateColorCodes('&',"&2&lOgre");
    private BossPVE plugin;

    public Ogre(BossPVE plugin) {
        this(plugin, ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getSpawnLocation());
    }

    public Ogre(BossPVE plugin, Location location) {
        super(EntityType.ZOMBIE_VILLAGER, ((CraftWorld) plugin.getMultiverseCore().getMVWorldManager().getMVWorld(location.getWorld()).getCBWorld()).getHandle());
        this.plugin=plugin;
        setPos(location.getX(),location.getY(),location.getZ());
        setCustomNameVisible(true);
        setCustomName(Component.literal(ChatColor.translateAlternateColorCodes('&',customName)));
        setHealth(getDefaultHp());
        goalSelector.addGoal(0, new MeleeAttackGoal(
                this, 1, false
        ));
        goalSelector.addGoal(1, new PanicGoal(
                this, 1.5D
        ));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(
                this, 0.6D
        ));
        goalSelector.addGoal(3, new RandomLookAroundGoal(
                this
        ));
        // AMOR
        setItemSlot(EquipmentSlot.FEET, plugin.getItemManager().getItemByName("ogreBoots").getNMSStack());
        setItemSlot(EquipmentSlot.LEGS, plugin.getItemManager().getItemByName("ogrePants").getNMSStack());
        setItemSlot(EquipmentSlot.CHEST, plugin.getItemManager().getItemByName("ogreChestplate").getNMSStack());
        setItemSlot(EquipmentSlot.HEAD, plugin.getItemManager().getItemByName("ogreHelmet").getNMSStack());
        if(getDrops()!=null) {
            drops.clear();
            drops.addAll(getDrops());
        }
        addTag(getNBTIdentifier());
    }

    @Override
    public Entity spawn(Location location) {
        Entity entity = new Ogre(plugin,location);
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> result=new ArrayList<>();
        if(UtilChances.rollTheDice(1,100,3)) {
            result.add(plugin.getItemManager().getItemByName("ogreBoots").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,100,3)) {
            result.add(plugin.getItemManager().getItemByName("ogrePants").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,100,3)) {
            result.add(plugin.getItemManager().getItemByName("ogreChestplate").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,100,3)) {
            result.add(plugin.getItemManager().getItemByName("ogreHelmet").nmsAsItemStack());
        }
        return result;
    }

    @Override
    public double getMoney() {
        return 2;
    }

    @Override
    public int getXp() {
        return 3;
    }

    @Override
    public int getDefaultHp() {
        return 10;
    }

    @Override
    public int getDamage() {
        return 12;
    }

    @Override
    public String getUncoloredName() {
        return ChatColor.stripColor(customName);
    }

    @Override
    public String getNBTIdentifier() {
        return "ogre";
    }

}
