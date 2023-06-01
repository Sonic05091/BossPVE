package me.bubbles.bosspve.commands.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.util.Messages;
import me.bubbles.bosspve.util.UtilMessage;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Command implements CommandExecutor {

    public BossPVE plugin;
    public String no_perms;
    private String command;
    private String permission;
    private List<Argument> arguments = new ArrayList<>();
    public UtilMessage utilMessage;
    public final int index=0;

    public Command(String command, BossPVE plugin) {
        this.command=command;
        this.plugin=plugin;
    }

    public void run(CommandSender sender, String[] args) {
        this.utilMessage=new UtilMessage(plugin,sender);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        run(sender,args); // this is so I can use super statements for run
        return true;
    }

    public String getCommand() {
        return command;
    }

    public boolean permissionCheck() {
        if(permission==null)
            return true;
        if(!utilMessage.isPlayer()) {
            return true;
        }
        Player player = (Player) utilMessage.getSender();
        if(player.hasPermission(permission)) {
            utilMessage.sendMessage(no_perms);
            return false;
        }else{
            return true;
        }
    }

    public void setPermission(String permission) {
        String node = plugin.getName() + "." + permission;
        this.permission=node;
        this.no_perms=Messages.NO_PERMS.getValue().replace("%node%",node);
    }

    public String getArgsMessage() {

        StringBuilder stringBuilder = new StringBuilder();
        String topLine = "%prefix%" + "%primary%" + " Commands:";
        stringBuilder.append(topLine);

        // IF YOU ARE COPYING MY CODE, REMOVE THIS LINE - THE LINE BELOW IS SPECIFIC TO THIS PLUGIN
        stringBuilder.append("\n").append("%primary%").append("/").append(getCommand()).append(" ").append("%secondary%").append("<player>");

        for(Argument arg : arguments) {
            String command = "\n" + "%primary%" + "/" + getCommand() + "%secondary%" + " " + arg.getDisplay() + "\n";
            stringBuilder.append(command);
        }

        return stringBuilder.toString();

    }

    public void addArguments(Argument... args) {
        arguments.addAll(Arrays.asList(args));
    }

    public List<Argument> getArguments() {
        return arguments;
    }

}
