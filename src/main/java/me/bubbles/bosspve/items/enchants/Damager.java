package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.items.manager.Item;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.items.manager.enchant.Enchant;
import org.bukkit.Material;

import java.util.List;

public class Damager extends Enchant {

    public Damager(ItemManager itemManager) {
        super(itemManager, "Damager", Material.SPIDER_EYE, 3);
        getEnchantItem().setDisplayName("&4&lDamager");
        allowedTypes.addAll(
                List.of(
                        Item.Type.WEAPON
                )
        );
    }

    @Override
    public double getDamageMultiplier(int level) {
        return level+1;
    }

    @Override
    public String getDescription() {
        return "Do more damage";
    }

}
