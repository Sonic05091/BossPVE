package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntityBase;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;

public class UtilCustomEvents {

    private Event event;
    private BossPVE plugin;

    public UtilCustomEvents(BossPVE plugin, Event event) {
        this.plugin=plugin;
        this.event=event;
    }

    public void CustomEntityDeathEvent(IEntityBase entity) {
        if(!(event instanceof EntityDeathEvent)) {
            return;
        }
        EntityDeathEvent e = (EntityDeathEvent) event;
        if(e.getEntity().getKiller()==null) {
            return;
        }
        if(e.getEntity().getKiller().getPlayer()==null) {
            return;
        }
        if(!entity.hasSameTagAs(e.getEntity())) {
            return;
        }
        e.getDrops().clear();
        if(entity.getDrops()!=null) {
            e.getDrops().addAll(entity.getDrops());
        }
        Player player = e.getEntity().getKiller();
        int xp;
        int money;
        xp=entity.getXp();
        money=entity.getMoney();
        UtilItemStack uiu = new UtilItemStack(plugin,player.getInventory().getItemInMainHand());
        xp*=uiu.calculateXpMultiplier();
        money*= uiu.calculateMoneyMultiplier();
        //todo
        // database of xp + somehow get level from amount
        // make each level harder to achieve then the last DONT use a for loop to see amt of times until lvl=lvl or wtv
    }

}
