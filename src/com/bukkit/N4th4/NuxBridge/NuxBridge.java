package com.bukkit.N4th4.NuxBridge;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class NuxBridge extends JavaPlugin {
    private final NBPlayerListener         playerListener = new NBPlayerListener(this);
    private final HashMap<Player, Boolean> debugees       = new HashMap<Player, Boolean>();
    public Configuration                   config;
    public Connection                      conn;
    public PermissionHandler               permissions    = null;

    public NuxBridge() {
        NBLogger.initialize();
    }

    public void onEnable() {
        // ###### Config ######
        File configFile = new File("plugins/NuxBridge/config.yml");
        if (configFile.exists()) {
            config = new Configuration(configFile);
            config.load();
        } else {
            NBLogger.severe("File not found : plugins/NuxBridge/config.yml");
        }

        String url = "jdbc:" + config.getString("url");
        String user = config.getString("user");
        String passwd = config.getString("passwd");

        // ###### Permissions ######
        if (permissions != null) {
            return;
        }

        Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

        if (permissionsPlugin == null) {
            NBLogger.severe("Permissions not found");
            return;
        }

        permissions = ((Permissions) permissionsPlugin).getHandler();

        // ###### SQL ######
        try {
            conn = DriverManager.getConnection(url, user, passwd);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        NBLogger.info("Connected to MySQL");

        // ###### Events ######
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.High, this);

        PluginDescriptionFile pdfFile = this.getDescription();
        NBLogger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");
    }

    public void onDisable() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}
