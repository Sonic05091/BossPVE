package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntityBase;
import me.bubbles.bosspve.items.manager.enchant.Enchant;
import me.bubbles.bosspve.items.manager.enchant.EnchantItem;
import me.bubbles.bosspve.util.UtilChances;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Protector extends Ravager implements IEntityBase {


    private final String customName = ChatColor.translateAlternateColorCodes('&',"&8&lProtector");
    private BossPVE plugin;

    public Protector(BossPVE plugin) {
        this(plugin, ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getSpawnLocation());
    }

    public Protector(BossPVE plugin, Location location) {
        super(EntityType.RAVAGER, ((CraftWorld) plugin.getMultiverseCore().getMVWorldManager().getMVWorld(location.getWorld()).getCBWorld()).getHandle());
        this.plugin=plugin;
        expToDrop=0;
        setPos(location.getX(),location.getY(),location.getZ());
        setCustomNameVisible(true);
        setCustomName(Component.literal(ChatColor.translateAlternateColorCodes('&',customName)));
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(getDefaultHp());
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(getDamage());
        setHealth(getDefaultHp());
        goalSelector.addGoal(0, new MeleeAttackGoal(
                this, 1.0, false
        ));
        goalSelector.addGoal(1, new RandomLookAroundGoal(
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
        Entity entity = new Protector(plugin,location);
        ((CraftWorld) location.getWorld()).addEntityToWorld(entity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return entity;
    }

    @Override
    public List<ItemStack> getDrops() {
        List<ItemStack> result=new ArrayList<>();
        if(UtilChances.rollTheDice(1,400,1)) {
            result.add(plugin.getItemManager().getItemByName("damagerEnch").nmsAsItemStack());
        }
        if(UtilChances.rollTheDice(1,350,1)) {
            result.add(((EnchantItem)plugin.getItemManager().getItemByName("resistanceEnch")).getAtLevel(3));
        }
        return result;
    }

    @Override
    public double getMoney() {
        return 100;
    }

    @Override
    public int getXp() {
        return 20;
    }

    @Override
    public int getDefaultHp() {
        return 30;
    }

    @Override
    public int getDamage() {
        return 30;
    }

    @Override
    public String getUncoloredName() {
        return ChatColor.stripColor(customName);
    }

    @Override
    public String getNBTIdentifier() {
        return "protector";
    }

}