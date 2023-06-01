package me.bubbles.bosspve.commands.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.base.BaseCommand;

import java.util.HashSet;
import java.util.List;

public class CommandManager {
    private BossPVE plugin;
    private HashSet<Command> commandsList = new HashSet<>();

    public CommandManager(BossPVE plugin) {
        this.plugin=plugin;
        registerCommands();
    }

    public void addCommand(Command... commands) {
        for(Command command : commands) {
            plugin.getCommand(command.getCommand()).setExecutor(command);
            commandsList.add(command);
            if(!command.getArgsMessage().isEmpty()) {
                registerArguments(command.getArguments());
            }
        }
    }

    public void registerArguments(List<Argument> arguments) {
        for(Argument argument : arguments) {
            if(argument.getAlias()!=null) {
                try {
                    plugin.getCommand(argument.getAlias()).setExecutor(argument);
                } catch (NullPointerException e) {
                    plugin.getLogger().warning("Command /"+argument.getAlias()+", could not be registered. Most likely due to improper plugin.yml");
                }
            }
            if(argument.getArguments().isEmpty()) {
                break;
            }
            registerArguments(argument.getArguments());
        }
    }

    public void registerCommands() {
        addCommand(new BaseCommand(plugin));
    }

}
