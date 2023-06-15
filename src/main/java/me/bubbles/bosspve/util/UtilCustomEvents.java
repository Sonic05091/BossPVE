package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntityBase;
import me.bubbles.bosspve.items.manager.Enchant;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class UtilCustomEvents {

    private Event event;
    private BossPVE plugin;

    public UtilCustomEvents(BossPVE plugin, Event event) {
        this.plugin=plugin;
        this.event=event;
    }

    public void customEntityDeathEvent(IEntityBase entity) {
        if(!(event instanceof EntityDeathEvent)) {
            return;
        }
        EntityDeathEvent e = (EntityDeathEvent) event;
        if(e.getEntity().getKiller()==null) {
            return;
        }
        if(e.getEntity().getKiller().getPlayer()==null) {
            return;
        }
        if(!entity.hasSameTagAs(e.getEntity())) {
            return;
        }
        Player player = e.getEntity().getKiller();
        e.getDrops().clear();
        if(entity.getDrops()!=null) {
            e.getDrops().addAll(entity.getDrops());
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if(!itemStack.hasItemMeta()) {
                return;
            }
            if(!itemStack.getItemMeta().hasEnchants()) {
                return;
            }
            boolean telepathy=false;
            for(Enchantment enchantment : itemStack.getItemMeta().getEnchants().keySet()) {
                Enchant enchant = plugin.getItemManager().getEnchantManager().asCustomEnchant(enchantment);
                if(enchant!=null) {
                    if(enchant.getName().equalsIgnoreCase("telepathy")) {
                        telepathy=true;
                    }
                }
            }
            if(telepathy) {
                if(player.getInventory().firstEmpty()!=-1) {
                    e.getDrops().clear();
                    entity.getDrops().forEach(drop -> player.getInventory().addItem(drop));
                }
            }
        }
        UtilItemStack uis = new UtilItemStack(plugin,player.getInventory().getItemInMainHand());
        int xp=(int) uis.calculateXp(entity.getXp(),player);
        double money=uis.calculateMoney(entity.getMoney(),player);
        new UtilUser(plugin,player).giveXpAndMoney(xp,money,entity);
    }

    public void customEntityDamageByEntityEvent(IEntityBase entity) {
        if(!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(e.getEntity() instanceof Player) {
            return;
        }
        if(!(e.getDamager() instanceof Player)) {
            return;
        }
        if(!entity.hasSameTagAs(e.getEntity())) {
            return;
        }
        e.setDamage(entity.getDamage());
    }

    public void customEntityDamageByEntityEvent() {
        if(!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getDamager() instanceof Player)) {
            return;
        }
        Player player = ((Player) e.getDamager()).getPlayer();
        if(player==null) {
            return;
        }
        UtilItemStack uis = new UtilItemStack(plugin,player.getInventory().getItemInMainHand());
        double result = uis.calculateDamage(e.getDamage(),((Player) e.getDamager()).getPlayer());
        e.setDamage(result);
    }

}
