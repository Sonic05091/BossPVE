package me.bubbles.bosspve.stage;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntityBase;
import me.bubbles.bosspve.ticker.Timer;
import org.bukkit.Location;

public class StageEntity extends Timer {

    private BossPVE plugin;
    private IEntityBase entity;
    private Location location;

    public StageEntity(BossPVE plugin, IEntityBase entity, Location location, int ticks) {
        super(plugin,ticks);
        this.plugin=plugin;
        this.entity=entity;
        this.location=location;
    }

    @Override
    public void onComplete() {
        entity.spawn(location);
        restart();
    }

    public StageEntity setEnabled(boolean bool) {
        if(plugin.getTimerManager().containsTimer(this)==bool) {
            return this;
        }
        if(bool) {
            plugin.getTimerManager().addTimer(this);
        } else {
            plugin.getTimerManager().removeTimer(this);
        }
        return this;
    }

}
