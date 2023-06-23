package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UtilSender {

    private CommandSender sender;
    private BossPVE plugin;

    public UtilSender(BossPVE plugin, CommandSender sender) {
        this.plugin=plugin;
        this.sender=sender;
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public CommandSender getSender() {
        return sender;
    }

    public void sendMessage(String message) {
        if(isPlayer()) {
            getPlayer().sendMessage(new UtilString(plugin).colorFillPlaceholders(message));
            return;
        }
        Bukkit.getConsoleSender().sendMessage(new UtilString(plugin).colorFillPlaceholders(message.replace("\n","\n&f")));
    }

    public Player getPlayer() {
        if(!isPlayer()){
            return null;
        }
        return (Player) sender;
    }

    public boolean hasPermission(String permission) {
        if(!isPlayer()) {
            return true;
        }
        return getPlayer().hasPermission(permission);
    }

    public UtilUserData getUserData() {
        if(!isPlayer()) {
            return null;
        }
        return plugin.getMySQL().getData(getPlayer().getUniqueId());
    }

}
