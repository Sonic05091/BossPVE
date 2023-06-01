package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.commands.manager.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class BaseCommand extends Command {
    private final int index=0;

    public BaseCommand(BossPVE plugin) {
        super("bosspve", plugin);
        /*addArguments(
        // TODO
        );*/
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        super.run(sender,args);
        if(!(args.length==0)) { // IF PLAYER SENDS ARGUMENTS
            for(Argument argument : getArguments()) { // ARGUMENTS
                if(argument.getArg().equalsIgnoreCase(args[index])) {
                    argument.run(sender, args,false);
                    return;
                }
            }
        }

        utilMessage.sendMessage(ChatColor.translateAlternateColorCodes('&',getArgsMessage()));
    }

}
