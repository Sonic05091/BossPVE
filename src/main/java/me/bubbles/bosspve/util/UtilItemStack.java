package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.Enchant;
import me.bubbles.bosspve.items.manager.Item;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class UtilItemStack {

    private ItemStack itemStack;
    private BossPVE plugin;

    public UtilItemStack(BossPVE plugin, ItemStack itemStack) {
        this.itemStack=itemStack;
    }

    @SuppressWarnings("deprecation")
    public List<String> getUpdatedLore(ItemStack itemStack) {
        List<String> lore=new ArrayList<>();
        for(Enchantment enchantment : itemStack.getEnchantments().keySet()) {
            String str = ChatColor.translateAlternateColorCodes('&',"&9" + enchantment.getName() + " " + itemStack.getItemMeta().getEnchantLevel(enchantment));
            lore.add(str);
        }
        return lore;
    }

    public List<String> getUpdatedLore() {
        return getUpdatedLore(itemStack);
    }

    public ItemStack enchantItem(ItemStack giver) {
        ItemStack receiver = itemStack.clone();

        ItemMeta receiverMeta = receiver.getItemMeta();
        ItemMeta giverMeta = giver.getItemMeta();
        if((!giverMeta.hasEnchants())&&(!giver.getType().equals(Material.ENCHANTED_BOOK))) {
            return receiver;
        }
        if(receiver.getType().equals(Material.ENCHANTED_BOOK)) {
            EnchantmentStorageMeta ems = (EnchantmentStorageMeta) receiverMeta;
            for(Enchantment enchantment : ems.getStoredEnchants().keySet()) {
                receiverMeta.addEnchant(enchantment,ems.getStoredEnchantLevel(enchantment),true);
                ems.removeStoredEnchant(enchantment);
            }
        }
        if(giver.getType().equals(Material.ENCHANTED_BOOK)) {
            EnchantmentStorageMeta ems = (EnchantmentStorageMeta) giverMeta;
            for(Enchantment enchantment : ems.getStoredEnchants().keySet()) {
                int receiverLvl = 0;
                if(receiverMeta!=null&&receiverMeta.hasEnchant(enchantment)) {
                    receiverLvl = receiverMeta.getEnchantLevel(enchantment);
                }
                int giverLvl = ems.getStoredEnchantLevel(enchantment);
                if(receiverLvl>giverLvl) {
                    continue;
                }
                if(receiverLvl==giverLvl&&giverLvl+1<=enchantment.getMaxLevel()) {
                    receiverMeta.addEnchant(enchantment, giverLvl+1, true);
                }else{
                    receiverMeta.addEnchant(enchantment, giverLvl, true);
                }
            }
        }
        for(Enchantment enchantment : giverMeta.getEnchants().keySet()) {
            int receiverLvl = 0;
            if(receiverMeta!=null&&receiverMeta.hasEnchant(enchantment)) {
                receiverLvl = receiverMeta.getEnchantLevel(enchantment);
            }
            int giverLvl = giverMeta.getEnchantLevel(enchantment);
            if(receiverLvl>giverLvl) {
                continue;
            }
            if(receiverLvl==giverLvl&&giverLvl+1<=enchantment.getMaxLevel()) {
                receiverMeta.addEnchant(enchantment, giverLvl+1, true);
            }else{
                receiverMeta.addEnchant(enchantment, giverLvl, true);
            }
        }
        receiverMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        receiver.setItemMeta(receiverMeta);
        receiverMeta.setLore(getUpdatedLore(receiver));
        receiver.setItemMeta(receiverMeta);
        return receiver;
    }

    public int calculateMoneyMultiplier() {
        if(!itemStack.hasItemMeta()) {
            return 1;
        }
        Item item = plugin.getItemManager().getItemFromStack(itemStack);
        if(item==null) {
            return 1;
        }
        if(!itemStack.getItemMeta().hasEnchants()) {
            return item.getBaseMoney();
        }
        int result=item.getBaseMoney();
        for(Enchant enchant : getCustomEnchants()) {
            result*=enchant.getMoneyMultiplier();
        }
        return result;
    }

    public int calculateXpMultiplier() {
        if(!itemStack.hasItemMeta()) {
            return 1;
        }
        Item item = plugin.getItemManager().getItemFromStack(itemStack);
        if(item==null) {
            return 1;
        }
        if(!itemStack.getItemMeta().hasEnchants()) {
            return item.getBaseXP();
        }
        int result=item.getBaseXP();
        for(Enchant enchant : getCustomEnchants()) {
            result*=enchant.getXpMultiplier();
        }
        return result;
    }

    public HashSet<Enchant> getCustomEnchants() {
        HashSet<Enchant> result=new HashSet<>();
        // probably unsafe but idc
        result.addAll((Collection<? extends Enchant>) itemStack.getItemMeta().getEnchants().keySet().stream()
                .filter(enchantment -> enchantment instanceof Enchant).collect(Collectors.toList()));
        return result;
    }

}
