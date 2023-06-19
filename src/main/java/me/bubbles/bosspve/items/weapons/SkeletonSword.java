package me.bubbles.bosspve.items.weapons;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.Item;
import me.bubbles.bosspve.util.UtilItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SkeletonSword extends Item {

    public SkeletonSword(BossPVE plugin) {
        super(plugin, Material.IRON_SWORD, "skeletonSword",Type.WEAPON);
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&7&lSimpleton's Sword"
        ));
        itemMeta.setLore(new UtilItemStack(plugin, itemStack).getUpdatedLore());
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    @Override
    public double getBaseDamage() {
        return 9.5;
    }

    @Override
    public String getDescription() {
        return "Simple, yet reliable sword from Simpletons";
    }

}
