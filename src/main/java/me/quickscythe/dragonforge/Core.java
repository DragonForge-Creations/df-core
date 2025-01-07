package me.quickscythe.dragonforge;

import me.quickscythe.dragonforge.commands.CommandManager;
import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.chat.MessageUtils;
import me.quickscythe.dragonforge.utils.items.ItemUtils;
import me.quickscythe.dragonforge.utils.sessions.SessionManager;
import me.quickscythe.dragonforge.utils.storage.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;


public final class Core extends JavaPlugin {

    @Override
    public void onEnable() {
        CoreUtils.init(this);
        CommandManager.init();
        ItemStack heart = new ItemStack(Material.TOTEM_OF_UNDYING);
        ItemMeta meta = heart.getItemMeta();
        meta.setCustomModelData(1001);
        heart.setItemMeta(meta);

        SessionManager.session().reward(new ItemStack(Material.ACACIA_DOOR)).task(Bukkit.getAdvancement(NamespacedKey.minecraft("husbandry/wax_on"))).end(() -> {
            if (SessionManager.session().task().isPresent() && SessionManager.session().reward().isPresent()) {
                CoreUtils.logger().log("Utils", "Task complete!");
                Advancement advancement = SessionManager.session().task().get();
                ItemStack reward = SessionManager.session().reward().get();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    AdvancementProgress progress = player.getAdvancementProgress(advancement);
                    if (progress.isDone()) {
                        player.sendMessage(MessageUtils.getMessage("message.task.complete", MessageUtils.plainText(reward.displayName())));
                        if (!ItemUtils.inventoryHasRoom(player.getInventory(), reward))
                            player.getWorld().dropItem(player.getLocation(), reward);
                        else player.getInventory().addItem(reward);
                    }
                }
            }
        }).start().end();


    }

    @Override
    public void onDisable() {
        DataManager.end();
        MessageUtils.loadChangesToFile();
        // Plugin shutdown logic
    }
}
