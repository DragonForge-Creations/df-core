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
        CoreUtils.packServer().setPack(e.getPlayer());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getItem() != null && e.getItem().getType() == Material.DIAMOND) {
            EphemeralAdvancement.Builder builder = new EphemeralAdvancement.Builder(CoreUtils.plugin());
            builder.title("title")
                    .description("description")
                    .icon(Material.DIAMOND)
                    .frame(AdvancementDisplay.Frame.TASK)
                    .toast(true)
                    .chat(true);
//                    .announceMessage(text("You got a diamond!").color(NamedTextColor.GREEN))
//                    .announceSound("minecraft:entity.player.levelup");
            EphemeralAdvancement advancement = builder.build();
            advancement.send(e.getPlayer());
        }
    }
}
