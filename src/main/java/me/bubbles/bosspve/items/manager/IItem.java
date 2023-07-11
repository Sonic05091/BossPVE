package me.bubbles.bosspve.items.manager;

import org.bukkit.inventory.ShapedRecipe;

public interface IItem {

    Item.Type getType();

    default int getLevelRequirement() {
        return -1;
    }

    default ShapedRecipe getRecipe() {
        return null;
    }

    default String getDescription() {
        return "";
    }

}
