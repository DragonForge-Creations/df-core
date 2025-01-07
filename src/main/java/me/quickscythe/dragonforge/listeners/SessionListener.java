package me.quickscythe.dragonforge.listeners;

import json2.JSONObject;
import me.quickscythe.dragonforge.utils.sessions.SessionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SessionListener implements Listener {

    public SessionListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        SessionManager.session().track(e.getPlayer()).data(e.getPlayer());
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        JSONObject session = SessionManager.session().data(e.getPlayer());
        String key = "blocks_mined";
        session.put(key, session.has(key) ? session.getInt(key) + 1 : 1);
    }
}
