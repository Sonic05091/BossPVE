package me.bubbles.bosspve.users;

import me.bubbles.bosspve.BossPVE;

import java.util.HashMap;
import java.util.UUID;

public class UserManager {

    private BossPVE plugin;
    private HashMap<UUID, User> playerList;

    public UserManager(BossPVE plugin) {
        this.plugin=plugin;
        this.playerList=new HashMap<>();
    }

    public User getUser(UUID uuid) {
        if(playerList.containsValue(uuid)) {
            return playerList.get(uuid);
        }
        User user = new User(plugin, uuid);
        playerList.put(uuid,user);
        return user;
    }

}
