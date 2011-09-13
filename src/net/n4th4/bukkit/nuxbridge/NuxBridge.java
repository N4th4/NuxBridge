package net.n4th4.bukkit.nuxbridge;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

public class NuxBridge extends JavaPlugin {
    private final NBPlayerListener playerListener = new NBPlayerListener(this);
    public final Logger            log            = this.getServer().getLogger();
    public Configuration           config;

    public void onEnable() {
        File configFile = new File("plugins/NuxBridge/config.yml");
        if (configFile.exists()) {
            config = new Configuration(configFile);
            config.load();
        } else {
            log.severe("[NuxBridge] File not found : plugins/NuxBridge/config.yml");
        }

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.High, this);

        PluginDescriptionFile pdfFile = this.getDescription();
        log.info("[NuxBridge] " + pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }

    public void onDisable() {
    }
}
