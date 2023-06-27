package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.items.manager.enchant.EnchantItem;
import me.bubbles.bosspve.util.UtilChances;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.monster.Vindicator;
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

public class Hellbringer extends Vindicator implements IEntity {

    private final String customName = ChatColor.translateAlternateColorCodes('&',"&c&lHellbringer");
    private BossPVE plugin;

    public Hellbringer(BossPVE plugin) {
        this(plugin, ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getSpawnLocation());
    }

    public Hellbringer(BossPVE plugin, Location location) {
        super(EntityType.VINDICATOR, ((CraftWorld) plugin.getMultiverseCore().getMVWorldManager().getMVWorld(location.getWorld()).getCBWorld()).getHandle());
        this.plugin=plugin;
        setPos(location.getX(),location.getY(),location.getZ());
        setCustomNameVisible(true);
        setCustomName(Component.literal(ChatColor.translateAlternateColorCodes('&',customName)));
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(getDefaultHp());
        setHealth(getDefaultHp());
        expToDrop=0;
        setItemInHand(InteractionHand.MAIN_HAND, CraftItemStack.asNMSCopy(new ItemStack(Material.IRON_AXE)));
        goalSelector.addGoal(0, new MeleeAttackGoal(
                this, 1, false
        ));
        goalSelector.addGoal(2, new PanicGoal(
                this, 1.5D
        ));
        goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(
                this, 0.6D
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
        Entity entity = new Hellbringer(plugin,location);
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> result=new ArrayList<>();
        if(UtilChances.rollTheDice(1,300,2)) {
            result.add(plugin.getItemManager().getItemByName("resistanceEnch").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,250,4)) {
            EnchantItem speedEnch = ((EnchantItem) plugin.getItemManager().getItemByName("speedEnch"));
            result.add(speedEnch.getAtLevel(2));
        }
        return result;
    }

    @Override
    public double getMoney() {
        return 10;
    }

    @Override
    public int getXp() {
        return 4;
    }

    @Override
    public int getDefaultHp() {
        return 10;
    }

    @Override
    public int getDamage() {
        return 10;
    }

    @Override
    public String getUncoloredName() {
        return ChatColor.stripColor(customName);
    }

    @Override
    public String getNBTIdentifier() {
        return "hellbringer";
    }

}
