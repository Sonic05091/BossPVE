package me.bubbles.bosspve.ticker;

import java.util.HashSet;

public class TimerManager {

    private HashSet<Timer> timers;

    public TimerManager() {
        timers=new HashSet<>();
    }

    public void onTick() {
        timers.forEach(Timer::onTick);
    }

    public boolean addTimer(Timer timer) {
        return timers.add(timer);
    }

    public boolean removeTimer(Timer timer) {
        return timers.remove(timer);
    }

    public boolean containsTimer(Timer timer) {
        return timers.contains(timer);
    }

}
