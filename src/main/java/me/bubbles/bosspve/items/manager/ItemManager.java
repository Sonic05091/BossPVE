package me.bubbles.bosspve.items.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.armor.oger.OgreBoots;
import me.bubbles.bosspve.items.armor.oger.OgreChestplate;
import me.bubbles.bosspve.items.armor.oger.OgreHelmet;
import me.bubbles.bosspve.items.armor.oger.OgrePants;
import me.bubbles.bosspve.items.weapons.SkeletonSword;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemManager {

    public BossPVE plugin;
    private HashSet<Item> items;
    private EnchantManager enchantManager;

    public ItemManager(BossPVE plugin) {
        this.plugin = plugin;
        items=new HashSet<>();
        enchantManager=new EnchantManager(this);
        registerItem(
                new SkeletonSword(plugin),
                new OgreBoots(plugin),
                new OgrePants(plugin),
                new OgreChestplate(plugin),
                new OgreHelmet(plugin)
        );
    }

    public EnchantManager getEnchantManager() {
        return enchantManager;
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
        Optional<Item> optItem = items.stream().filter(item -> item.getNBTIdentifier().equalsIgnoreCase(string)).findFirst();
        return optItem.orElse(null);
    }

    public HashSet<Item> getItems() {
        return items;
    }

    public void onTick() {
        enchantManager.onTick();
    }

    public void onEvent(Event event) {
        items.forEach(item -> item.onEvent(event));
        enchantManager.onEvent(event);
    }

    public Item getItemFromStack(ItemStack itemStack) {
        // Get FIRST
        Optional<Item> optItem = items.stream().filter(item -> item.equals(itemStack)).collect(Collectors.toList()).stream().findFirst();
        return optItem.orElse(null);
    }

}
