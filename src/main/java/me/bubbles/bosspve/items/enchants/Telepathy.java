package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.items.manager.Enchant;
import me.bubbles.bosspve.items.manager.ItemManager;
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

    public Telepathy(ItemManager itemManager) {
        super(itemManager, "Telepathy", Material.BOOK, 1);
        getEnchantItem().setDisplayName("&1Telepathy");
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
            if(!allowUsage(player)) {
                return;
            }
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
    }

}
