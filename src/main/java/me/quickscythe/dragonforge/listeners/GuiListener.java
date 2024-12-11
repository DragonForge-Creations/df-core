package me.quickscythe.dragonforge.listeners;


import me.quickscythe.dragonforge.Core;
import me.quickscythe.dragonforge.utils.gui.GuiInventory;
import me.quickscythe.dragonforge.utils.gui.GuiItem;
import me.quickscythe.dragonforge.utils.gui.GuiManager;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackInfoLike;
import net.kyori.adventure.resource.ResourcePackRequest;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

public class GuiListener implements Listener {

    public GuiListener(Core plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerCloseInventory(InventoryCloseEvent e) {
        e.getPlayer().sendResourcePacks(() -> ResourcePackRequest.resourcePackRequest().packs(new ResourcePackInfoLike() {
            @Override
            public @NotNull ResourcePackInfo asResourcePackInfo() {
                return new ResourcePackInfo() {
                    @Override
                    public @NotNull UUID id() {
                        return null;
                    }

                    @Override
                    public @NotNull URI uri() {
                        return URI.create("https://ci.vanillaflux.com/view/DragonForge%20Creations/job/df_ll4el_resources/lastSuccessfulBuild/artifact/resources.zip");
                    }

                    @Override
                    public @NotNull String hash() {
                        return "" + new Date().getTime();
                    }
                };
            }
        }).replace(false).build());
        if (!e.getPlayer().hasMetadata("switchinv")) {
            GuiManager.closeGui((Player) e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (GuiManager.getOpenGui(player) != null) {
            GuiInventory gui = GuiManager.getOpenGui(player);
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType() == Material.AIR) return;
            if (!e.getCurrentItem().hasItemMeta()) return;
            if (gui.hasItem(e.getCurrentItem(), player)) {
                GuiItem item = gui.getItem(e.getCurrentItem(), player);
                if (item.hasAction()) {
                    item.processActions(player, e.getClick());
                }
            }
        }
    }
}