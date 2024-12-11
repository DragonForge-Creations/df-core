package me.quickscythe.dragonforge.utils.storage;


import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.config.ConfigFile;
import me.quickscythe.dragonforge.utils.config.ConfigFileManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ConfigManager {

    private final JavaPlugin plugin;
    private final String name;
    private ConfigFile config;

    public ConfigManager(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    public void start() {
        CoreUtils.logger().log("Starting " + name + " ConfigManager...");
        config = ConfigFileManager.getFile(plugin, name);

    }

    public void end() {

        CoreUtils.logger().log("Ending " + name + " ConfigManager...");
        config.save();
    }

    public String name() {
        return name;
    }

    public ConfigFile config() {
        return config;
    }
}
