package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.entity.Player;

public class UtilPlayerMessage {

    private Player player;
    private BossPVE plugin;

    public UtilPlayerMessage(BossPVE plugin, Player player) {
        this.player=player;
        this.plugin=plugin;
    }

    public void sendMessage(String string) {
        player.sendMessage(new UtilString(plugin).colorFillPlaceholders(string));
    }


}
