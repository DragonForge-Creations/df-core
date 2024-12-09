package me.quickscythe.dragonforge.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import static net.kyori.adventure.text.Component.text;

public abstract class CommandExecutor {
    private final JavaPlugin plugin;
    private final String cmd;

    public CommandExecutor(JavaPlugin plugin, String cmd) {
        this.plugin = plugin;
        this.cmd = cmd;
    }

    public String getName() {
        return cmd;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public abstract LiteralCommandNode<CommandSourceStack> getNode();

    public int logError(CommandSender sender, String message) {
        return logError(sender, text(message));
    }

    public int logError(CommandSender sender, Component message) {
        sender.sendMessage(message);
        return Command.SINGLE_SUCCESS;
    }
}
