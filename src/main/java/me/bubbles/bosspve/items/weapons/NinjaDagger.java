package me.bubbles.bosspve.items.weapons;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.Item;
import me.bubbles.bosspve.items.manager.bases.weapon.IWeapon;
import me.bubbles.bosspve.util.UtilItemStack;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NinjaDagger extends Item implements IWeapon {

    public NinjaDagger(BossPVE plugin) {
        super(plugin, Material.SHEARS, "ninjaDagger",Type.WEAPON);
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&8&lNinja's Dagger"
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
        return 45;
    }

    @Override
    public int getBaseXP() {
        return 4;
    }

    @Override
    public double getBaseMoney() {
        return 5;
    }

    @Override
    public String getDescription() {
        return "From the fallen ninjas";
    }

}
