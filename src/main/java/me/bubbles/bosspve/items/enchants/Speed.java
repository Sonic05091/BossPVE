package me.bubbles.bosspve.items.enchants;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.Enchant;
import me.bubbles.bosspve.items.manager.ItemManager;
import me.bubbles.bosspve.ticker.Timer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class Speed extends Enchant {

    private Timer timer;

    public Speed(ItemManager itemManager) {
        super(itemManager, "Speed", Material.FEATHER, 5);
        timer=new Timer(plugin,20);
        plugin.getTimerManager().addTimer(timer);
    }

    @Override
    public void onTick() {
        if(timer.isActive()) {
            return;
        }
        HashMap<Player, ItemStack> list = playersWithEnchantInAnyHand();
        list.forEach((player, itemStack) -> {
            PotionEffect speed = new PotionEffect(PotionEffectType.SPEED,40,itemStack.getItemMeta().getEnchantLevel(this)-1);
            player.addPotionEffect(speed);
        });
        timer.restart();
    }

}
