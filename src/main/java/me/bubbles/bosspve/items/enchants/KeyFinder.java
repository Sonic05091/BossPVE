package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.items.manager.enchant.Enchant;
import me.bubbles.bosspve.items.manager.Item;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.util.UtilChances;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KeyFinder extends Enchant {

    public KeyFinder(ItemManager itemManager) {
        super(itemManager, "KeyFinder", Material.TRIPWIRE_HOOK, 10);
        getEnchantItem().setDisplayName("&dKey Finder");
        allowedTypes.addAll(
                List.of(
                        Item.Type.WEAPON,
                        Item.Type.ENCHANT
                )
        );
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EntityDeathEvent) {
            EntityDeathEvent e = (EntityDeathEvent) event;
            if(e.getEntity().getKiller()==null) {
                return;
            }
            Player player = e.getEntity().getKiller();
            ItemStack main = player.getInventory().getItemInMainHand();
            if(!containsEnchant(main)) {
                return;
            }
            int level = main.getItemMeta().getEnchantLevel(this);
            double addition = level-1*(.25);
            if(UtilChances.rollTheDice(1,1000,3+addition)) {
                giveKey(player,"Stage",level);
            }
            if(UtilChances.rollTheDice(1,2000,1+addition)) {
                giveKey(player,"Enchant",level);
            }
            if(UtilChances.rollTheDice(1,10000,1+addition)) {
                giveKey(player,"Rank",1);
            }
        }
    }

    private void giveKey(Player player, String key, int amt) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"cc give v "+key+" "+amt+" "+player.getName());
    }

    @Override
    public String getDescription() {
        return "Chance of dropping keys when killing mobs";
    }

}
