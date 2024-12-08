package me.quickscythe.dragonforge.utils.chat;

import me.quickscythe.dragonforge.utils.CoreUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ChatManager {

    private static final Map<String, String> FORMATS = new HashMap<>();

    public static void start() {
        FileConfiguration fc = CoreUtils.plugin().getConfig();
        if (!fc.isSet("format.party")) fc.set("format.party", "&a[P]");
        if (!fc.isSet("format.player")) fc.set("format.player", "&f<%lives_color%%player%&f> ");
        if (!fc.isSet("format.chat")) fc.set("format.chat", "&f%message%");
        CoreUtils.plugin().saveConfig();
        FORMATS.put("party", fc.getString("format.party"));
        FORMATS.put("player", fc.getString("format.player"));
        FORMATS.put("chat", fc.getString("format.chat"));
    }

    public static String getFormat(String format) {
        return FORMATS.get(format);
    }
}
