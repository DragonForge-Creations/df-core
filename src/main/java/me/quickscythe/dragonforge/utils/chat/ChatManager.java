package me.quickscythe.dragonforge.utils.chat;

import json2.JSONObject;
import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.config.ConfigFile;
import me.quickscythe.dragonforge.utils.config.ConfigFileManager;
import me.quickscythe.dragonforge.utils.storage.DataManager;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ChatManager {

    private static final Map<String, String> FORMATS = new HashMap<>();

    public static void start() {
        ConfigFile file = ConfigFileManager.getFile(CoreUtils.plugin(), "chat_format");
        JSONObject data = file.getData();
        if(!data.has("party")) data.put("party", "&a[P]");
        if(!data.has("player")) data.put("player", "&f<%lives_color%%player%&f> ");
        if(!data.has("chat")) data.put("chat", "&f%message%");
        file.save();
        FORMATS.put("party", data.getString("format.party"));
        FORMATS.put("player", data.getString("format.player"));
        FORMATS.put("chat", data.getString("format.chat"));
    }

    public static String getFormat(String format) {
        return FORMATS.get(format);
    }
}
