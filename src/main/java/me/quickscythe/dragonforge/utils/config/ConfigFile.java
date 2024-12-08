package me.quickscythe.dragonforge.utils.config;


import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.chat.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import json2.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * ConfigFile is a class for creating custom config files.
 * @author QuickSctythe
 */
public class ConfigFile implements Config {

    final JSONObject defaults;
    JSONObject data;
    File file;
    JavaPlugin plugin;

    /**
     * Generate new ConfigFile instance. Should only be accessed by {@link ConfigFileManager}
     * @param plugin Plugin that registers the ConfigFile.
     * @param file Physical location the ConfigFile will be saved to.
     * @param defaults Default values to populate ConfigFile. Can be empty.
     */
     ConfigFile(JavaPlugin plugin, File file, JSONObject defaults) {
        this.plugin = plugin;
        StringBuilder data = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                data.append(scanner.nextLine());
            }
            scanner.close();
        } catch (IOException e) {
            CoreUtils.logger().log(Logger.LogLevel.ERROR, e);
        }
        this.data = data.toString().isEmpty() ? defaults : new JSONObject(data.toString());
        this.defaults = new JSONObject(defaults.toString());
        for(String key : defaults.keySet()){
            if(!this.data.has(key)){
                this.data.put(key, defaults.get(key));
            }
        }
        this.file = file;
    }

    public void save() {
        CoreUtils.logger().log("Saving " + plugin.getName() + "/" + file.getName());
        try {
            FileWriter f2 = new FileWriter(file, false);
            f2.write(data.toString(2));
            f2.close();
        } catch (IOException e) {
            CoreUtils.logger().log(Logger.LogLevel.ERROR, "There was an error saving " + file.getName());
            CoreUtils.logger().log(Logger.LogLevel.ERROR, e);
        }
    }

    /**
     * Gets the plugin that registered the config file. This is also the asset file the config will be saved under.
     * @return JavaPlugin
     */
    public JavaPlugin getPlugin(){
        return plugin;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public void reset() {
        this.data = new JSONObject(defaults.toString());
        save();
    }
}
