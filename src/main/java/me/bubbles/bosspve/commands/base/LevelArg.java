package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.util.UtilUserData;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class LevelArg extends Argument {

    public LevelArg(BossPVE plugin, int index) {
        super(plugin, "level", "level <player>", index);
        setPermission("level");
        setAlias("level");
    }

    @Override
    @SuppressWarnings("deprecation")
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(!permissionCheck()) {
            return;
        }
        if(!utilSender.isPlayer()) {
            if(args.length==relativeIndex) {
                utilSender.sendMessage(getArgsMessage());
                return;
            }
        }
        UtilUserData uid;
        if(args.length==relativeIndex) { // no args
            uid = plugin.getMySQL().getData(utilSender.getPlayer().getUniqueId());
            utilSender.sendMessage("%prefix% %primary%Your level is %secondary%"+uid.getLevel()+"%primary%.");
            return;
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(args[relativeIndex]);
        if(!utilSender.hasPermission("bosspve.level.other")) {
            utilSender.sendMessage("%prefix% %primary%You do not have permission to do that.");
        }
        if(!player.hasPlayedBefore()) {
            utilSender.sendMessage("%prefix% %primary%Could not find player %secondary%"+args[relativeIndex]+"%primary%.");
            return;
        }
        uid = plugin.getMySQL().getData(player.getUniqueId());
        utilSender.sendMessage("%prefix% %secondary%"+player.getName()+"'s %primary%level is "+uid.getLevel());
    }

}
