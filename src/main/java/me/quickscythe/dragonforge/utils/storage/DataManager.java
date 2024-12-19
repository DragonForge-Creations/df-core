package me.quickscythe.dragonforge.utils.storage;


import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.network.discord.WebhookManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DataManager {

    private static final InternalStorage storage = new InternalStorage();
//    private static File configFolder;
    private static File dataFolder;
    private static Map<String, ConfigManager> configManagers = new HashMap<>();

    public static void init(JavaPlugin plugin) {
        dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) CoreUtils.logger().log("DataManager",  "Creating data folder: " + dataFolder.mkdir());
        registerConfigManager(new WebhookManager(plugin));
    }


    public static File getDataFolder() {
        return dataFolder;
    }

    public static InternalStorage getStorage() {
        return storage;
    }

    public static void registerConfigManager(ConfigManager manager) {
        configManagers.put(manager.name(), manager);
        manager.start();
    }

    public static ConfigManager getConfigManager(String name) {
        return configManagers.getOrDefault(name, null);
    }

    public static <T extends ConfigManager> T  getConfigManager(String name, Class<T> clazz) {
        return clazz.cast(configManagers.getOrDefault(name, null));
    }


    public static void end() {
        configManagers.values().forEach(ConfigManager::end);
    }
}
