package me.bubbles.bosspve.util;

import java.util.UUID;

public class UtilUserData {

    private UUID uuid;
    private int xp;

    public UtilUserData(UUID uuid, int xp) {
        this.uuid=uuid;
        this.xp=xp;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp=xp;
    }

    public int getLevel() {
        return ((int) Math.sqrt(xp/10));
    }

}
