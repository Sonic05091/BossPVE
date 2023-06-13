package me.bubbles.bosspve.items.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.enchants.Speed;
import me.bubbles.bosspve.items.enchants.Telepathy;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

public class EnchantManager {

    private HashSet<Enchant> enchants;
    private BossPVE plugin;

    public EnchantManager(ItemManager itemManager) {
        this.enchants=new HashSet<>();
        this.plugin=itemManager.plugin;
        registerEnchants(
                new Telepathy(itemManager),
                new Speed(itemManager)
        );
    }

    private void registerEnchants(Enchant... enchants) {
        Arrays.stream(enchants).forEach(enchant -> {
            this.enchants.add(enchant);
        });
    }

    public void onTick() {
        enchants.forEach(Enchant::onTick);
    }

    public void onEvent(Event event) {
        enchants.forEach(enchant -> enchant.onEvent(event));
    }

    public Enchant asCustomEnchant(Enchantment enchantment) {
        if(!enchants.contains(enchantment)) {
            return null;
        }
        Optional<Enchant> result = enchants.stream().filter(enchant -> enchant==enchantment).findFirst();
        return result.orElse(null);
    }


}
