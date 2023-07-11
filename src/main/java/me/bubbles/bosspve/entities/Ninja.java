package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.util.UtilChances;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Skeleton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Ninja extends Skeleton implements IEntity {

    private final String customName = ChatColor.translateAlternateColorCodes('&',"&8&lNinja");

    private BossPVE plugin;

    public Ninja(BossPVE plugin) {
        this(plugin, ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getSpawnLocation());
    }

    public Ninja(BossPVE plugin, Location location) {
        super(EntityType.SKELETON, ((CraftWorld) plugin.getMultiverseCore().getMVWorldManager().getMVWorld(location.getWorld()).getCBWorld()).getHandle());
        this.plugin=plugin;
        setPos(location.getX(),location.getY(),location.getZ());
        setCustomNameVisible(true);
        setCustomName(Component.literal(ChatColor.translateAlternateColorCodes('&',customName)));
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(getDefaultHp());
        setHealth(getDefaultHp());
        expToDrop=0;
        goalSelector.addGoal(0, new RangedBowAttackGoal<>(
                this, 1, 1, 5
        ));
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
        setItemInHand(InteractionHand.MAIN_HAND,CraftItemStack.asNMSCopy(new ItemStack(Material.SHEARS)));
        setItemInHand(InteractionHand.OFF_HAND,CraftItemStack.asNMSCopy(new ItemStack(Material.SHEARS)));
        setItemSlot(EquipmentSlot.FEET, plugin.getItemManager().getItemByName("ninjaBoots").getNMSStack());
        setItemSlot(EquipmentSlot.LEGS, plugin.getItemManager().getItemByName("ninjaPants").getNMSStack());
        setItemSlot(EquipmentSlot.CHEST, plugin.getItemManager().getItemByName("ninjaChestplate").getNMSStack());
        setItemSlot(EquipmentSlot.HEAD, plugin.getItemManager().getItemByName("ninjaHelmet").getNMSStack());
        if(getDrops()!=null) {
            drops.clear();
            drops.addAll(getDrops());
        }
        addTag(getNBTIdentifier());
    }

    @Override
    public Entity spawn(Location location) {
        Entity entity = new Ninja(plugin,location);
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> result=new ArrayList<>();
        // ENCHANTS
        if(UtilChances.rollTheDice(1,100,2)) {
            result.add(plugin.getItemManager().getItemByName("bankerEnch").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,100,2)) {
            result.add(plugin.getItemManager().getItemByName("grinderEnch").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,1000,2)) {
            result.add(plugin.getItemManager().getItemByName("nukerEnch").nmsAsItemStack());
        }
        // NINJA SET
        if(UtilChances.rollTheDice(1,800,1)) {
            result.add(plugin.getItemManager().getItemByName("ninjaDagger").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,400,1)) {
            result.add(plugin.getItemManager().getItemByName("ninjaHelmet").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,500,1)) {
            result.add(plugin.getItemManager().getItemByName("ninjaChestplate").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,450,1)) {
            result.add(plugin.getItemManager().getItemByName("ninjaPants").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,400,1)) {
            result.add(plugin.getItemManager().getItemByName("ninjaBoots").nmsAsItemStack());
        }
        return result;
    }

    @Override
    public double getMoney() {
        return 15;
    }

    @Override
    public int getXp() {
        return 6;
    }

    @Override
    public int getDefaultHp() {
        return 40;
    }

    @Override
    public String getNBTIdentifier() {
        return "ninja";
    }

    @Override
    public String getShowName() {
        return customName;
    }

    @Override
    public int getDamage() {
        return 30;
    }

}