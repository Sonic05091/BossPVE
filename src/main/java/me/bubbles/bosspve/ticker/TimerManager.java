package me.bubbles.bosspve.ticker;

import me.bubbles.bosspve.BossPVE;

import java.util.HashSet;

public class TimerManager {

    private HashSet<Timer> timers;
    private BossPVE plugin;

    public TimerManager(BossPVE plugin) {
        this.plugin=plugin;
        timers=new HashSet<>();
    }

    public void onTick() {
        timers.forEach(Timer::onTick);
    }

    public void addTimer(Timer timer) {
        timers.add(timer);
    }

    public void removeTimer(Timer timer) {
        timers.remove(timer);
    }

    public boolean containsTimer(Timer timer) {
        return timers.contains(timer);
    }

}
