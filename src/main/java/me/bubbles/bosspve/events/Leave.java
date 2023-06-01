package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.users.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave extends Event {

    public Leave(BossPVE plugin) {
        super(plugin);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

        User user = plugin.getUserManager().getUser(e.getPlayer());

    }

}
