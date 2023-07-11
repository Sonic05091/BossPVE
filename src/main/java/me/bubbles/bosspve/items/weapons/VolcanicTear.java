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

public class VolcanicTear extends Item implements IWeapon {

    public VolcanicTear(BossPVE plugin) {
        super(plugin, Material.MAGMA_CREAM, "volcanicTear");
        ItemStack itemStack = nmsAsItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',
                "&6&lVolcanic Tear"
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
        return 35;
    }

    @Override
    public int getBaseXP() {
        return 3;
    }

    @Override
    public double getBaseMoney() {
        return 2;
    }

    @Override
    public Type getType() {
        return Type.WEAPON;
    }

    @Override
    public String getDescription() {
        return "From the tears of a Volcono";
    }

}
