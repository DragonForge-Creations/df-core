package me.quickscythe.dragonforge.utils.storage;


import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.config.ConfigFile;
import me.quickscythe.dragonforge.utils.config.ConfigFileManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class DataManager {

    private static File configFolder;

    private static final InternalStorage storage = new InternalStorage();
    private static ConfigFile configFile;
    private static File dataFolder;

    public static void init(JavaPlugin plugin) {
        dataFolder = plugin.getDataFolder();;
        if(!dataFolder.exists()) CoreUtils.logger().log("Creating data folder: " + dataFolder.mkdir());
        configFile = ConfigFileManager.getFile(plugin, "config", "config.json");



    }

    public static ConfigFile getConfigFile() {
        return configFile;
    }

    public static File getDataFolder() {
        return dataFolder;
    }

    public static InternalStorage getStorage() {
        return storage;
    }

}
