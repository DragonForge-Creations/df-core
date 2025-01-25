package me.quickscythe.dragonforge.listeners;

import me.quickscythe.dragonforge.utils.sessions.SessionManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SessionListener implements Listener {

    public SessionListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (SessionManager.session().tracking(e.getPlayer())){
            System.out.println("Player already tracked.");
            return;
        }
        System.out.println("Player not tracked yet.");
        SessionManager.session().track(e.getPlayer()).data(e.getPlayer());
    }


}