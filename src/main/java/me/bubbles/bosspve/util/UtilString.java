package me.bubbles.bosspve.util;

import me.bubbles.bosspve.BossPVE;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class UtilString {

    private BossPVE plugin;

    public UtilString(BossPVE plugin) {
        this.plugin=plugin;
    }

    public String colorFillPlaceholders(String message) {
        FileConfiguration config = plugin.getConfigManager().getConfig("messages.yml").getFileConfiguration();
        for(String string : config.getKeys(false)) {
            String regex = "%"+string+"%";
            String replacement = config.getString(string);
            if(replacement!=null) {
                message = message.replace(regex,replacement);
            }
        }
        return ChatColor.translateAlternateColorCodes('&',message);
    }

}
