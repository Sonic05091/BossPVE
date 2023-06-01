package me.bubbles.bosspve.users;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class User {

    private Player player;
    private BossPVE plugin;

    public User(Player player, BossPVE plugin) {
        this.player=player;
        this.plugin=plugin;
    }

    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    // GETTERS

    public Player getPlayer() {
        return player;
    }

}
