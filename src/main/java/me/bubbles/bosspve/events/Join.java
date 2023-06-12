package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join extends Event {

    public Join(BossPVE plugin) {
        super(plugin, PlayerJoinEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerJoinEvent e = (PlayerJoinEvent) event;
    }

}
