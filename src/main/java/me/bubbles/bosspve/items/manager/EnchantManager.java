package me.bubbles.bosspve.items.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.enchants.Speed;
import me.bubbles.bosspve.items.enchants.Telepathy;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.HashSet;

public class EnchantManager {

    private HashSet<Enchant> enchants;
    private BossPVE plugin;

    public EnchantManager(BossPVE plugin) {
        this.enchants=new HashSet<>();
        this.plugin=plugin;
        registerEnchants(
                new Telepathy(plugin),
                new Speed(plugin)
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

}
