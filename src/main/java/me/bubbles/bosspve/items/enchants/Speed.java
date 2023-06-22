package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.items.manager.enchant.Enchant;
import me.bubbles.bosspve.items.manager.Item;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.ticker.Timer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;

public class Speed extends Enchant {

    private Timer timer;

    public Speed(ItemManager itemManager) {
        super(itemManager, "Speed", Material.FEATHER, 15);
        getEnchantItem().setDisplayName("&fSpeed");
        timer=new Timer(plugin,20);
        plugin.getTimerManager().addTimer(timer);
        allowedTypes.addAll(
                List.of(
                        Item.Type.WEAPON,
                        Item.Type.ENCHANT
                )
        );
    }

    @Override
    public void onTick() {
        if(timer.isActive()) {
            return;
        }
        HashMap<Player, ItemStack> list = playersWithEnchantInAnyHand();
        list.forEach((player, itemStack) -> {
            if(!allowUsage(player)) {
                return;
            }
            PotionEffect speed = new PotionEffect(PotionEffectType.SPEED,40,itemStack.getItemMeta().getEnchantLevel(this)-1);
            player.addPotionEffect(speed);
        });
        timer.restart();
    }

    @Override
    public String getDescription() {
        return "Gives you speed";
    }

}
