package me.bubbles.bosspve.users;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.util.UtilUserData;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    public int cachedXp;
    public int cachedLevel;
    private UUID uuid;
    private BossPVE plugin;

    public User(BossPVE plugin, UUID uuid) {
        this.uuid=uuid;
        this.plugin=plugin;
        update();
    }

    public void update() {
        UtilUserData uud = plugin.getMySQL().getData(uuid);
        this.cachedXp=uud.getXp();
        this.cachedLevel=uud.getLevel();
    }

}
