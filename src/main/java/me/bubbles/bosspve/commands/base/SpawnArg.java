package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.entities.manager.IEntityBase;
import me.bubbles.bosspve.items.manager.Item;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SpawnArg extends Argument {

    public SpawnArg(BossPVE plugin, int index) {
        super(plugin, "spawn", "spawn <entity>", index);
        setPermission("admin");
        for(Item item : plugin.getItemManager().getItems()) {
            acList.add(item.getNBTIdentifier());
        }
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender,args,alias);
        if(!permissionCheck()) {
            return;
        }
        if(args.length==relativeIndex) { // SENDS NO ARGS
            utilSender.sendMessage(getItemsList());
            return;
        }
        if(!utilSender.isPlayer()) {
            utilSender.sendMessage("%prefix% %primary%You must be in game to do this.");
            return;
        }
        IEntityBase base = plugin.getEntityManager().getEntityByName(args[relativeIndex]);
        if(base==null) {
            utilSender.sendMessage("%prefix% %primary%Entity %secondary%"+args[relativeIndex]+"%primary% does not exist.");
            return;
        }
        base.spawn(utilSender.getPlayer().getLocation());
        utilSender.sendMessage("%prefix% %primary%Entity %secondary%"+ChatColor.stripColor(base.uncoloredName())+"%primary% has been spawned.");
    }

    private String getItemsList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%prefix% %primary%Items:");
        for(IEntityBase entity : plugin.getEntityManager().getEntities()) {
            stringBuilder.append("\n").append("%primary%").append("- ").append("%secondary%").append(ChatColor.stripColor(entity.uncoloredName()).replaceAll(" ","_"));
        }
        return stringBuilder.toString();
    }

}
