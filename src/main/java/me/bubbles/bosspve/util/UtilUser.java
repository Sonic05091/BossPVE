package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.entities.manager.IEntityBase;
import me.bubbles.bosspve.mysql.MySQL;
import org.bukkit.entity.Player;

public class UtilUser {

    private Player player;
    private BossPVE plugin;

    public UtilUser(BossPVE plugin, Player player) {
        this.player=player;
        this.plugin=plugin;
    }

    public void giveXp(int xp, boolean message) {
        MySQL mySQL = plugin.getMySQL();
        UtilUserData uud = mySQL.getData(player.getUniqueId());
        int level=uud.getLevel();
        uud.setXp(uud.getXp()+xp);
        if(message) {
            if(level!=uud.getLevel()) {
                sendMessage("%prefix% %primary%You have level up from %secondary%"+level+"%primary% to %secondary%"+uud.getLevel()+"%primary%.");
            } else {
                sendMessage("%prefix %primary%You have gained %secondary%"+xp+" xp%primary%.");
            }
        }
        mySQL.save(uud);
    }

    public void giveXp(int xp, IEntityBase entity) {
        MySQL mySQL = plugin.getMySQL();
        UtilUserData uud = mySQL.getData(player.getUniqueId());
        int level=uud.getLevel();
        uud.setXp(uud.getXp()+xp);
        if(level!=uud.getLevel()) {
            sendMessage("%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and level up from %secondary%"+level+"%primary% to %secondary%"+uud.getLevel()+"%primary%.");
        } else {
            sendMessage("%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and gained %secondary%"+xp+" XP%primary%.");
        }
        mySQL.save(uud);
    }

    public void giveXpAndMoney(int xp, int money, IEntityBase entity) {
        MySQL mySQL = plugin.getMySQL();
        UtilUserData uud = mySQL.getData(player.getUniqueId());
        int level=uud.getLevel();
        uud.setXp(uud.getXp()+xp);
        if(level!=uud.getLevel()) {
            sendMessage("%prefix% %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and gained %secondary%$"+money+
                    " %primary%and leveled up from %secondary%"+level+"%primary% to %secondary%"+uud.getLevel()+"%primary%.");
        } else {
            sendMessage("%prefix %primary%You killed a %secondary%"+entity.getUncoloredName()+"%primary% and gained %secondary%$"+money+" and %secondary%"+xp+" xp%primary%.");
        }
        mySQL.save(uud);
        plugin.getEconomy().depositPlayer(player,money);
    }

    public void giveXpAndMoney(int xp, int money, boolean message) {
        MySQL mySQL = plugin.getMySQL();
        UtilUserData uud = mySQL.getData(player.getUniqueId());
        int level=uud.getLevel();
        uud.setXp(uud.getXp()+xp);
        if(message) {
            if(level!=uud.getLevel()) {
                sendMessage("%prefix% %primary%You gained %secondary%$"+money+
                        " %primary%and leveled up from %secondary%"+level+"%primary% to %secondary%"+uud.getLevel()+"%primary%.");
            } else {
                sendMessage("%prefix %primary%You gained %secondary%$"+money+" and %secondary%"+xp+" xp%primary%.");
            }
        }
        mySQL.save(uud);
        plugin.getEconomy().depositPlayer(player,money);
    }

    public void sendMessage(String message) {
        player.sendMessage(new UtilString(plugin).colorFillPlaceholders(message));
    }

}
