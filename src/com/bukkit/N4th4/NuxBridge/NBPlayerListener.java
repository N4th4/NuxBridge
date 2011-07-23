package com.bukkit.N4th4.NuxBridge;

import java.sql.ResultSet;
import java.sql.Statement;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import com.nijiko.permissions.Entry;
import com.nijiko.permissions.Group;
import com.nijiko.permissions.User;

public class NBPlayerListener extends PlayerListener {
    public final NuxBridge plugin;

    public NBPlayerListener(NuxBridge instance) {
        plugin = instance;
    }

    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        NBLogger.info(player.getName() + " is login in ...");
        
        Statement state;
        try {
            state = plugin.conn.createStatement();
            ResultSet result = state.executeQuery("SELECT id_group FROM smf_members WHERE member_name='" + player.getName() + "'");
            result.last();
            int id_group;
            
            if (result.getRow() == 0) {
                id_group = plugin.config.getInt("default_id", 0);
            } else {
                id_group = result.getInt("id_group");
            }
            
            String group = plugin.config.getString("groups." + id_group);
            
            NBLogger.info(player.getName() + "'s group is " + group);
            
            for (Object worldObj : plugin.config.getList("worlds")) {
                String world = (String) worldObj;
                Group permGroup = plugin.permissions.safeGetGroup(world, group);
                User permUser = plugin.permissions.safeGetUser(world, player.getName());
                
                if (permUser.getParents() != null) {
                    for (Entry parent : permUser.getParents()) {
                        permUser.removeParent(plugin.permissions.getGroupObject(world, parent.getName()));
                    }
                }
                
                permUser.addParent(permGroup);
            }
            
            NBLogger.info(player.getName() + " was succesfully added in group " + group);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
