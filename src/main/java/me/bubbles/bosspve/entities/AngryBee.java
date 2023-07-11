package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntity;
import me.bubbles.bosspve.util.UtilChances;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
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

public class AngryBee extends Bee implements IEntity {

    private final String customName = ChatColor.translateAlternateColorCodes('&',"&e&lAngry Bee");
    private BossPVE plugin;

    public AngryBee(BossPVE plugin) {
        this(plugin, ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getSpawnLocation());
    }

    public AngryBee(BossPVE plugin, Location location) {
        super(EntityType.BEE, ((CraftWorld) plugin.getMultiverseCore().getMVWorldManager().getMVWorld(location.getWorld()).getCBWorld()).getHandle());
        this.plugin=plugin;
        setPos(location.getX(),location.getY(),location.getZ());
        setCustomNameVisible(true);
        expToDrop=0;
        setCustomName(Component.literal(ChatColor.translateAlternateColorCodes('&',customName)));
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(getDefaultHp());
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(getDamage());
        setHealth(getDefaultHp());
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
        setAggressive(true);
        if(getDrops()!=null) {
            drops.clear();
            drops.addAll(getDrops());
        }
        addTag(getNBTIdentifier());
    }


    @Override
    public Entity spawn(Location location) {
        Entity entity = new AngryBee(plugin,location);
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> result=new ArrayList<>();
        if(UtilChances.rollTheDice(1,800,1)) {
            result.add(plugin.getItemManager().getItemByName("damagerEnch").nmsAsItemStack());
        }
        // BEE SET
        if(UtilChances.rollTheDice(1,900,1)) {
            result.add(plugin.getItemManager().getItemByName("beeStinger").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,500,1)) {
            result.add(plugin.getItemManager().getItemByName("beeHelmet").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,700,1)) {
            result.add(plugin.getItemManager().getItemByName("beeChestplate").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,650,1)) {
            result.add(plugin.getItemManager().getItemByName("beePants").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,500,1)) {
            result.add(plugin.getItemManager().getItemByName("beeBoots").nmsAsItemStack());
        }
        return result;
    }

    @Override
    public double getMoney() {
        return 17;
    }

    @Override
    public int getXp() {
        return 13;
    }

    @Override
    public int getDefaultHp() {
        return 50;
    }

    @Override
    public int getDamage() {
        return 40;
    }

    @Override
    public String getShowName() {
        return customName;
    }

    @Override
    public String getNBTIdentifier() {
        return "angrybee";
    }
}
