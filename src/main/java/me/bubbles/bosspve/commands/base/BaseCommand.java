package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Command;
import org.bukkit.command.CommandSender;

public class BaseCommand extends Command {
    private final int index=0;

    public BaseCommand(BossPVE plugin) {
        super(plugin, "bosspve");
        addArguments(
                new ItemArg(plugin, index),
                new SpawnArg(plugin, index),
                new ReloadArg(plugin, index)
        );
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        super.run(sender,args);
    }

}
