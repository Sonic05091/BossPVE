package me.bubbles.bosspve.entities;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntityBase;
import me.bubbles.bosspve.util.UtilCustomEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.player.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Simpleton extends Skeleton implements IEntityBase {

    private final String customName = ChatColor.translateAlternateColorCodes('&',"&7&lSimpleton");

    private BossPVE plugin;

    public Simpleton(BossPVE plugin) {
        this(plugin, ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle().getWorld().getSpawnLocation());
    }

    public Simpleton(BossPVE plugin, Location location) {
        super(EntityType.SKELETON, ((CraftWorld) location.getWorld()).getHandle());
        setPos(location.getX(),location.getY(),location.getZ());
        setCustomNameVisible(true);
        setCustomName(Component.literal(ChatColor.translateAlternateColorCodes('&',customName)));
        setHealth(getHealth());
        goalSelector.addGoal(0, new AvoidEntityGoal<Player>(
                this, Player.class, 5, 1D, 1.5D
        ));
        goalSelector.addGoal(1, new PanicGoal(
                this, 2.0D
        ));
        goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(
                this, 0.6D
        ));
        goalSelector.addGoal(3, new RandomLookAroundGoal(
                this
        ));
        expToDrop=0;
        addTag(getNBTIdentifier());
        if(this.plugin==null) {
            this.plugin=plugin;
        }
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EntityDeathEvent) {
            UtilCustomEvents uce = new UtilCustomEvents(plugin,event);
            uce.CustomEntityDeathEvent(this);
        }
    }

    @Override
    public void spawn(Location location) {
        ((CraftWorld) location.getWorld()).addEntityToWorld(new Simpleton(this.plugin,location), CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

    @Override
    public List<ItemStack> getDrops() {
        return null;
    }

    @Override
    public int getMoney() {
        return 0;
    }

    @Override
    public int getXp() {
        return 0;
    }

    @Override
    public int getDefaultHp() {
        return 30;
    }

    @Override
    public String uncoloredName() {
        return ChatColor.stripColor(customName);
    }

    @Override
    public String getNBTIdentifier() {
        return "simpleton";
    }

}