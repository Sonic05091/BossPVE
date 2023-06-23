package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.items.manager.enchant.Enchant;
import me.bubbles.bosspve.items.manager.Item;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.util.UtilChances;
import me.bubbles.bosspve.util.UtilPlayerMessage;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class Throw extends Enchant {

    public Throw(ItemManager itemManager) {
        super(itemManager, "Throw", Material.LIGHTNING_ROD, 10);
        getEnchantItem().setDisplayName("&c&lThrow");
        allowedTypes.addAll(
                List.of(
                        Item.Type.WEAPON
                )
        );
    }

    @Override
    public void onEvent(Event event) {
        /*if(event instanceof PlayerInteractEntityEvent) {
            PlayerInteractEntityEvent e = (PlayerInteractEntityEvent) event;
            Player player = e.getPlayer().getPlayer();
            ItemStack itemStack = e.getPlayer().getInventory().getItemInMainHand();
            if(e.getHand().equals(EquipmentSlot.OFF_HAND)) {
                return;
            }
            if(!containsEnchant(itemStack)) {
                return;
            }
            UtilUser uu = new UtilUser(plugin,player);
            if(player==null) {
                return;
            }
            int level = itemStack.getItemMeta().getEnchantLevel(this);
            if(level==0) {
                return;
            }
            if(plugin.getStageManager().inAStage(player)&&(e.getRightClicked() instanceof Player)) {
                return;
            }
            if(isOnCoolDown(player)) {
                uu.sendMessage("%prefix% %primary%You're on cooldown! %secondary%"+getCoolDown(player)/20+"s%primary% remaining.");
                return;
            }
            Entity entity = e.getRightClicked();
            Vector up = new Vector(entity.getVelocity().getX(),2*Math.min(level,4),entity.getVelocity().getZ());
            entity.setVelocity(up);
            entity.setLastDamageCause(new EntityDamageByEntityEvent(player , entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 1.5*level));
            restartCoolDown(player);
        }*/
        if(event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
            if(!(e.getDamager() instanceof Player)) {
                return;
            }
            Player player = ((Player) e.getDamager());
            ItemStack main = player.getInventory().getItemInMainHand();
            if(!containsEnchant(main)) {
                return;
            }
            int level = main.getItemMeta().getEnchantLevel(this);
            double addition = level-1*(.25);
            if(!UtilChances.rollTheDice(1,100,3+addition)) {
                return;
            }
            Entity entity = e.getEntity();
            Vector up = new Vector(entity.getVelocity().getX(),2*Math.min(level,4),entity.getVelocity().getZ());
            entity.setVelocity(up);
            entity.setLastDamageCause(new EntityDamageByEntityEvent(player , entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 1.5*level));
            restartCoolDown(player);
            new UtilPlayerMessage(plugin,player).onProc(this);
        }
    }

    @Override
    public String getDescription() {
        return "Launch your enemies!";
    }

}
