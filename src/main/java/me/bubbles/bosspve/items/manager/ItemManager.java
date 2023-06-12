package me.bubbles.bosspve.items.manager;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.stream.Collectors;

public class ItemManager {

    private BossPVE plugin;
    private HashSet<Item> items;

    public ItemManager(BossPVE plugin) {
        this.plugin = plugin;
        items=new HashSet<>();
        /*registerItem(
                new LifeBook(plugin),
                new ReviveBook(plugin,this)
        );*/
    }

    public void registerItem(Item... items) {
        for(Item item : items) {
            this.items.add(item);
            //item.onRegister();
            if(item.getRecipe()!=null) {
                Bukkit.addRecipe(item.getRecipe());
            }
        }
    }

    public Item getItemByName(String string) {
        for(Item item : items) {
            if(item.getNBTIdentifier().equalsIgnoreCase(string)) {
                return item;
            }
        }
        return null;
    }

    public HashSet<Item> getItems() {
        return items;
    }

    public void onEvent(Event event) {
        items.forEach(item -> item.onEvent(event));
    }

    public Item getItemFromStack(ItemStack itemStack) {
        // Get FIRST
        try {
            return items.stream().filter(item -> item.equals(itemStack)).collect(Collectors.toList()).get(0);
        } catch(IndexOutOfBoundsException e) {
            // u messed up if u get here
        }
        return null;
    }

}
