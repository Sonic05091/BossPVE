package me.bubbles.bosspve.commands;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Command;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class Data extends Command {

    public Data(BossPVE plugin) {
        super(plugin, "data");
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        super.run(sender, args);
        if(!utilSender.isPlayer()) {
            return;
        }
        ItemStack bukkitStack = utilSender.getPlayer().getInventory().getItemInMainHand();
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(bukkitStack);
        NBTTagCompound nbtTagCompound = nmsItemStack.w();
        /*Set<String> nbtList = nbtTagCompound.e();
        StringBuilder stringBuilder = new StringBuilder();
        for(String string : nbtList) {
            stringBuilder.append(string).append(": ").append(nbtTagCompound.l(string)).append("\n");
        }
        utilSender.sendMessage(stringBuilder.toString());*/
        utilSender.sendMessage(nbtTagCompound.toString());
    }

}
