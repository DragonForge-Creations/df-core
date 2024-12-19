package me.quickscythe.dragonforge.utils.chat;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import static net.kyori.adventure.text.Component.text;

public class Logger {

    private final ComponentLogger LOG;

    public Logger(Plugin plugin) {
        LOG = plugin.getComponentLogger();
    }

    public void log(String tag, String msg) {
        log(LogLevel.INFO, tag, msg);
    }

    public void log(LogLevel level, String tag, String msg) {
        log(level, tag, msg, null);
    }

    public void log(LogLevel level, String tag, TextComponent msg) {
        log(level, tag, msg, null);
    }

    public void log(LogLevel level, String tag, Exception ex) {
        StringBuilder trace = new StringBuilder();
        for (StackTraceElement el : ex.getStackTrace())
            trace.append(el).append("\n");
        log(level, tag, ex.getMessage() + ": " + trace, null);
    }

    public void log(LogLevel level, String tag, String msg, CommandSender feedback) {
        log(level, tag, Component.text(msg), feedback);
    }

    public void log(LogLevel level, String tag, TextComponent msg, CommandSender feedback) {
        level = level == null ? LogLevel.INFO : level;
        switch (level) {
            case WARN -> LOG.warn(msg);
            case DEBUG -> LOG.debug(msg);
            case ERROR -> LOG.error(msg);
            case TRACE -> LOG.trace(msg);
            default -> LOG.info(msg);
        }
        if (feedback != null) feedback.sendMessage(level.getTag().append(text(" [" + tag + "] ")).append(msg));
    }

    public void error(String tag, Exception ex) {
        log(LogLevel.ERROR, tag, ex);
    }

    public ComponentLogger logger() {
        return LOG;
    }


    public enum LogLevel {
        INFO("[INFO]", "#438df2"), WARN("[WARN]", NamedTextColor.YELLOW), ERROR("[ERROR]", NamedTextColor.RED), TRACE("[TRACE]"), DEBUG("[DEBUG]");

        String tag;
        TextColor color;

        LogLevel(String tag, String color) {
            this.tag = tag;
            this.color = TextColor.fromCSSHexString(color);
        }

        LogLevel(String tag, NamedTextColor color) {
            this.tag = tag;
            this.color = color;
        }

        LogLevel(String tag) {
            this.tag = tag;
            this.color = NamedTextColor.GRAY;
        }

        public TextComponent getTag() {
            return text().content("").color(NamedTextColor.WHITE).append(text(tag, color)).build();
        }

        public String getTagString() {
            return tag;
        }
    }

}
