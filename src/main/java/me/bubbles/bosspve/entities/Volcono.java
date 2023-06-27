package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.util.UtilChances;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Volcono extends MagmaCube implements IEntity {


    private final String customName = ChatColor.translateAlternateColorCodes('&',"&6&lVolcono");
    private BossPVE plugin;

    public Volcono(BossPVE plugin) {
        this(plugin, ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getSpawnLocation());
    }

    public Volcono(BossPVE plugin, Location location) {
        super(EntityType.MAGMA_CUBE, ((CraftWorld) plugin.getMultiverseCore().getMVWorldManager().getMVWorld(location.getWorld()).getCBWorld()).getHandle());
        this.plugin=plugin;
        expToDrop=0;
        setPos(location.getX(),location.getY(),location.getZ());
        setCustomNameVisible(true);
        setCustomName(Component.literal(ChatColor.translateAlternateColorCodes('&',customName)));
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(getDefaultHp());
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(getDamage());
        setHealth(getDefaultHp());
        setSize(5,false);
        goalSelector.addGoal(0, new NearestAttackableTargetGoal<>(
                this, Player.class, false
        ));
        goalSelector.addGoal(4, new RandomLookAroundGoal(
                this
        ));
        if(getDrops()!=null) {
            drops.clear();
            drops.addAll(getDrops());
        }
        addTag(getNBTIdentifier());
    }

    @Override
    public Entity spawn(Location location) {
        Entity entity = new Volcono(plugin,location);
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> result=new ArrayList<>();
        if(UtilChances.rollTheDice(1,600,1)) {
            result.add(plugin.getItemManager().getItemByName("volcanictear").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,300,1)) {
            result.add(plugin.getItemManager().getItemByName("volcanicHelmet").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,400,1)) {
            result.add(plugin.getItemManager().getItemByName("volcanicChestplate").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,350,1)) {
            result.add(plugin.getItemManager().getItemByName("volcanicPants").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,300,1)) {
            result.add(plugin.getItemManager().getItemByName("volcanicBoots").nmsAsItemStack());
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
        return 30;
    }

    @Override
    public int getDamage() {
        return 23;
    }

    @Override
    public String getUncoloredName() {
        return ChatColor.stripColor(customName);
    }

    @Override
    public String getNBTIdentifier() {
        return "volcono";
    }

}