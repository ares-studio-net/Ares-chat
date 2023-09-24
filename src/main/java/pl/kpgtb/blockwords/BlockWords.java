package pl.kpgtb.blockwords;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockWords extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this,this);
        new Metrics(this, 19882);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(event.isCancelled()) {
            return;
        }
        String msg = event.getMessage();
        String fixed = msg.toLowerCase().trim().replace(" ", "");

        for(String key : getConfig().getConfigurationSection("blocked").getKeys(false)) {
            ConfigurationSection section = getConfig().getConfigurationSection("blocked."+key);
            if(fixed.contains(section.getString("word"))) {
                event.setMessage(section.getString("replace"));
                return;
            }
        }
    }
}
