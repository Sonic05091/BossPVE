package me.bubbles.bosspve.events.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.Join;
import me.bubbles.bosspve.events.Leave;

public class EventManager {
    private BossPVE plugin;

    public EventManager(BossPVE plugin) {
        this.plugin=plugin;
        registerEvents();
    }

    public void addEvent(Event... events) {
        for(Event event : events) {
            plugin.getServer().getPluginManager().registerEvents(event,plugin);
        }
    }

    public void registerEvents() {
        addEvent(new Join(plugin),new Leave(plugin));
    }

}
