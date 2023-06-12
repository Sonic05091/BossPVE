package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.Enchant;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Container;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class Telepathy extends Enchant {

    public Telepathy(BossPVE plugin) {
        super(plugin, "Telepathy", Material.BOOK, 1);
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if(event instanceof BlockBreakEvent) {
            BlockBreakEvent e = (BlockBreakEvent) event;
            if(!containsEnchant(e.getPlayer().getInventory().getItemInMainHand())) {
                return;
            }
            Player player = e.getPlayer();
            if(player.getGameMode().equals(GameMode.CREATIVE)||player.getGameMode().equals(GameMode.SPECTATOR)) {
                return;
            }
            if(e.getPlayer().getInventory().firstEmpty()==-1) {
                return;
            }
            if(e.getBlock().getState() instanceof Container) {
                return;
            }
            boolean silkTouch=false;
            if(e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                silkTouch=true;
            }
            e.setDropItems(false);
            Collection<ItemStack> drops = new ArrayList<>();
            if(silkTouch) {
                drops.add(new ItemStack(e.getBlock().getType()));
            } else {
                drops.addAll(e.getBlock().getDrops());
            }
            if(drops.isEmpty()&&!silkTouch) {
                return;
            }
            player.getInventory().addItem(drops.iterator().next());
        }
        /*if(event instanceof EntityDeathEvent) {
            EntityDeathEvent e = (EntityDeathEvent) event;
            if(e.getEntity().getKiller()==null) {
                return;
            }
            Player player = e.getEntity().getKiller();
            if(player.getInventory().firstEmpty()==-1) {
                return;
            }
            Collection<ItemStack> drops = e.getDrops();
            if(drops.isEmpty()) {
                return;
            }
            e.getDrops().clear();
            player.getInventory().addItem(drops.iterator().next());
        }*/
    }

}
