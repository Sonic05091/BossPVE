package me.bubbles.bosspve.items.manager.armor;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.Item;
import org.bukkit.Material;

public abstract class Armor extends Item implements IArmor {

    private ArmorSet armorSet;

    public Armor(BossPVE plugin, Material material, String nbtIdentifier, Type type) { // no armor set
        this(plugin, null, material, nbtIdentifier, type);
    }

    public Armor(BossPVE plugin, ArmorSet set, Material material, String nbtIdentifier, Type type) {
        super(plugin, material, nbtIdentifier, type);
    }

    @Override
    public ArmorSet getArmorSet() {
        return armorSet;
    }

}
