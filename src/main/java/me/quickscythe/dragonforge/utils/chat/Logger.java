package me.quickscythe.dragonforge.utils.chat;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static net.kyori.adventure.text.Component.text;

public class Logger {

    private final ComponentLogger LOG;

    public Logger(Plugin plugin) {
        LOG = plugin.getComponentLogger();
    }

    public void log(String msg) {
        log(LogLevel.INFO, msg);
    }

    public void log(LogLevel level, String msg) {
        log(level, msg, null);
    }

    public void log(LogLevel level, TextComponent msg) {
        log(level, msg, null);
    }

    public void log(LogLevel level, Exception ex) {
        StringBuilder trace = new StringBuilder();
        for (StackTraceElement el : ex.getStackTrace())
            trace.append(el).append("\n");
        log(level, ex.getMessage() + ": " + trace.toString(), null);
    }

    public void log(LogLevel level, String msg, CommandSender feedback) {
        log(level, Component.text(msg), feedback);
    }
    public void log(LogLevel level, TextComponent msg, CommandSender feedback) {
        level = level == null ? LogLevel.INFO : level;
//        StringBuilder builder = new StringBuilder();
//        builder.append(msg.content());
//        loopComponents(msg, builder);
//        LOG.info(level.getTagString() + " " + builder.toString());
        switch(level){
            case WARN -> LOG.warn(msg);
            case DEBUG -> LOG.debug(msg);
            case ERROR -> LOG.error(msg);
            case TRACE -> LOG.trace(msg);
            default -> LOG.info(msg);
        }
        if (feedback != null) feedback.sendMessage(level.getTag().append(text(" ")).append(msg));
    }

    public void error(Exception ex) {
        log(LogLevel.ERROR, ex);
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
