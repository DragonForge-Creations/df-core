package me.quickscythe.dragonforge;

import me.quickscythe.dragonforge.commands.CommandManager;
import me.quickscythe.dragonforge.listeners.GuiListener;
import me.quickscythe.dragonforge.utils.CoreUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    @Override
    public void onEnable() {
        CoreUtils.init(this);
        CommandManager.init();
        new GuiListener(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
