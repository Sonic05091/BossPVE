package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.items.manager.Item;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.items.manager.enchant.Enchant;
import me.bubbles.bosspve.stages.Stage;
import me.bubbles.bosspve.util.UtilChances;
import me.bubbles.bosspve.util.UtilPlayerMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Nuker extends Enchant {

    public Nuker(ItemManager itemManager) {
        super(itemManager, "nuker", Material.TNT, 5);
        getEnchantItem().setDisplayName("&c&lNuker");
        allowedTypes.addAll(
                List.of(
                        Item.Type.WEAPON
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
            if(!UtilChances.rollTheDice(1,1000,3+addition)) {
                return;
            }
            Stage stage = plugin.getStageManager().getStage(player.getLocation());
            stage.killAll(player);
            new UtilPlayerMessage(plugin,player).onProc(this);
        }
    }

    @Override
    public String getDescription() {
        return "Chance of killing all mobs in current stage";
    }

}
