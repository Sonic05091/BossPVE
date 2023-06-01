package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.users.User;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UtilMessage {

    private CommandSender sender;
    private BossPVE plugin;

    public UtilMessage(BossPVE plugin, CommandSender sender) {
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
            User user = plugin.getUserManager().getUser((Player) sender);
            user.sendMessage(colorFillPlaceholders(message));
            return;
        }
        plugin.getLogger().warning(fillPlaceholders(message));
    }

    public String colorFillPlaceholders(String message) {
        return ChatColor.translateAlternateColorCodes('&',message
                .replace("%prefix%", Messages.PREFIX.getValue())
                .replace("%primary%", Messages.PRIMARY_COLOR.getValue())
                .replace("%secondary%", Messages.SECONDARY_COLOR.getValue()));
    }

    public String fillPlaceholders(String message) {
        return message
                .replace("%prefix%", Messages.PREFIX.getValue())
                .replace("%primary%", Messages.PRIMARY_COLOR.getValue())
                .replace("%secondary%", Messages.SECONDARY_COLOR.getValue());
    }

}
