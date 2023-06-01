package me.bubbles.bosspve.commands.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.util.Messages;
import me.bubbles.bosspve.util.UtilMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Argument implements CommandExecutor {

    public BossPVE plugin;
    public int index;
    private List<Argument> arguments = new ArrayList<>();
    public String no_perms;
    private String arg;
    private String display;
    private String permission;
    private String alias;
    private UtilMessage utilMessage;

    // CONSTRUCTORS
    public Argument(BossPVE plugin, String arg, String display, int index) {
        this.plugin=plugin;
        this.index=index+1;
        this.arg=arg;
        this.display=display;
        this.alias=null;
    }

    // ON RUN
    public void run(CommandSender sender, String[] args, boolean alias) {
        if(alias) {
            index=0;
        }
        this.utilMessage =new UtilMessage(plugin,sender);
    }

    // ON ALIAS
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        run(sender,args,true);
        return true;
    }

    // ARGUMENTS
    public void addArguments(Argument... args) {
        arguments.addAll(Arrays.asList(args));
    }

    public String getArg() {
        return arg;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    // ALIAS
    public void setAlias(String alias) {
        this.alias=alias;
    }

    public String getAlias() {
        return alias;
    }

    // PERMISSION

    public void setPermission(String permission) {
        String node = plugin.getName() + "." + permission;
        this.permission=node;
        this.no_perms=Messages.NO_PERMS.getValue().replace("%node%",node);
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

    // GETTERS

    public String getDisplay() {
        return display;
    }

    public String getArgsMessage() {

        StringBuilder stringBuilder = new StringBuilder();
        String topLine = "%prefix%" + "%primary%" + " " + display;
        stringBuilder.append(topLine);

        for(Argument argument : arguments) {
            String command = "\n" + "%primary%" + argument.getDisplay();
            stringBuilder.append(command);
        }

        return stringBuilder.toString();

    }

}
