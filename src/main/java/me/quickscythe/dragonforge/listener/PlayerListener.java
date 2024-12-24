package me.quickscythe.dragonforge.listener;

import io.papermc.paper.advancement.AdvancementDisplay;
import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.advancements.EphemeralAdvancement;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import static net.kyori.adventure.text.Component.text;

public class PlayerListener implements Listener {

    public PlayerListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws NoSuchAlgorithmException, IOException {
//        String[] props = new String[]{CoreUtils.config().getData().getString("jenkins_user"), CoreUtils.config().getData().getString("jenkins_password"), CoreUtils.config().getData().getString("jenkins_url"), CoreUtils.config().getData().getString("jenkins_api_endpoint")};
//        String url = "https://downloads.vanillaflux.com/assets.zip";
//        byte[] buffer = new byte[8192];
//        int count;
//        MessageDigest digest = MessageDigest.getInstance("SHA-1");
//        try (BufferedInputStream bis = new BufferedInputStream(NetworkUtils.downloadFile(url))) {
//            while ((count = bis.read(buffer)) > 0) {
//                digest.update(buffer, 0, count);
//            }
//        }
//
//        byte[] hash = digest.digest();
//        e.getPlayer().setResourcePack(url, hash, text("This is a resource pack"));
        CoreUtils.packServer().setPack(e.getPlayer());
    }
}
