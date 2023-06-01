package me.bubbles.bosspve.events.manager;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.event.Listener;

public class Event implements Listener {

    public BossPVE plugin;
    public Event(BossPVE plugin) {
        this.plugin=plugin;
    }

}
