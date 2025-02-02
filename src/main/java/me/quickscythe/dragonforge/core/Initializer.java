package me.quickscythe.dragonforge.core;

import me.quickscythe.dragonforge.core.commands.CommandManager;
import me.quickscythe.dragonforge.core.listeners.SessionListener;
import me.quickscythe.dragonforge.core.utils.CoreUtils;
import me.quickscythe.dragonforge.core.utils.chat.MessageUtils;
import org.bukkit.plugin.java.JavaPlugin;


public final class Initializer extends JavaPlugin {

    @Override
    public void onEnable() {
        CoreUtils.init(this);
        new SessionListener(this);
        CommandManager.init();
    }

    @Override
    public void onDisable() {
        MessageUtils.loadChangesToFile();
        // Plugin shutdown logic
    }
}
