package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import me.bubbles.bosspve.mysql.MySQL;
import me.bubbles.bosspve.ticker.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class UpdateXP extends Timer {

    private BossPVE plugin;

    public UpdateXP(BossPVE plugin) {
        super(plugin,10); // update every 2 seconds
        this.plugin=plugin;
    }

    @Override
    public void onComplete() {
        MySQL mySQL = plugin.getMySQL();
        for(Player player : Bukkit.getOnlinePlayers()) {
            UtilUserData uud = mySQL.getData(player.getUniqueId());
            if(player.getLevel()<uud.getLevel()) {
                player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            }
            player.setLevel(uud.getLevel());
            float result = getPercentComplete(uud);
            if(result<0.0||result>=1.0) {
                continue;
            }
            player.setExp(getPercentComplete(uud));
        }
        restart();
    }

    private float getPercentComplete(UtilUserData uud) { // get the percent complete

        float nextLevel = uud.getLevel()+1;
        float xpRequirement = nextLevel*nextLevel*10;

        return uud.getXp()/xpRequirement;

    }

    private float getExpNextLevelRequirement(int level) { // the xp requirement to level up - used for the percent
        if(level<=16) {
            return 2*level+7;
        }
        if(level<=31) {
            return 5*level-38;
        }
        return 9*level-158;
    }

}