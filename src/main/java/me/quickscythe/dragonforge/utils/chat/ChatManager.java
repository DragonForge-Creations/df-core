package me.quickscythe.dragonforge.utils.chat;

import json2.JSONObject;
import me.quickscythe.dragonforge.utils.CoreUtils;
import me.quickscythe.dragonforge.utils.config.ConfigFile;
import me.quickscythe.dragonforge.utils.config.ConfigFileManager;
import net.kyori.adventure.text.Component;

import java.util.HashMap;
import java.util.Map;

public class ChatManager {

    private static final Map<String, Component> FORMATS = new HashMap<>();

    public static void start() {
        ConfigFile file = ConfigFileManager.getFile(CoreUtils.plugin(), "chat_format", "chat_format.json");
        JSONObject data = file.getData();
        for(String key : data.keySet())
            FORMATS.put(key, MessageUtils.deserialize(data.getString(key)));
    }

    public static Component getFormat(String format) {
        return FORMATS.get(format);
    }
}
