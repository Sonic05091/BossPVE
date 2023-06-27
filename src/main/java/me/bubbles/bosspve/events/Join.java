package me.bubbles.bosspve.events;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.events.manager.Event;
import me.bubbles.bosspve.mysql.MySQL;
import me.bubbles.bosspve.util.UtilUserData;
import org.bukkit.NamespacedKey;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class Join extends Event {

    public Join(BossPVE plugin) {
        super(plugin, PlayerJoinEvent.class);
    }

    @Override
    public void onEvent(org.bukkit.event.Event event) {
        PlayerJoinEvent e = (PlayerJoinEvent) event;
        MySQL mySQL = plugin.getMySQL();
        UtilUserData uud = mySQL.getData(e.getPlayer().getUniqueId());
        if(uud.getXp()==-1) {
            mySQL.save(new UtilUserData(e.getPlayer().getUniqueId(),0));
        }

        PersistentDataContainer data = e.getPlayer().getPersistentDataContainer();
        if(!data.has(new NamespacedKey(plugin,"mobKillMessages"), PersistentDataType.BOOLEAN)) {
            data.set(new NamespacedKey(plugin,"mobKillMessages"), PersistentDataType.BOOLEAN, true);
        }

    }

}
