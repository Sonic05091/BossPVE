package me.bubbles.bosspve.commands.other;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Command;
import org.bukkit.command.CommandSender;

public class Vote extends Command {

    public Vote(BossPVE plugin) {
        super(plugin, "Vote");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        super.run(sender, args);
        utilSender.sendMessage(
                "&8[%secondary%&lVOTE&8] %primary%Vote links:\n"+
                        "%primary%1: %secondary%https://minecraftservers.org/vote/651841\n"+
                        "%primary%2: %secondary%https://minecraft-server-list.com/server/497273/vote/\n"+
                        "%primary%3: %secondary%https://best-minecraft-servers.co/server-bubblesmc.17119/vote/\n"+
                        "%primary%4: %secondary%https://topminecraftservers.org/vote/33951\n"+
                        "%primary%5: %secondary%https://minecraft-mp.com/server/320755/vote/"
                );
    }

}
