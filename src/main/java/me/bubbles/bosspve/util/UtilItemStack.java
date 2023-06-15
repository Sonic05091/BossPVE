package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.Enchant;
import me.bubbles.bosspve.items.manager.Item;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UtilItemStack {

    private ItemStack itemStack;
    private BossPVE plugin;

    public UtilItemStack(BossPVE plugin, ItemStack itemStack) {
        this.plugin=plugin;
        this.itemStack=itemStack;
    }

    @SuppressWarnings("deprecation")
    public List<String> getUpdatedLore(ItemStack itemStack) {
        List<String> lore=new ArrayList<>();
        for(Enchantment enchantment : itemStack.getEnchantments().keySet()) {
            lore.add(ChatColor.translateAlternateColorCodes('&',
                    "&9" + enchantment.getName() + " " + itemStack.getItemMeta().getEnchantLevel(enchantment)
            ));
        }
        return lore;
    }

    public List<String> getUpdatedLoreForPlayer(ItemStack itemStack, Player player) {
        int dmg=(int) calculateDamage(1,player)-1;
        int xp=(int) calculateXp(1,player);
        int money=(int) calculateMoney(1,player);
        List<String> lore = new ArrayList<>(getUpdatedLore(itemStack));
        Item item = plugin.getItemManager().getItemFromStack(itemStack);
        if(item!=null) {
            if((!item.getDescription().equals(""))&&item.getDescription()!=null) {
                lore.add(item.getDescription());
            }
            if(item.getType().equals(Item.Type.WEAPON)) {
                lore.add(ChatColor.translateAlternateColorCodes('&',
                        "&8Base Damage:&7 "+dmg
                ));
                lore.add(ChatColor.translateAlternateColorCodes('&',
                        "&8Base XP:&7 "+xp
                ));
                lore.add(ChatColor.translateAlternateColorCodes('&',
                        "&8Base Money:&7 $"+money
                ));
            }
        }
        return lore;
    }

    public List<String> getUpdatedLoreForPlayer(Player player) {
        return getUpdatedLoreForPlayer(itemStack,player);
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

    public double calculateMoney(double init, Player player) {
        double result=init;
        Stage stage = plugin.getStageManager().getStage(player.getLocation());
        if(stage!=null) {
            if(stage.isAllowed(player)) {
                result*=stage.getXpMultiplier();
            }
        }
        if(!itemStack.hasItemMeta()) {
            return result;
        }
        Item item = plugin.getItemManager().getItemFromStack(itemStack);
        if(item==null) {
            if(itemStack.getItemMeta().hasEnchants()) {
                for(Enchant enchant : getCustomEnchants()) {
                    if(enchant.allowUsage(player)) {
                        result*=enchant.getMoneyMultiplier(itemStack.getItemMeta().getEnchantLevel(enchant));
                    }
                }
            }
            return result;
        }
        if(!item.allowUsage(player)) {
            return result;
        }
        result+=item.getBaseMoney();
        if(itemStack.getItemMeta().hasEnchants()) {
            for(Enchant enchant : getCustomEnchants()) {
                if(enchant.allowUsage(player)) {
                    result*=enchant.getMoneyMultiplier(itemStack.getItemMeta().getEnchantLevel(enchant));
                }
            }
        }
        return result;
    }

    public double calculateXp(double init, Player player) {
        double result=init;
        Stage stage = plugin.getStageManager().getStage(player.getLocation());
        if(stage!=null) {
            if(stage.isAllowed(player)) {
                result*=stage.getXpMultiplier();
            }
        }
        if(!itemStack.hasItemMeta()) {
            return result;
        }
        Item item = plugin.getItemManager().getItemFromStack(itemStack);
        if(item==null) {
            if(itemStack.getItemMeta().hasEnchants()) {
                for(Enchant enchant : getCustomEnchants()) {
                    if(enchant.allowUsage(player)) {
                        result*=enchant.getXpMultiplier(itemStack.getItemMeta().getEnchantLevel(enchant));
                    }
                }
            }
            return result;
        }
        if(!item.allowUsage(player)) {
            return result;
        }
        result+=item.getBaseXP();
        if(itemStack.getItemMeta().hasEnchants()) {
            for(Enchant enchant : getCustomEnchants()) {
                if(enchant.allowUsage(player)) {
                    result*=enchant.getXpMultiplier(itemStack.getItemMeta().getEnchantLevel(enchant));
                }
            }
        }
        return result;
    }

    public double calculateDamage(double init, Player player) {
        double result=init;
        if(!itemStack.hasItemMeta()) {
            return init;
        }
        Item item = plugin.getItemManager().getItemFromStack(itemStack);
        if(item==null) {
            if(itemStack.getItemMeta().hasEnchants()) {
                for(Enchant enchant : getCustomEnchants()) {
                    if(enchant.allowUsage(player)) {
                        result*=enchant.getDamageMultiplier(itemStack.getItemMeta().getEnchantLevel(enchant));
                    }
                }
            }
            return result;
        }
        if(!item.allowUsage(player)) {
            return result;
        }
        result+=item.getBaseDamage();
        if(itemStack.getItemMeta().hasEnchants()) {
            for(Enchant enchant : getCustomEnchants()) {
                if(enchant.allowUsage(player)) {
                    result*=enchant.getDamageMultiplier(itemStack.getItemMeta().getEnchantLevel(enchant));
                }
            }
        }
        return result;
    }

    public HashSet<Enchant> getCustomEnchants() {
        // probably unsafe but idc
        HashSet<Enchant> result=new HashSet<>();
        itemStack.getItemMeta().getEnchants().keySet().stream()
                .filter(enchantment -> enchantment instanceof Enchant).collect(Collectors.toList()).forEach(enchantment -> result.add((Enchant) enchantment));
        return result;
    }

}
