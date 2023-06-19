package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.items.manager.Enchant;
import me.bubbles.bosspve.items.manager.Item;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.util.UtilUser;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class Throw extends Enchant {

    public Throw(ItemManager itemManager) {
        super(itemManager, "Throw", Material.LIGHTNING_ROD, 10, 450);
        allowedTypes.addAll(
                List.of(
                        Item.Type.WEAPON,
                        Item.Type.ENCHANT
                )
        );
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof PlayerInteractEntityEvent) {
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
            Vector up = new Vector(0,2*Math.min(level,4),0);
            entity.setVelocity(up);
            entity.setLastDamageCause(new EntityDamageByEntityEvent(player , entity, EntityDamageEvent.DamageCause.ENTITY_ATTACK, 1.5*level));
            restartCoolDown(player);
        }
    }

    @Override
    public String getDescription() {
        return "Launch your enemies!";
    }

}
