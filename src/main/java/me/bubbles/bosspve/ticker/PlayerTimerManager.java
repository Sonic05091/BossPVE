package me.bubbles.bosspve.ticker;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerTimerManager {

    private HashMap<Player, Timer> timers;
    private BossPVE plugin;
    private long lastClear;

    public PlayerTimerManager(BossPVE plugin) {
        this.plugin=plugin;
        timers=new HashMap<>();
        lastClear=plugin.getEpochTimestamp();
    }

    public void onTick() {
        timers.forEach((player, timer) -> timer.onTick());
        if(lastClear<=plugin.getEpochTimestamp()-3600) {
            HashMap<Player, Timer> copy = new HashMap<>(timers);
            copy.forEach(((player, timer) -> {
                if(timer.getLastCall()<=plugin.getEpochTimestamp()-1800) {
                    removeTimer(player);
                }
            }));
        }
    }

    public void addTimer(Player player, Timer timer) {
        timers.put(player, timer);
    }

    public boolean contains(Player player) {
        return timers.containsKey(player);
    }

    public void removeTimer(Player player) {
        timers.remove(player);
    }

    public void restartTimer(Player player) {
        timers.get(player).restart();
    }

    public boolean isTimerActive(Player player) {
        return timers.get(player).isActive();
    }

}
