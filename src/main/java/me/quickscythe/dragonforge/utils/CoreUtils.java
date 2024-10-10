package me.quickscythe.dragonforge.utils;


import me.quickscythe.dragonforge.utils.chat.Logger;
import me.quickscythe.dragonforge.utils.config.ConfigFile;
import me.quickscythe.dragonforge.utils.config.ConfigFileManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreUtils {

    private static Logger logger;
    private static ConfigFile config;
    private static JavaPlugin plugin;

    public static void init(JavaPlugin plugin) {
        CoreUtils.plugin = plugin;
        logger = new Logger(plugin);
        config = ConfigFileManager.getFile(plugin, "config", "config.json");
    }


    public static Logger getLogger() {
        return logger;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }

    public static ConfigFile getConfig() {
        return config;
    }
}
