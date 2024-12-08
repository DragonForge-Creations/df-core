package me.quickscythe.dragonforge.utils.storage;


import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.config.ConfigFile;
import me.quickscythe.dragonforge.utils.config.ConfigFileManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class DataManager {

    private static File configFolder;

    private static final InternalStorage storage = new InternalStorage();
    private static File dataFolder;

    public static void init(JavaPlugin plugin) {
        dataFolder = plugin.getDataFolder();
        if(!dataFolder.exists()) CoreUtils.logger().log("Creating data folder: " + dataFolder.mkdir());
    }


    public static File getDataFolder() {
        return dataFolder;
    }

    public static InternalStorage getStorage() {
        return storage;
    }

}
