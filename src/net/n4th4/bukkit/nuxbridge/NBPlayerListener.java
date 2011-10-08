package net.n4th4.bukkit.nuxbridge;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

public class NBPlayerListener extends PlayerListener {
    private Connection     conn;
    public final NuxBridge plugin;

    public NBPlayerListener(NuxBridge instance) {
        plugin = instance;
    }

    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        plugin.log.info("[NuxBridge] " + player.getName() + " is login in ...");

        Statement state;
        try {
            conn = DriverManager.getConnection("jdbc:" + plugin.config.getString("url"), plugin.config.getString("user"), plugin.config.getString("passwd"));
            conn.setAutoCommit(false);
            state = conn.createStatement();
            ResultSet result = state.executeQuery("SELECT id_group FROM smf_members WHERE member_name='" + player.getName() + "'");
            result.last();
            int id_group;

            if (result.getRow() == 0) {
                id_group = plugin.config.getInt("default_id", 0);
            } else {
                id_group = result.getInt("id_group");
            }

            String group = plugin.config.getString("groups." + id_group);

            plugin.log.info("[NuxBridge] " + player.getName() + "'s group is " + group);

            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "permissions player setgroup " + player.getName() + " " + group);

            conn.close();

            plugin.log.info("[NuxBridge] " + player.getName() + " was succesfully added in group " + group);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
