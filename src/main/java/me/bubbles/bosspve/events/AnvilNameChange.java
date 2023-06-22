package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.items.manager.Item;
import me.bubbles.bosspve.util.UtilItemStack;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilNameChange extends Event {

    public AnvilNameChange(BossPVE plugin) {
        super(plugin, PrepareAnvilEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PrepareAnvilEvent e = (PrepareAnvilEvent) event;
        ItemStack firstSlot = e.getInventory().getContents()[0];
        ItemStack secondSlot = e.getInventory().getContents()[1];
        ItemStack thirdSlot = e.getResult();
        if(firstSlot==null||secondSlot!=null) {
            return;
        }
        if(!firstSlot.hasItemMeta()) {
            return;
        }
        if(!firstSlot.getItemMeta().hasDisplayName()) {
            return;
        }
        if(thirdSlot==null) {
            return;
        }
        if(!thirdSlot.hasItemMeta()) {
            return;
        }
        if(!thirdSlot.getItemMeta().hasDisplayName()) {
            e.setResult(firstSlot);
            return;
        }
        if(firstSlot.getItemMeta().getDisplayName().equals(thirdSlot.getItemMeta().getDisplayName())) {
            return;
        }
        ItemMeta resultMeta = firstSlot.getItemMeta();
        resultMeta.setDisplayName(firstSlot.getItemMeta().getDisplayName());
        thirdSlot.setItemMeta(resultMeta);
        UtilItemStack uis = new UtilItemStack(plugin,thirdSlot);
        e.setResult(uis.enchantItem(firstSlot));
    }

}
