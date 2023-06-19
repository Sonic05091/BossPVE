package me.bubbles.bosspve.commands;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Command;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class Spawn extends Command {

    public Spawn(BossPVE plugin) {
        super(plugin, "spawn");
        setPermission("spawn");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        super.run(sender, args);
        if(!permissionCheck()) {
            return;
        }
        if(!utilSender.isPlayer()) {
            utilSender.sendMessage("%prefix% %primary%You must be in game to do that!");
            return;
        }
        if(args.length==index+1) { // 1 arg
            if(utilSender.hasPermission("setspawn")) {
                if(args[index].equalsIgnoreCase("set")) {
                    plugin.getConfigManager().getConfig("config.yml").getFileConfiguration().set("spawn",asLocation(utilSender.getPlayer().getLocation()));
                    utilSender.sendMessage("%prefix% %primary%Spawn has been set.");
                    plugin.getConfigManager().saveAll();
                    return;
                }
            }
        }
        utilSender.sendMessage("%prefix% %primary%Teleporting to spawn.");
        utilSender.getPlayer().teleport(getLocation(plugin.getConfigManager().getConfig("config.yml").getFileConfiguration().getString("spawn")));
    }

    private Location getLocation(String value) {
        String[] values = value.split(",");
        Location location;
        if(values.length==4) {
            location=new Location(
                    plugin.getMultiverseCore().getMVWorldManager().getMVWorld(values[0]).getCBWorld(),
                    Double.parseDouble(values[1]),
                    Double.parseDouble(values[2]),
                    Double.parseDouble(values[3])
            );
        } else if (values.length==6) {
            location=new Location(
                    plugin.getMultiverseCore().getMVWorldManager().getMVWorld(values[0]).getCBWorld(),
                    Double.parseDouble(values[1]),
                    Double.parseDouble(values[2]),
                    Double.parseDouble(values[3]),
                    Float.parseFloat(values[4]),
                    Float.parseFloat(values[5])
            );
        } else {
            location=null;
        }
        return location;
    }

    private String asLocation(Location location) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(location.getWorld().getName());
        stringBuilder.append(",");
        stringBuilder.append(location.getX());
        stringBuilder.append(",");
        stringBuilder.append(location.getY());
        stringBuilder.append(",");
        stringBuilder.append(location.getZ());
        stringBuilder.append(",");
        stringBuilder.append(location.getYaw());
        stringBuilder.append(",");
        stringBuilder.append(location.getPitch());
        return stringBuilder.toString();
    }

}
