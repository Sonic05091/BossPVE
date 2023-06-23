package me.bubbles.bosspve.commands.base;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.commands.manager.Argument;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ToggleMsgs extends Argument {

    public ToggleMsgs(BossPVE plugin, int index) {
        super(plugin, "togglemsgs", index);
    }

    @Override
    public void run(CommandSender sender, String[] args, boolean alias) {
        super.run(sender, args, alias);
        if(!utilSender.isPlayer()) {
            utilSender.sendMessage("%prefix% %primary%You must be in game to do this!");
            return;
        }
        Player player = utilSender.getPlayer();
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        boolean current = pdc.get(new NamespacedKey(plugin, "mobKillMessages"), PersistentDataType.BOOLEAN);
        pdc.set(new NamespacedKey(plugin, "mobKillMessages"), PersistentDataType.BOOLEAN, !current);
        utilSender.sendMessage("%prefix% %primary%Mob kill messages have been set to %secondary%"+!current+"%primary%.");
    }

}
