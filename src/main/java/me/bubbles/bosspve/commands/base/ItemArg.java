package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import me.bubbles.bosspve.items.manager.Item;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.PlayerInventory;

public class ItemArg extends Argument {

    public ItemArg(BossPVE plugin, int index) {
        super(plugin, "item", "item <item>", index);
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
        if(utilSender.getPlayer().getInventory().firstEmpty()==-1) {
            utilSender.sendMessage("%prefix% %primary%Your inventory is full!");
            return;
        }
        Item item = plugin.getItemManager().getItemByName(args[relativeIndex]);
        if(item==null) {
            utilSender.sendMessage("%prefix% %primary%Item %secondary%"+args[relativeIndex]+"%primary% does not exist.");
            return;
        }
        PlayerInventory inventory = utilSender.getPlayer().getInventory();
        if(inventory.getItem(inventory.getHeldItemSlot())==null) {
            inventory.setItem(inventory.getHeldItemSlot(),item.nmsAsItemStack());
        }else{
            inventory.setItem(inventory.firstEmpty(),item.nmsAsItemStack());
        }
        utilSender.getPlayer().getInventory().setContents(inventory.getContents());
        utilSender.sendMessage("%prefix% %primary%Item %secondary%"+item.getNBTIdentifier()+"%primary% has been given.");
    }

    private String getItemsList() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("%prefix% %primary%Items:");
        for(Item item : plugin.getItemManager().getItems()) {
            stringBuilder.append("\n").append("%primary%").append("- ").append("%secondary%").append(item.getNBTIdentifier());
        }
        return stringBuilder.toString();
    }

}
