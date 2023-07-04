package me.bubbles.bosspve.items.armor.bee;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.items.manager.bases.armor.Armor;
import me.bubbles.bosspve.items.manager.bases.armor.ArmorSet;
import me.bubbles.bosspve.util.MessageType;
import me.bubbles.bosspve.util.UtilChances;
import me.bubbles.bosspve.util.UtilPlayerMessage;
import me.bubbles.bosspve.util.UtilUser;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;

public class BeeSet extends ArmorSet {

    public BeeSet(BossPVE plugin) {
        super(plugin);
    }

    @Override
    public void onEvent(Event event) {
        if(event instanceof EntityDeathEvent) {
            EntityDeathEvent e = (EntityDeathEvent) event;
            if(e.getEntity().getKiller() == null) {
                return;
            }
            Player player = e.getEntity().getKiller();
            if(!wearingFullSet(player,true)) {
                return;
            }
            if(!UtilChances.rollTheDice(1,100,1)) {
                return;
            }
            UtilUser uu = new UtilUser(plugin,player);
            uu.giveXp(100,false);
            new UtilPlayerMessage(plugin,player).sendMessage(MessageType.OTHER,"%prefix% %primary%You received %secondary%100 xp %primary%from your armor!");
        }
    }

    @Override
    public Armor getBoots() {
        return new BeeBoots(plugin);
    }

    @Override
    public Armor getPants() {
        return new BeePants(plugin);
    }

    @Override
    public Armor getChestplate() {
        return new BeeChestplate(plugin);
    }

    @Override
    public Armor getHelmet() {
        return new BeeHelmet(plugin);
    }

}
