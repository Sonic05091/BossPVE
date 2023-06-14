package me.bubbles.bosspve.events.manager;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.*;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

public class EventManager {

    private BossPVE plugin;
    private HashSet<Event> events = new HashSet<>();

    public EventManager(BossPVE plugin) {
        this.plugin=plugin;
        registerListener(); // REGISTER EVENT LISTENERS
        Collections.addAll(this.events, // REGISTER EVENT HANDLERS
                new Join(plugin),
                new Leave(plugin),
                new AnvilEnchantedBook(plugin),
                new UpdateAnvil(plugin),
                new PlayerDmgOther(plugin),
                new UpdateLore(plugin)
        );
    }

    public void onEvent(org.bukkit.event.Event event) {
        plugin.getItemManager().onEvent(event);
        plugin.getEntityManager().onEvent(event);
        events.stream()
                .filter(eventObj -> eventObj.getEvent().equals(event.getClass()))
                .collect(Collectors.toList())
                .forEach(eventObj -> eventObj.onEvent(event));
    }

    public void registerListener() {
        plugin.getServer().getPluginManager().registerEvents(new Listeners(this),plugin);
    }

}
