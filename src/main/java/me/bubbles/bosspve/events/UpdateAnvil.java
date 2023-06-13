package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.util.UtilItemStack;
import org.bukkit.Material;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class UpdateAnvil extends Event {

    public UpdateAnvil(BossPVE plugin) {
        super(plugin, PrepareAnvilEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PrepareAnvilEvent e = (PrepareAnvilEvent) event;
        if(e.getInventory().getContents()[0]==null||e.getInventory().getContents()[1]==null) {
            return;
        }
        if(equals(e.getInventory().getContents()[0])&&e.getInventory().getContents()[1].getType().equals(Material.ENCHANTED_BOOK)) {
            e.setResult(null);
            return;
        }
        if(!(e.getInventory().getContents()[1].getType().equals(Material.ENCHANTED_BOOK))) {
            return;
        }
        ItemStack firstSlot = e.getInventory().getContents()[0];
        ItemStack secondSlot = e.getInventory().getContents()[1];
        if(firstSlot.getAmount()>1) {
            return;
        }
        if(secondSlot.getAmount()>1) {
            return;
        }
        UtilItemStack uiu = new UtilItemStack(plugin,firstSlot);
        ItemStack result = uiu.enchantItem(secondSlot);
        e.getInventory().setItem(2,result);
        result.setAmount(1);
        e.setResult(result);
    }

}
