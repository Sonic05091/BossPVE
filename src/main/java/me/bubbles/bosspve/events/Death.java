package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.stages.Stage;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Death extends Event {

    public Death(BossPVE plugin) {
        super(plugin, EntityDamageEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        EntityDamageEvent e = (EntityDamageEvent) event;
        if(!(e.getEntity() instanceof Player)) {
            return;
        }
        Player player = ((Player) e.getEntity()).getPlayer();
        if(player==null) {
            return;
        }
        if(player.getHealth()-e.getFinalDamage()>0) {
            return;
        }
        Stage stage = plugin.getStageManager().getStage(player.getLocation());
        if(stage==null) {
            return;
        }
        if(!stage.isAllowed(player)) {
            return;
        }
        e.setCancelled(true);
        player.teleport(stage.getSpawn());
        player.setHealth(20);
    }

}
