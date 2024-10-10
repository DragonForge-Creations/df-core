package me.quickscythe.dragonforge.utils.config;

import org.bukkit.plugin.java.JavaPlugin;
import json2.JSONObject;

/**
 * Easier way to specify a class as a ConfigFile manager.
 */
public class ConfigClass {
    protected final ConfigFile CONFIG;

    /**
     * @param plugin     Plugin to register ConfigFile.
     * @param configFile String to save file. Do not include file extension.
     */
    public ConfigClass(JavaPlugin plugin, String configFile) {
        CONFIG = ConfigFileManager.getFile(plugin, configFile);
    }

    /**
     * @param plugin     Plugin to register ConfigFile.
     * @param configFile String to save file. Do not include file extension.
     * @param resource   String path to plugin resource. Include file extension.
     */
    public ConfigClass(JavaPlugin plugin, String configFile, String resource) {
        CONFIG = ConfigFileManager.getFile(plugin, configFile, resource);
    }

    /**
     * @param plugin     Plugin to register ConfigFile.
     * @param configFile String to save file. Do not include file extension.
     * @param defaults   JSONObject of default values to populate ConfigFile.
     */
    public ConfigClass(JavaPlugin plugin, String configFile, JSONObject defaults) {
        CONFIG = ConfigFileManager.getFile(plugin, configFile, defaults);
    }

    public ConfigFile getConfig(){
        return CONFIG;
    }

    /**
     * Save ConfigFile
     */
    public void finish() {
        CONFIG.save();
    }
}
