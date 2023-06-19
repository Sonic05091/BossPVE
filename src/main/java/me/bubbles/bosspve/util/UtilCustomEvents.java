package me.bubbles.bosspve.util;

import com.google.common.util.concurrent.AtomicDouble;
import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntityBase;
import me.bubbles.bosspve.items.manager.Enchant;
import me.bubbles.bosspve.items.manager.IArmor;
import me.bubbles.bosspve.items.manager.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class UtilCustomEvents {

    private Event event;
    private BossPVE plugin;

    public UtilCustomEvents(BossPVE plugin, Event event) {
        this.plugin=plugin;
        this.event=event;
    }

    public void customEntityDeathEvent(IEntityBase entity) { // player kills mob
        if(!(event instanceof EntityDeathEvent)) {
            return;
        }
        EntityDeathEvent e = (EntityDeathEvent) event;
        if(e.getEntity().getKiller()==null) {
            e.getDrops().clear();
            return;
        }
        if(e.getEntity().getKiller().getPlayer()==null) {
            e.getDrops().clear();
            return;
        }
        if(!entity.hasSameTagAs(e.getEntity())) {
            return;
        }
        Player player = e.getEntity().getKiller();
        e.getDrops().clear();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        UtilItemStack uis = new UtilItemStack(plugin,itemStack);
        List<ItemStack> drops = entity.getDrops();
        if(drops.isEmpty()) {
            e.getDrops().addAll(drops);
            if(itemStack.hasItemMeta()) {
                if(itemStack.getItemMeta().hasEnchants()) {
                    boolean telepathy=false;
                    for(Enchant enchant : uis.getCustomEnchants()) {
                        if(enchant.getName().equalsIgnoreCase("telepathy")) {
                            telepathy=true;
                        }
                    }
                    if(telepathy) {
                        if(player.getInventory().firstEmpty()!=-1) {
                            e.getDrops().clear();
                            drops.forEach(drop -> player.getInventory().addItem(drop));
                        }
                    }
                }
            }
        }
        int xp=(int) uis.calculateXp(entity.getXp(),player);
        double money=uis.calculateMoney(entity.getMoney(),player);
        new UtilUser(plugin,player).giveXpAndMoney(xp,money,entity);
    }

    public void customEntityDamageByEntityEvent(IEntityBase entity) {
        if(!(event instanceof EntityDamageByEntityEvent)) {
            return;
        }
        EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event;
        if(!(e.getEntity() instanceof Player)) {
            return;
        }
        if(!entity.hasSameTagAs(e.getDamager())) {
            return;
        }
        double result = entity.getDamage();
        Player player = ((Player) e.getEntity()).getPlayer();
        if(player==null) {
            return;
        }
        result=getResultFromArmor(result,player);
        e.setDamage(result);
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
        ItemStack usedItem = player.getInventory().getItemInMainHand();
        UtilItemStack uis = new UtilItemStack(plugin,usedItem);
        double damage;
        Item item = plugin.getItemManager().getItemFromStack(usedItem);
        if(item==null) {
            damage=e.getDamage();
        } else {
            damage=item.getBaseDamage();
        }
        double result = uis.calculateDamage(damage,((Player) e.getDamager()).getPlayer());
        if(e.getEntity() instanceof Player) {
            result=getResultFromArmor(result,((Player) e.getEntity()));
        }
        e.setDamage(result);
    }

    private double getResultFromArmor(double init, Player player) {
        double result=init;
        for(ItemStack armor : player.getInventory().getArmorContents()) {
            Item armorItem = plugin.getItemManager().getItemFromStack(armor);
            if(armorItem==null) {
                continue;
            }
            if(armorItem.getType().equals(Item.Type.ARMOR)) {
                IArmor iArmor = (IArmor) armorItem;
                result-=iArmor.baseProtection();
                result*=iArmor.damageMultiplier();
            }
            if(!armor.hasItemMeta()) {
                continue;
            }
            if(!armor.getItemMeta().hasEnchants()) {
                continue;
            }
            UtilItemStack uis = new UtilItemStack(plugin,armor);
            AtomicDouble atomicDouble = new AtomicDouble(1);
            uis.getCustomEnchants().forEach(enchant -> atomicDouble.set(atomicDouble.get()*enchant.getDamageMultiplier(armor.getItemMeta().getEnchantLevel(enchant))));
            result*=atomicDouble.get();
            uis.getCustomEnchants().forEach(enchant -> atomicDouble.set(atomicDouble.get()*enchant.getDamageProtection(armor.getItemMeta().getEnchantLevel(enchant))));
            result-=atomicDouble.get();
        }
        return result;
    }

}
