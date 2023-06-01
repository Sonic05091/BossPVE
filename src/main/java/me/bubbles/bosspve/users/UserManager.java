package me.bubbles.bosspve.users;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class UserManager {

    private BossPVE plugin;
    private HashSet<User> userList = new HashSet<>();

    public UserManager(BossPVE plugin) {
        this.plugin=plugin;
    }

    public User addPlayer(Player p) {
        for(User user : userList) {
            if(user.getPlayer().getUniqueId().equals(p.getUniqueId()))
                return null;
        }
        User user = new User(p,plugin);
        userList.add(user);
        return user;
    }

    public User getUser(Player p) {
        for(User user : userList) {
            if(user.getPlayer().getUniqueId().equals(p.getUniqueId()))
                return user;
        }
        return null;
    }

}
