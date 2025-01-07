package me.quickscythe.dragonforge;

import me.quickscythe.dragonforge.commands.CommandManager;
import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.chat.MessageUtils;
import me.quickscythe.dragonforge.utils.storage.DataManager;
import org.bukkit.Material;
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


    }

    @Override
    public void onDisable() {
        DataManager.end();
        MessageUtils.loadChangesToFile();
        // Plugin shutdown logic
    }
}
