package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.util.UtilItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UpdateLore extends Event {

    public UpdateLore(BossPVE plugin) {
        super(plugin, PlayerItemHeldEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerItemHeldEvent e = (PlayerItemHeldEvent) event;
        Player player = e.getPlayer();
        ItemStack itemStack = player.getInventory().getItem(e.getNewSlot());
        if(itemStack==null) {
            return;
        }
        if(itemStack.getType().equals(Material.AIR)) {
            return;
        }
        if(!itemStack.hasItemMeta()) {
            return;
        }
        UtilItemStack uis = new UtilItemStack(plugin, itemStack);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(uis.getUpdatedLoreForPlayer(e.getPlayer()));
        itemStack.setItemMeta(itemMeta);
        player.getInventory().setItem(e.getNewSlot(),itemStack);
    }

}
