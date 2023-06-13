package me.bubbles.bosspve.items.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.util.UtilItemStack;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantItem extends Item {

    private Enchant enchant;

    public EnchantItem(BossPVE plugin, Material material, Enchant enchant, String nbtIdentifier) {
        super(plugin, material, nbtIdentifier+"Ench");
        this.enchant=enchant;
        setDisplayName("&7&lEnchanted Item");
        ItemStack itemStack = nmsAsItemStack();
        itemStack.addUnsafeEnchantment(enchant,1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(new UtilItemStack(plugin,itemStack).getUpdatedLore());
        itemStack.setItemMeta(itemMeta);
        setNMSStack(itemStack);
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof PrepareAnvilEvent) {
            PrepareAnvilEvent e = (PrepareAnvilEvent) event;
            if(e.getInventory().getContents()[0]==null||e.getInventory().getContents()[1]==null) {
                return;
            }
            if(equals(e.getInventory().getContents()[0])&&e.getInventory().getContents()[1].getType().equals(Material.ENCHANTED_BOOK)) {
                e.setResult(null);
                return;
            }
            if(!equals(e.getInventory().getContents()[1])) {
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
        if(event instanceof InventoryClickEvent) {
            InventoryClickEvent e = (InventoryClickEvent) event;
            if(!(e.getClickedInventory() instanceof AnvilInventory)) {
                return;
            }
            if(!e.getSlotType().equals(InventoryType.SlotType.RESULT)) {
                return;
            }
            if(e.getWhoClicked().getInventory().firstEmpty()==-1) {
                return;
            }
            AnvilInventory inventory = (AnvilInventory) e.getClickedInventory();
            ItemStack firstSlot = inventory.getContents()[0];
            ItemStack secondSlot = inventory.getContents()[1];
            ItemStack thirdSlot = e.getCurrentItem();
            if(firstSlot==null||secondSlot==null||thirdSlot==null) {
                return;
            }
            if(!equals(secondSlot)) {
                return;
            }
            Player player = (Player) e.getWhoClicked();
            player.getInventory().addItem(thirdSlot);
            inventory.setItem(0,null);
            inventory.setItem(1,null);
            inventory.setItem(2,null);
        }

    }

}