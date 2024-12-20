package me.quickscythe.dragonforge;

import me.quickscythe.dragonforge.commands.CommandManager;
import me.quickscythe.dragonforge.listener.PlayerListener;
import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.chat.MessageUtils;
import me.quickscythe.dragonforge.utils.storage.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    @Override
    public void onEnable() {
        CoreUtils.init(this);
        CommandManager.init();

        new PlayerListener(this);
    }

    @Override
    public void onDisable() {
        DataManager.end();
        MessageUtils.loadChangesToFile();
        // Plugin shutdown logic
    }
}
